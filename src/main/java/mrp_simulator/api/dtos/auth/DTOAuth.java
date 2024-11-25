package mrp_simulator.api.dtos.auth;

import org.springframework.security.oauth2.jwt.Jwt;

public record DTOAuth(

        String userName,
        String nameNetworkUser
) {
    public DTOAuth(Jwt jwt){
        this(
            jwt.getClaimAsString("name"),
                jwt.getClaimAsString("preferred_username")
        );
    }
}
