package com.example.ateIt.controller;

import com.example.ateIt.domain.Diet;
import com.example.ateIt.service.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @GetMapping("/diet/select")
    public String getSelectDate(DateForm dateForm, Model model) {
        List<Diet> dietList = null;
        System.out.println(dateForm.getDate());
        if(dateForm.getDate() == null) {
            model.addAttribute("date", LocalDate.now());
            dietList = dietService.findDietByDate(LocalDate.now());
        } else {
            model.addAttribute("date", dateForm.getDate());
            dietList = dietService.findDietByDate(LocalDate.parse(dateForm.getDate(), DateTimeFormatter.ISO_DATE));
        }

        int totalCalorie = dietService.getTodayTotalCalorie(dietList);

        model.addAttribute("dietList", dietList);
        model.addAttribute("calTotal", totalCalorie);

        return "selectDate";
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

    @GetMapping("/diet/edit/form/{id}")
    public String getEditForm(@PathVariable("id") Long id, Model model) {
        Diet diet = dietService.findDietById(id);
        model.addAttribute("diet", diet);

        return "editForm";
    }

    @GetMapping("/diet/edit/{id}")
    public String editDiet(@PathVariable("id") Long id, DietForm dietForm) {
        dietService.editMyDiet(id, dietForm);
        return "redirect:/diet";
    }

    @DeleteMapping("/diet/delete/{id}")
    public String deleteDiet(@PathVariable("id") Long id) {
        dietService.deleteMyDiet(id);
        return "mainPage";
    }
}
