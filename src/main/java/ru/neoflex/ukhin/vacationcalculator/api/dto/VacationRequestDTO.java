package ru.neoflex.ukhin.vacationcalculator.api.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class VacationRequestDTO {
    @NotNull(message = "Salary is required")
    @Min(value = 0, message = "Salary must be positive")
    private Double salary;

    @Min(value = 0, message = "Vacation days must be positive")
    private Integer vacationDays;

    private LocalDate startDate; //TODO: change to String

    private LocalDate endDate; //TODO: change to String

    //TODO: add validation and required fields
}
