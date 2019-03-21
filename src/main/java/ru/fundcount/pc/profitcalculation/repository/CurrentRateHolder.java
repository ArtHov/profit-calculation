package ru.fundcount.pc.profitcalculation.repository;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Up to date Rate holder
 */

@Component
public class CurrentRateHolder {

    private Map<String, BigDecimal> currentRate = new ConcurrentHashMap<>();

    public void addCurrencyRate(String key, BigDecimal value) {
        currentRate.put(key, value);
    }

    public BigDecimal getCurrencyRate(String currency){
        return currentRate.getOrDefault(currency,BigDecimal.ZERO);
    }

}
