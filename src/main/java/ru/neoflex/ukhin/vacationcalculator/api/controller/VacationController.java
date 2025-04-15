package ru.neoflex.ukhin.vacationcalculator.api.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.ukhin.vacationcalculator.api.dto.VacationRequestDTO;
import ru.neoflex.ukhin.vacationcalculator.api.dto.VacationResponseDTO;
import ru.neoflex.ukhin.vacationcalculator.api.factory.VacationRequestFactory;
import ru.neoflex.ukhin.vacationcalculator.service.VacationCalculatorService;
import ru.neoflex.ukhin.vacationcalculator.store.entity.vacation.VacationEntity;

import javax.validation.Valid;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class VacationController {

    VacationCalculatorService vacationCalculatorService;
    VacationRequestFactory vacationRequestFactory;

    /**
     * Endpoint to calculate vacation pay.
     * <p>
     * This method should take the salary and vacation days as input parameters.
     * But it should accept the start/end dates or vacation days
     * In case of start/end dates, the vacation days parameter will be ignored.
     * </p>
     * @param salary the salary of the employee
     * @param vacationDays the number of vacation days (optional)
     * @param  startDate  the start date of vacation (optional)
     * @param endDate the end date of vacation (optional)
     * @return a ResponseEntity containing the vacation pay response data transfer object
     */
    @GetMapping("/calculate")
    public ResponseEntity<VacationResponseDTO> calculate(@RequestParam Double salary,
                                                         @RequestParam(required = false) Integer vacationDays,
                                                         @RequestParam(required = false) String startDate,
                                                         @RequestParam(required = false) String endDate) {
        VacationRequestDTO vacationRequestDTO = VacationRequestDTO.builder()
                .salary(salary)
                .vacationDays(vacationDays)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        VacationEntity vacationEntity = vacationRequestFactory.createVacationEntity(vacationRequestDTO);
        double vacationPay = vacationCalculatorService.calculate(vacationEntity);

        VacationResponseDTO responseDTO = new VacationResponseDTO();
        responseDTO.setVacationPay(vacationPay);

        return ResponseEntity.ok(responseDTO);
    }

}
