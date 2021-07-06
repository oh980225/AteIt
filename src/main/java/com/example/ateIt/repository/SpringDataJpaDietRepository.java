package com.example.ateIt.repository;

import com.example.ateIt.domain.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaDietRepository extends JpaRepository<Diet, Long>, DietRepository {
}
