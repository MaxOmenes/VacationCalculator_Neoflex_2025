package ru.neoflex.ukhin.vacationcalculator.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CalculateTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Returns vacation pay for valid salary and vacation days")
    void returnsVacationPayForValidInput() throws Exception {
        mockMvc.perform(get("/calculate")
                        .param("salary", "110000")
                        .param("vacationDays", "7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"vacationPay\":26279.86}"));
    }

    @Test
    @DisplayName("Returns validation error for negative salary")
    void returnsValidationErrorForNegativeSalary() throws Exception {
        mockMvc.perform(get("/calculate")
                        .param("salary", "-50000")
                        .param("vacationDays", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"error\":\"Validation Error\",\"message\":\"Salary must be positive\"}"));
    }

    @Test
    @DisplayName("Returns validation error for invalid date format")
    void returnsValidationErrorForInvalidDateFormat() throws Exception {
        mockMvc.perform(get("/calculate")
                        .param("salary", "50000")
                        .param("startDate", "01-01-2023")
                        .param("endDate", "2023/01/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("{\"error\":\"Validation Error\",\"message\":\"End date must be in dd-MM-yyyy format\"}"));
    }
}

