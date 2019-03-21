package ru.fundcount.pc.profitcalculation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfitController {

    @GetMapping(value = {"/", "/api", "/api/profit"})
    public String index() {
        return "index";
    }
}
