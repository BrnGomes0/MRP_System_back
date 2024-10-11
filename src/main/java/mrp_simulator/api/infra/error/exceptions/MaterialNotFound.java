package mrp_simulator.api.infra.error.exceptions;

public class MaterialNotFound extends RuntimeException{
    public MaterialNotFound(String message){
        super(message);
    }
}
