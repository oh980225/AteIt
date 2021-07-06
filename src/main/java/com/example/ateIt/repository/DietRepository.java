package com.example.ateIt.repository;

import com.example.ateIt.domain.Diet;

import java.time.LocalDate;
import java.util.List;

public interface DietRepository {
    Diet save(Diet diet);
    List<Diet> findByToday(LocalDate date);
    void deleteById(Long id);
}
