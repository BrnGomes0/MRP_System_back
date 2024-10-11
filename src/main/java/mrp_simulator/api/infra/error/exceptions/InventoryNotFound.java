package mrp_simulator.api.infra.error.exceptions;

public class InventoryNotFound extends RuntimeException {
    public InventoryNotFound(String message) {
        super(message);
    }
}
