package ru.neoflex.ukhin.vacationcalculator.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.neoflex.ukhin.vacationcalculator.store.entity.vacation.DateBasedVacationEntity;
import ru.neoflex.ukhin.vacationcalculator.store.entity.vacation.SimpleVacationEntity;
import ru.neoflex.ukhin.vacationcalculator.store.entity.vacation.VacationEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class VacationCalculatorServiceTest {
    public static final double EPS = 1.0;
    @Autowired
    VacationCalculatorService vacationCalculatorService;

    /*
     * Test based on this resource:
     * https://fd.hse.ru/news/913404888.html#:~:text=%D0%92%D0%BE%D1%82%20%D0%BF%D1%80%D0%BE%D1%81%D1%82%D0%B0%D1%8F%20%D1%84%D0%BE%D1%80%D0%BC%D1%83%D0%BB%D0%B0%20%D0%B4%D0%BB%D1%8F%20%D0%BF%D1%80%D0%B8%D0%BC%D0%B5%D1%80%D0%BD%D0%BE%D0%B3%D0%BE,%D1%80%D1%83%D0%B1%D0%BB%D0%B5%D0%B9%2C%20%D0%BE%D1%82%D0%BF%D1%83%D1%81%D0%BA%20%E2%80%94%207%20%D0%B4%D0%BD%D0%B5%D0%B9.&text=%D0%94%D0%BB%D1%8F%20%D1%82%D0%BE%D1%87%D0%BD%D0%BE%D0%B3%D0%BE%20%D1%80%D0%B0%D1%81%D1%87%D0%B5%D1%82%D0%B0%20%D0%BD%D0%B5%D0%BE%D0%B1%D1%85%D0%BE%D0%B4%D0%B8%D0%BC%D0%BE%20%D1%83%D0%BC%D0%BD%D0%BE%D0%B6%D0%B8%D1%82%D1%8C,%D0%A1%D0%94%D0%97)%20%D0%BD%D0%B0%20%D0%BA%D0%BE%D0%BB%D0%B8%D1%87%D0%B5%D1%81%D1%82%D0%B2%D0%BE%20%D0%B4%D0%BD%D0%B5%D0%B9%20%D0%BE%D1%82%D0%BF%D1%83%D1%81%D0%BA%D0%B0.
     */
    @Test
    void testCalculateVacationSimple() {
        var vacation = vacationCalculatorService.calculate(SimpleVacationEntity.builder()
                .salary(110000.0)
                .vacationDays(7)
                .build());

        assert Math.abs(vacation - 26279) < EPS : "Vacation pay should be 26279";
    }

    @Test
    void testCalculateVacationDateBased() {
        var vacation = vacationCalculatorService.calculate(DateBasedVacationEntity.builder()
                .salary(50000.0)
                .startDate(LocalDate.parse("2025-04-15"))
                .endDate(LocalDate.parse("2025-04-22"))
                .build());

        assert Math.abs(vacation - 8532) < EPS : "Vacation pay should be 8532";
    }

    @Test
    void testCalculateVacationDateBased_Zero() {
        var vacation = vacationCalculatorService.calculate(DateBasedVacationEntity.builder()
                .salary(0.0)
                .startDate(LocalDate.parse("2025-04-15"))
                .endDate(LocalDate.parse("2025-04-22"))
                .build());

        assert Math.abs(vacation - 0) < EPS : "Vacation pay should be 0";
    }

    @Test
    void testCalculateVacationDateBased_ReversedDate() {
        var vacation = DateBasedVacationEntity.builder()
                .salary(50000.0)
                .startDate(LocalDate.parse("2025-04-22"))
                .endDate(LocalDate.parse("2025-04-15"))
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            vacationCalculatorService.calculate(vacation);
        });
    }

    @Test
    void testCalculateVacationDateBased_NegativeSalary() {
        var vacation = DateBasedVacationEntity.builder()
                .salary(-50000.0)
                .startDate(LocalDate.parse("2025-04-22"))
                .endDate(LocalDate.parse("2025-04-15"))
                .build();

        assertThrows(IllegalArgumentException.class, () -> {
            vacationCalculatorService.calculate(vacation);
        });
    }

    @Test
    void testCalculateVacationDateBased_InvalidVacation() {
        class TempVacationEntity extends VacationEntity {
        }
        var vacation = new TempVacationEntity();

        assertThrows(IllegalArgumentException.class, () -> {
            vacationCalculatorService.calculate(vacation);
        });
    }


}
