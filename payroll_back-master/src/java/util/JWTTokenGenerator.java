/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import io.jsonwebtoken.Jwts;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Nimesha
 */
public class JWTTokenGenerator {

    public String createJWT(String name, String email, String subject) {
        System.out.println("jwt token gen");

        long expirationTimeMillis = 3 * 60 * 60 * 1000; // 3 hours in milliseconds

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // Calculate expiration time
        Date exp = new Date(nowMillis + expirationTimeMillis);

        String jwtToken = Jwts.builder()
                .claim("name", name)
                .claim("email", email)
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(now)
                .setExpiration(exp)
                .compact();

        return jwtToken;
    }

    public boolean validateToken(String authToken) {

        if (authToken != null) {
            String token = authToken.substring(7, authToken.length());
            System.out.println("token " + token);
            return true;
        } else {
            System.out.println("token is null");
            return false;
        }

    }
}
