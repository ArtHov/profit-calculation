package ru.fundcount.pc.profitcalculation.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class CurrencyRateModel implements Serializable {

    private String base;
    private Map<String, String> rates;
    private String date;
}
