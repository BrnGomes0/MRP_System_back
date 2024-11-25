package mrp_simulator.api.dtos.error;

import org.springframework.validation.FieldError;

public record DataError(
        String message
) {
    public DataError(FieldError error){
        this(error.getDefaultMessage());
    }
}
