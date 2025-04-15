package ru.neoflex.ukhin.vacationcalculator.api.factory;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.neoflex.ukhin.vacationcalculator.api.dto.VacationRequestDTO;
import ru.neoflex.ukhin.vacationcalculator.store.entity.vacation.DateBasedVacationEntity;
import ru.neoflex.ukhin.vacationcalculator.store.entity.vacation.SimpleVacationEntity;
import ru.neoflex.ukhin.vacationcalculator.store.entity.vacation.VacationEntity;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Validated
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class VacationRequestFactory {
    DateTimeFormatter dateTimeFormatter;

    /**
     * Creates a VacationEntity based on the provided VacationRequestDTO.
     *
     * @param vacationRequestDTO the DTO containing vacation request details
     * @return a VacationEntity object
     * @throws IllegalArgumentException if the vacationRequestDTO is null or invalid
     */
    public VacationEntity createVacationEntity(@Valid VacationRequestDTO vacationRequestDTO) {
        if (vacationRequestDTO == null) {
            throw new IllegalArgumentException("vacationRequestDTO is null");
        }

        if (vacationRequestDTO.getStartDate() != null &&
                vacationRequestDTO.getEndDate() != null) { //DateBased format
            try{
                return DateBasedVacationEntity.builder()
                        .startDate(LocalDate.parse(vacationRequestDTO.getStartDate(), dateTimeFormatter))
                        .endDate(LocalDate.parse(vacationRequestDTO.getEndDate(), dateTimeFormatter))
                        .salary(vacationRequestDTO.getSalary())
                        .build();
            } catch (DateTimeParseException e){
                throw new IllegalArgumentException("Date must be in dd-MM-yyyy format");
            }
        } else if (vacationRequestDTO.getVacationDays() != null &&
                vacationRequestDTO.getVacationDays() > 0) { // Simple format
            return SimpleVacationEntity.builder()
                    .vacationDays(vacationRequestDTO.getVacationDays())
                    .salary(vacationRequestDTO.getSalary())
                    .build();
        }else{
            throw new IllegalArgumentException("vacationRequestDTO is not valid");
        }
    }
}
