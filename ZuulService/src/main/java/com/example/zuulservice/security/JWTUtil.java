package com.example.zuulservice.security;

import com.example.zuulservice.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;
@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;
    private Clock clock = DefaultClock.INSTANCE;

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            System.out.println("----------------------");
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("JWT expired"+ ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("Token is null, empty or only whitespace"+ ex.getMessage());
        } catch (MalformedJwtException ex) {
            System.out.println("JWT is invalid"+ ex);
        } catch (UnsupportedJwtException ex) {
            System.out.println("JWT is not supported"+ ex);
        } catch (SignatureException ex) {
            System.out.println("Signature validation failed");
        }

        return false;
    }

    //taked password in considration for test only
    public String getAccessToken(User user){
        final Date createdDate = clock.now();
        final Date expirationDate = new Date(createdDate.getTime() + 60 * 1000);
//        final Date expirationDate = new Date(createdDate.getTime() + 1);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    private Claims getAllClaimsFromToken(String token) {
        token = token.replace("Bearer ", "");
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
}
