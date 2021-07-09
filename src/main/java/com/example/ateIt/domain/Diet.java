package com.example.ateIt.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer calorie;
    private LocalDate today;

    @Builder
    public Diet(String name, Integer calorie, LocalDate today) {
        this.name = name;
        this.calorie = calorie;
        this.today = today;
    }

    public void setNameAndCalorie(String name, Integer calorie) {
        this.name = name;
        this.calorie = calorie;
    }
}
