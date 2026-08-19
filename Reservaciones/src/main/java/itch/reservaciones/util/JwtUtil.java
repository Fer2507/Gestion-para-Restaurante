package itch.reservaciones.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtUtil {

    public static String getTokenActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;

        Object credentials = auth.getCredentials();
        if (credentials instanceof String token) {
            return token;
        }

        return null;
    }
}
