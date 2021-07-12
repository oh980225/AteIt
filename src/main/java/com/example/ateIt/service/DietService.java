package com.example.ateIt.service;

import com.example.ateIt.controller.DateForm;
import com.example.ateIt.controller.DietForm;
import com.example.ateIt.domain.Diet;
import com.example.ateIt.exception.NotExistDietException;
import com.example.ateIt.exception.NotValidDataException;
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

    public int getTodayTotalCalorie(List<Diet> todayDiet) {
        int total = 0;
        for (Diet diet : todayDiet) {
            total += diet.getCalorie();
        }
        return total;
    }

    public Diet findDietById(Long id) {
        return dietRepository.findById(id).orElseThrow(NotExistDietException::new);
    }

    public List<Diet> findDietByDate(LocalDate date) {
        return dietRepository.findByToday(date);
    }

    public void registerMyDiet(DietForm dietForm) {
        validateData(dietForm);

        Diet diet = Diet.builder()
                .name(dietForm.getName())
                .calorie(dietForm.getCalorie())
                .today(LocalDate.now())
                .build();

        dietRepository.save(diet);
    }

    public void editMyDiet(Long id, DietForm dietForm) {
        validateData(dietForm);

        Diet diet = dietRepository.findById(id).orElseThrow(() -> new RuntimeException("해당 diet가 존재하지 않습니다."));
        diet.setNameAndCalorie(dietForm.getName(), dietForm.getCalorie());
    }

    public void deleteMyDiet(Long id) {
        dietRepository.deleteById(id);
    }

    private void validateData(DietForm dietForm) {
        if (dietForm.getName().isBlank() || dietForm.getCalorie() < 0) {
            throw new NotValidDataException();
        }
    }
}
