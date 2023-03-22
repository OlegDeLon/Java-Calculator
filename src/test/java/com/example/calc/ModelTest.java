package com.example.calc;

import com.example.calc.Controller.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {
    Controller controller;
    @BeforeEach
    void beforeEachMethod() {
        controller = new Controller();
    }

    @Test
    public void test1() {
        assertEquals("4.0", controller.calculate("2+2"));
    }

    @Test
    public void test2() {
        assertEquals("-0.8414709848078965", controller.calculate("sin(3-(2+2))"));
    }

    @Test
    public void test3() {
        assertEquals("2.4457757470351704", controller.calculate("tan(-1+sin(5))"));
    }

    @Test
    public void test4() {
        assertEquals("6.0", controller.calculate("11-(-3-(-10-(-2)))"));
    }

    @Test
    public void test5() {
        assertEquals("13.0", controller.calculate("sqrt(169)"));
    }

    @Test
    public void test6() {
        assertEquals("2.0", controller.calculate("10mod4"));
    }
    @Test
    public void test7() {
        assertEquals("169.0", controller.calculate("13^2"));
    }




}
