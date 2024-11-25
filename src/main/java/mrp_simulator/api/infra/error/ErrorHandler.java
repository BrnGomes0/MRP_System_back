package mrp_simulator.api.infra.error;

import mrp_simulator.api.dtos.error.DataError;
import mrp_simulator.api.infra.error.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(FirstWeekMaterialRegisted.class)
    public ResponseEntity<DataError> handlerFirstWeekMaterialCreated(FirstWeekMaterialRegisted excpetion){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DataError(excpetion.getMessage()));
    }

    @ExceptionHandler(MaterialNotFound.class)
    public ResponseEntity<DataError> handlerMaterialNotFound(MaterialNotFound excpetion){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DataError(excpetion.getMessage()));
    }

    @ExceptionHandler(InventoryNotFound.class)
    public ResponseEntity<DataError> handlerInventoryNotFound(InventoryNotFound excpetion){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DataError(excpetion.getMessage()));
    }

    @ExceptionHandler(FirstWeekInventoryRegistred.class)
    public ResponseEntity<DataError> handlerFirstWeekInventoryCreated(FirstWeekInventoryRegistred excpetion){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DataError(excpetion.getMessage()));
    }

    @ExceptionHandler(PurchasingOrderNotFound.class)
    public ResponseEntity<DataError> handlerPurchasingOrderNotFound(PurchasingOrderNotFound excpetion){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DataError(excpetion.getMessage()));
    }

    @ExceptionHandler(MaterialRegistered.class)
    public ResponseEntity<DataError> handlerMaterialRegistered(MaterialRegistered excpetion){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DataError(excpetion.getMessage()));
    }
    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<DataError> handlerUnauthorizedAccess(UnauthorizedAccessException excpetion){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DataError(excpetion.getMessage()));
    }
}
