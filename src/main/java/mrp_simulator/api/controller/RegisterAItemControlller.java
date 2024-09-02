package mrp_simulator.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegisterAItemControlller {

    @GetMapping("/Get_Teste")
    public ResponseEntity<String> getHelloWorld(){
        return ResponseEntity.status(HttpStatus.OK).body("Hello World!");
    }
}
