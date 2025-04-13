package ru.neoflex.ukhin.vacationcalculator.store.entity.vacation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Abstract class representing a vacation entity.
 * This class contains common properties and methods for vacation entities.
 */
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class VacationEntity {
    private Double salary;
}
