package org.example.oracle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class MainController {

    @GetMapping
    public String getPerformance(Model model) {
        return "index";
    }
}
