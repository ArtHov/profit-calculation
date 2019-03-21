package ru.fundcount.pc.profitcalculation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import ru.fundcount.pc.profitcalculation.model.CurrencyRateModel;
import ru.fundcount.pc.profitcalculation.repository.CurrentRateHolder;
import ru.fundcount.pc.profitcalculation.util.Validator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.MessageFormat;

import static ru.fundcount.pc.profitcalculation.util.DateUtil.getCurrentDateAsString;
import static ru.fundcount.pc.profitcalculation.util.Validator.isValidAmount;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyRateService {

    private static final String RUB_CURRENCY = "RUB";
    private static final String URI_TEMPLATE = "https://api.exchangeratesapi.io/{0}?base=USD&symbols=RUB";
    private static final BigDecimal SPREAD = new BigDecimal("1.005");


    private final RestTemplate restTemplate;
    private final CurrentRateHolder holder;

    /**
     * Get Rate on the selected date
     * @param date Selected date
     * @return     Rate
     */
    public CurrencyRateModel getCurrencyRate(String date) {
        String uri = MessageFormat.format(URI_TEMPLATE, date);
        return restTemplate.getForObject(uri, CurrencyRateModel.class);
    }

    /**
     * Calculate Positive or Negative Profit
     * @param date         Selected date
     * @param amount       Bought amount
     * @return             Profit
     */
    public BigDecimal calculateProfit(String date, double amount) {

        if (!isValidAmount(amount)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount");
        }
        if (!Validator.isValidDate(date)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date");
        }

        if (!date.equals(getCurrentDateAsString())) {
            BigDecimal currentRate = holder.getCurrencyRate(RUB_CURRENCY);
            BigDecimal exchangeRate = new BigDecimal(getCurrencyRate(date).getRates().get(RUB_CURRENCY));

            if (!currentRate.equals(exchangeRate)) {
                BigDecimal resultProfit = currentRate.subtract(exchangeRate)
                        .multiply(SPREAD).multiply(BigDecimal.valueOf(amount));

                log.debug("Current Currency Rate : {}", currentRate);
                log.debug("Exchange Date Currency Rate : {}", exchangeRate);
                log.debug("Positive Or Negative Profit : {}", resultProfit);

                return resultProfit.round(MathContext.DECIMAL32);
            }
        }

        return BigDecimal.ZERO;
    }
}
