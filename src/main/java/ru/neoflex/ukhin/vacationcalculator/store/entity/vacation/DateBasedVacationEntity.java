package ru.neoflex.ukhin.vacationcalculator.store.entity.vacation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DateBasedVacationEntity extends VacationEntity {
    private LocalDate startDate;
    private LocalDate endDate;
}
