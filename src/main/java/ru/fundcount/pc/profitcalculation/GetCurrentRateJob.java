package ru.fundcount.pc.profitcalculation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.fundcount.pc.profitcalculation.model.CurrencyRateModel;
import ru.fundcount.pc.profitcalculation.repository.CurrentRateHolder;
import ru.fundcount.pc.profitcalculation.service.CurrencyRateService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ru.fundcount.pc.profitcalculation.util.DateUtil.getCurrentDateAsString;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetCurrentRateJob {

    private final CurrencyRateService currencyRateService;
    private final CurrentRateHolder currentRateHolder;

    /**
     * Scheduled job runs every 30 minutes and update current date rate
     */
    @Scheduled(fixedDelay = 30 * 60 * 1000)
    public void setCurrentRate() {

        CurrencyRateModel response = currencyRateService.getCurrencyRate(getCurrentDateAsString());
        log.debug("Updated Rate : {}", response);
        response.getRates().forEach((key, value) -> currentRateHolder.addCurrencyRate(key, new BigDecimal(value)));
    }
}



