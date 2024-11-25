package mrp_simulator.api.infra.error.exceptions;

public class MaterialRegistered extends RuntimeException {
    public MaterialRegistered(String message) {
        super(message);
    }
}
