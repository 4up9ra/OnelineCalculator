package ru.bagrov.onelinecalculator.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bagrov.onelinecalculator.services.CalculatorService;


@Controller
public class CalculatorController {

    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @RequestMapping(value="/calculator", method=RequestMethod.GET)
    public String index() {
        return "calculator";
    }

    @RequestMapping(value="/calculator", method=RequestMethod.POST)
    public String calculate(@RequestParam("input") String input, Model model) {
        model.addAttribute("input", calculatorService.calculate(input));
        return "calculator";
    }
}
