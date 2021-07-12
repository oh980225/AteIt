package com.example.ateIt.service;

import com.example.ateIt.controller.DietForm;
import com.example.ateIt.domain.Diet;
import com.example.ateIt.exception.NotExistDietException;
import com.example.ateIt.exception.NotValidDataException;
import com.example.ateIt.repository.DietRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class DietServiceTest {
    @Autowired
    DietRepository dietRepository;
    @Autowired
    DietService dietService;

    @Test
    void 오늘_식단_조회() {
        // given
        // sql문으로 테스트 데이터 입력

        // when
        List<Diet> dietList = dietService.getTodayMyDiet();

        // then
        assertAll(
                () -> assertThat(dietList.size()).isEqualTo(3),
                () -> assertThat(dietList.get(0).getName()).isEqualTo("비타민"),
                () -> assertThat(dietList.get(1).getName()).isEqualTo("샌드 위치"),
                () -> assertThat(dietList.get(2).getName()).isEqualTo("간지 치킨")
        );
    }

    @Test
    void 칼로리_총합() {
        // given
        List<Diet> dietList = dietService.getTodayMyDiet();

        // when
        int total = dietService.getTodayTotalCalorie(dietList);

        // then
        assertThat(total).isEqualTo(1050);
    }

    @Test
    void ID로_식단조회() {
        // given
        // sql문으로 테스트 데이터 입력

        // when
        Diet diet = dietService.findDietById(1L);

        // then
        assertThat(diet.getName()).isEqualTo("비타민");
    }

    @Test
    void ID존재X() {
        // given
        // sql문으로 테스트 데이터 입력

        // when
        RuntimeException e = assertThrows(NotExistDietException.class, () -> dietService.findDietById(4L));

        // then
        assertThat(e.getMessage()).isEqualTo("주어진 id에 해당하는 Diet가 존재하지 않습니다.");
    }

    @Test
    void 날짜별_식단_조회() {
        // given
        Diet diet = Diet.builder()
                .name("밥")
                .calorie(300)
                .today(LocalDate.of(2021, 7, 11))
                .build();
        dietRepository.save(diet);

        // when
        List<Diet> dietByDate = dietService.findDietByDate(LocalDate.of(2021, 7, 11));

        // then
        assertAll(
                () -> assertThat(dietByDate.size()).isEqualTo(1),
                () -> assertThat(dietByDate.get(0).getName()).isEqualTo("밥"),
                () -> assertThat(dietByDate.get(0).getCalorie()).isEqualTo(300)
        );
    }

    @Test
    void 식단등록() {
        // given
        DietForm dietForm = new DietForm();
        dietForm.setName("김치");
        dietForm.setCalorie(200);

        // when
        dietService.registerMyDiet(dietForm);

        // then
        List<Diet> todayMyDiet = dietService.getTodayMyDiet();
        assertAll(
                () -> assertThat(todayMyDiet.size()).isEqualTo(4),
                () -> assertThat(todayMyDiet.get(3).getName()).isEqualTo("김치")
        );
    }

    @Test
    void 식단수정() {
        // given
        // sql문으로 테스트 데이터 입력
        DietForm dietForm = new DietForm();
        dietForm.setName("김치");
        dietForm.setCalorie(200);

        // when
        dietService.editMyDiet(1L, dietForm);

        // then
        Diet dietById = dietService.findDietById(1L);
        assertThat(dietById.getName()).isEqualTo("김치");
    }

    @Test
    void 식단삭제() {
        // given
        // sql문으로 테스트 데이터 입력

        // when
        dietService.deleteMyDiet(1L);

        // then
        List<Diet> todayMyDiet = dietService.getTodayMyDiet();
        assertThat(todayMyDiet.size()).isEqualTo(2);
    }

    @Test
    void 입력값_타당X() {
        // given
        DietForm d1 = new DietForm();
        DietForm d2 = new DietForm();
        d1.setName("");
        d1.setCalorie(400);
        d2.setName("김치");
        d2.setCalorie(-200);

        // when
        NotValidDataException e1 = assertThrows(NotValidDataException.class, () -> dietService.registerMyDiet(d1));
        NotValidDataException e2 = assertThrows(NotValidDataException.class, () -> dietService.editMyDiet(1L, d1));
        NotValidDataException e3 = assertThrows(NotValidDataException.class, () -> dietService.registerMyDiet(d2));
        NotValidDataException e4 = assertThrows(NotValidDataException.class, () -> dietService.editMyDiet(1L, d2));

        // then
        assertAll(
                () -> assertThat(e1.getMessage()).isEqualTo("적합하지 않은 데이터입니다."),
                () -> assertThat(e2.getMessage()).isEqualTo("적합하지 않은 데이터입니다."),
                () -> assertThat(e3.getMessage()).isEqualTo("적합하지 않은 데이터입니다."),
                () -> assertThat(e4.getMessage()).isEqualTo("적합하지 않은 데이터입니다.")
                );
    }
}