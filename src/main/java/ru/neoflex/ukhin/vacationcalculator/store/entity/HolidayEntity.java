package ru.neoflex.ukhin.vacationcalculator.store.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HolidayEntity {
    private int day;
    private int month;
}
