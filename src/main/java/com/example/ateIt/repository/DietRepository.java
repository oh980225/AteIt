package com.example.ateIt.repository;

import com.example.ateIt.domain.Diet;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DietRepository {
    Diet save(Diet diet);
    Optional<Diet> findById(Long id);
    List<Diet> findByToday(LocalDate date);
    void deleteById(Long id);
}
