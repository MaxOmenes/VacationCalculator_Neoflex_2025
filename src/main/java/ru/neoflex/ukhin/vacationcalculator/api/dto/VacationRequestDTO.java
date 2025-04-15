package ru.neoflex.ukhin.vacationcalculator.api.dto;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class VacationRequestDTO{
    @NotNull(message = "Salary is required")
    @Min(value = 0, message = "Salary must be positive")
    private Double salary;

    @Min(value = 0, message = "Vacation days must be positive")
    private Integer vacationDays;

    @Pattern(regexp = "(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-([0-9]{4})", message = "Start date must be in dd-MM-yyyy format")
    private String startDate;

    @Pattern(regexp = "(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-([0-9]{4})", message = "End date must be in dd-MM-yyyy format")
    private String endDate;
}
