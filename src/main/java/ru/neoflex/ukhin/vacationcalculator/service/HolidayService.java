package ru.neoflex.ukhin.vacationcalculator.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.neoflex.ukhin.vacationcalculator.store.entity.HolidayEntity;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class HolidayService {

    private List<HolidayEntity> holidays = new ArrayList<>();

    @Value("${vacation.holidays.file:classpath:holidays.txt}")
    private Resource holidaysFile;

    /**
     * Initializes the holidays list by loading data from the configured file
     * Called automatically after the bean is constructed
     *
     *
     * @throws IOException if there is an error reading the file
     */
    @PostConstruct
    public void initializeHolidays() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(holidaysFile.getInputStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue; // Skip empty lines and comments
                }

                String[] parts = line.split("-");
                if (parts.length == 2) {
                    try {
                        int day = Integer.parseInt(parts[0]);
                        int month = Integer.parseInt(parts[1]);
                        addHoliday(day, month);
                    } catch (NumberFormatException e) {
                        // Log invalid format
                        System.err.println("Invalid holiday format: " + line);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load holidays from file", e);
        }
    }

    /**
     * Adds a holiday to the list
     *
     * @param day day of month (1-31)
     * @param month month (1-12)
     */
    private void addHoliday(int day, int month) {
        holidays.add(HolidayEntity.builder()
                .day(day)
                .month(month)
                .build()
        );
    }

    /**
     * Simple method to check if the date is a holiday.
     * <ul>
     *  <li>Complexity O(m*n), where m is the number of holidays and n is the number of days in a month.</li>
     * </ul>
     * <p>
     * More complex logic can be used:
     * <ul>
     *  <li>Sort dates</li>
     *  <li>Get first date</li>
     *  <li>For each date, check if the next date is a holiday</li>
     *  <li>Complexity O(n)</li>
     * </ul>
     *
     * @param date date of day, we're checking
     * @return is day a holiday, or not
     */
    public boolean isHoliday(LocalDate date) {
        for (HolidayEntity holiday : holidays) {
            if (holiday.getDay() == date.getDayOfMonth() &&
                    holiday.getMonth() == date.getMonthValue()) {
                return true;
            }
        }
        return false;
    }
}
