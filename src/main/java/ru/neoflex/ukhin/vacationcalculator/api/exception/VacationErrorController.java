package ru.neoflex.ukhin.vacationcalculator.api.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class VacationErrorController implements ErrorController {
    public static final String ERROR_PATH = "/error";

    ErrorAttributes errorAttributes;

    @RequestMapping(VacationErrorController.ERROR_PATH)
    public ResponseEntity<Object> error(WebRequest request) {
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(
                request,
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.EXCEPTION, ErrorAttributeOptions.Include.MESSAGE)
        );

        return ResponseEntity.status((Integer) attributes.get("status"))
                .body(ErrorDTO
                        .builder()
                        .error((String) attributes.get("error"))
                        .description((String) attributes.get("message"))
                        .build());
    }
}
