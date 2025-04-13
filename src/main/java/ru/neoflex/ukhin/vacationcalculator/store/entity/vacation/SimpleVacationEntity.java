package ru.neoflex.ukhin.vacationcalculator.store.entity.vacation;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SimpleVacationEntity extends VacationEntity {
    private Integer vacationDays;
}
