package ru.neoflex.ukhin.vacationcalculator.api.factory;

import org.springframework.stereotype.Component;
import ru.neoflex.ukhin.vacationcalculator.api.dto.VacationRequestDTO;
import ru.neoflex.ukhin.vacationcalculator.store.entity.vacation.DateBasedVacationEntity;
import ru.neoflex.ukhin.vacationcalculator.store.entity.vacation.SimpleVacationEntity;
import ru.neoflex.ukhin.vacationcalculator.store.entity.vacation.VacationEntity;

@Component
public class VacationRequestFactory {

    /**
     * Creates a VacationEntity based on the provided VacationRequestDTO.
     *
     * @param vacationRequestDTO the DTO containing vacation request details
     * @return a VacationEntity object
     * @throws IllegalArgumentException if the vacationRequestDTO is null or invalid
     */
    public VacationEntity createVacationEntity(VacationRequestDTO vacationRequestDTO) {
        if (vacationRequestDTO == null) {
            throw new IllegalArgumentException("vacationRequestDTO is null");
        }

        if (vacationRequestDTO.getStartDate() != null &&
                vacationRequestDTO.getEndDate() != null) { //Simple format
            return SimpleVacationEntity.builder()
                    .vacationDays(vacationRequestDTO.getVacationDays())
                    .salary(vacationRequestDTO.getSalary())
                    .build();
        } else if (vacationRequestDTO.getVacationDays() != null &&
                vacationRequestDTO.getVacationDays() > 0) { // DateBased format
            return DateBasedVacationEntity.builder()
                    .startDate(vacationRequestDTO.getStartDate())
                    .endDate(vacationRequestDTO.getEndDate()) //TODO: parse dates
                    .salary(vacationRequestDTO.getSalary())
                    .build();
        }else{
            throw new IllegalArgumentException("vacationRequestDTO is not valid");
        }
    }
}
