package mrp_simulator.api.infra.error.exceptions;

public class PurchasingOrderNotFound extends RuntimeException {
    public PurchasingOrderNotFound(String message) {
        super(message);
    }
}
