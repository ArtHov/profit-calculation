package ru.fundcount.pc.profitcalculation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fundcount.pc.profitcalculation.model.ResponseModel;
import ru.fundcount.pc.profitcalculation.service.CurrencyRateService;

/**
 * Currency Rate Positive or Negative Profit Calculation controller.
 *
 * @author Artur_Hovhannisyan
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfitCalculationRestController {

    private final CurrencyRateService service;

    @GetMapping(value = "/api/profit/calculation", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseModel getProfit(String date, double amount) {

        log.debug("Received date : {}", date);
        log.debug("Received amount : {}", amount);

        ResponseModel response = new ResponseModel();
        response.setProfit(service.calculateProfit(date, amount));
        return response;
    }
}
