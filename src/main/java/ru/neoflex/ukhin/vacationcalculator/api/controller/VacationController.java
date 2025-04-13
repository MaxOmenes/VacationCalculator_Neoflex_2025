package ru.neoflex.ukhin.vacationcalculator.api.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.neoflex.ukhin.vacationcalculator.api.dto.*;
import ru.neoflex.ukhin.vacationcalculator.api.factory.VacationRequestFactory;
import ru.neoflex.ukhin.vacationcalculator.service.VacationCalculatorService;
import ru.neoflex.ukhin.vacationcalculator.store.entity.vacation.VacationEntity;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class VacationController {

    VacationCalculatorService vacationCalculatorService;
    VacationRequestFactory vacationRequestFactory;

    /**
     * Endpoint to calculate vacation pay.
     *
     * @param vacationRequestDTO the vacation request data transfer object containing salary, vacation days, or date range
     * @return a ResponseEntity containing the vacation pay response data transfer object
     */
    @GetMapping("/calculate")
    public ResponseEntity<VacationResponseDTO> calculate(VacationRequestDTO vacationRequestDTO) {
        VacationEntity vacationEntity = vacationRequestFactory.createVacationEntity(vacationRequestDTO);
        double vacationPay = vacationCalculatorService.calculate(vacationEntity);

        VacationResponseDTO responseDTO = new VacationResponseDTO();
        responseDTO.setVacationPay(vacationPay);

        return ResponseEntity.ok(responseDTO);
    }

}
