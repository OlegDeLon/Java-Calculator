package com.example.calc.Controller;

import java.util.List;
import com.example.calc.Model.Calculation;
import com.example.calc.Model.PolishNotation;

public class Controller {

    PolishNotation polishNotation = new PolishNotation();
    Calculation calculation = new Calculation();

    public String calculatePolish(List<String> income_string) {
        return calculation.calculate(income_string);
    }

    public List<String> makePolish(String income_string) {
        return polishNotation.makePolish(income_string);
    }

    public String calculate(String incomeString){

        return calculatePolish(makePolish(incomeString));
    }
}
