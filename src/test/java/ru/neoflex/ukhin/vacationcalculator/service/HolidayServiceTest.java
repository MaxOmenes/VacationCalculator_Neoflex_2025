package ru.neoflex.ukhin.vacationcalculator.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class HolidayServiceTest {
    @Autowired
    HolidayService holidayService;

    @Test
    void testGetHolidays_CheckEmpty() {
        // Test the getHolidays method
        var holidays = holidayService.getHolidays();
        assert !holidays.isEmpty() : "Holidays list should not be empty";
    }

    @Test
    void testGetHolidays_CheckIsHoliday() {
        assert holidayService.isHoliday(LocalDate.parse("2025-01-01")) : "2025-01-01 should be a holiday";
    }
}
