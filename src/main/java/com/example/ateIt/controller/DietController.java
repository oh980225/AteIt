package com.example.ateIt.controller;

import com.example.ateIt.domain.Diet;
import com.example.ateIt.service.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DietController {
    private final DietService dietService;

    @Autowired
    public DietController(DietService dietService) {
        this.dietService = dietService;
    }

    @GetMapping("/diet")
    public String getTodayDiet(Model model) {
        List<Diet> todayDiet = dietService.getTodayMyDiet();
        int totalCalorie = dietService.getTodayTotalCalorie(todayDiet);

        model.addAttribute("dietList", todayDiet);
        model.addAttribute("calTotal", totalCalorie);

        return "mainPage";
    }

    @GetMapping("/diet/register")
    public String getRegisterForm() {
        return "dietForm";
    }

    @PostMapping("/diet/register")
    public String registerDiet(DietForm dietForm) {
        dietService.registerMyDiet(dietForm);

        return "redirect:/diet";
    }
}
