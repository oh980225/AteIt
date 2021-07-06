package com.example.ateIt.service;

import com.example.ateIt.controller.DietForm;
import com.example.ateIt.domain.Diet;
import com.example.ateIt.repository.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DietService {
    private final DietRepository dietRepository;

    @Autowired
    public DietService(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }

    public List<Diet> getTodayMyDiet() {
        return dietRepository.findByToday(LocalDate.now());
    }

    public void registerMyDiet(DietForm dietForm) {
        Diet diet = Diet.builder()
                .name(dietForm.getName())
                .calorie(dietForm.getCalorie())
                .today(LocalDate.now())
                .build();

        dietRepository.save(diet);
    }

    public int getTodayTotalCalorie(List<Diet> todayDiet) {
        int total = 0;
        for (Diet diet : todayDiet) {
            total += diet.getCalorie();
        }
        return total;
    }
}