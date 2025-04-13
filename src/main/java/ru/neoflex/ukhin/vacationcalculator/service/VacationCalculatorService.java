package ru.neoflex.ukhin.vacationcalculator.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.neoflex.ukhin.vacationcalculator.store.entity.vacation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class VacationCalculatorService {

    HolidayService holidayService;

    static double AVERAGE_WORKING_DAYS_IN_MONTH = 29.3;

    /**
     * Calculates the vacation pay based on the provided vacation entity.
     *
     * @param vacationEntity the vacation entity containing salary and vacation days (With or without dates)
     * @return the calculated vacation pay
     */
    public double calculate(VacationEntity vacationEntity) {
        if (vacationEntity == null || vacationEntity.getSalary() == null) {
            throw new IllegalArgumentException("Vacation entity or salary cannot be null");
        }

        if (vacationEntity instanceof SimpleVacationEntity) {
            return calculateSimple((SimpleVacationEntity) vacationEntity);
        } else if (vacationEntity instanceof DateBasedVacationEntity) {
            return calculateDateBased((DateBasedVacationEntity) vacationEntity);
        } else {
            throw new IllegalArgumentException("Unknown vacation entity type");
        }
    }

    /**
     * Calculates the vacation pay for a simple vacation entity.
     *
     * @param vacationEntity the simple vacation entity containing salary and vacation days
     * @return the calculated vacation pay
     */
    private double calculateSimple(SimpleVacationEntity vacationEntity) {
        if (vacationEntity == null || vacationEntity.getSalary() == null) {
            throw new IllegalArgumentException("Vacation entity or salary cannot be null");
        }
        if (vacationEntity.getVacationDays() == null || vacationEntity.getVacationDays() < 0) {
            throw new IllegalArgumentException("Vacation days cannot be null or less than 0");
        }

        return vacationEntity.getSalary() / AVERAGE_WORKING_DAYS_IN_MONTH * vacationEntity.getVacationDays();
    }

    /**
     * Calculates the vacation pay for a date-based vacation entity.
     *
     * @param vacationEntity the date-based vacation entity containing salary and vacation dates
     * @return the calculated vacation pay
     */
    private double calculateDateBased(DateBasedVacationEntity vacationEntity) {
        if (vacationEntity == null || vacationEntity.getSalary() == null) {
            throw new IllegalArgumentException("Vacation entity or salary cannot be null");
        }
        if (vacationEntity.getStartDate() == null || vacationEntity.getEndDate() == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }
        if (vacationEntity.getStartDate().isAfter(vacationEntity.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        LocalDate startDate = vacationEntity.getStartDate();
        LocalDate endDate = vacationEntity.getEndDate();

        long workingDays = startDate.datesUntil(endDate)
                .filter(localDate -> localDate.getDayOfWeek() != DayOfWeek.SATURDAY &&
                        localDate.getDayOfWeek() != DayOfWeek.SUNDAY)
                .filter(localDate -> !holidayService.isHoliday(localDate))
                .count();

        return vacationEntity.getSalary() / AVERAGE_WORKING_DAYS_IN_MONTH * workingDays;
    }
}
