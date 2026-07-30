package com.example.BillingSystem.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping({"/", "/index", "/home"})
    public String home() {
        return "index";
    }

    @GetMapping({"/dashboard", "/customer", "/product", "/invoice", "/payment", "/report"})
    public String page(HttpServletRequest request) {
        String path = request.getRequestURI().substring(1);
        return path;
    }
}
