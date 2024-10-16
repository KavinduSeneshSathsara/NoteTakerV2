package lk.ijse.gdse.aad68.NoteCollectorV2.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    String refreshToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}