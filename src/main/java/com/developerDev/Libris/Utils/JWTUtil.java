package com.developerDev.Libris.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Component
public class JWTUtil {

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;



    public String generateToken(String username){
        Map<String,Object> claims = new HashMap<>();
        return createToken.apply(claims,username);
    }

    Supplier<SecretKey>getSignInKey=()->Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    BiFunction<Map<String,Object>,String,String> createToken=(claims,subject)->{
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getSignInKey.get())
                .compact();
    };

    Function<String,Claims> extractAllClaims=token-> Jwts.parser().verifyWith(getSignInKey.get()).build().parseSignedClaims(token).getPayload();


    Function<String,Date> extractExpiration=token->extractAllClaims.apply(token).getExpiration();

    Predicate<String> isTokenExpired = token->extractExpiration.apply(token).before(new Date());

    public String extractUsername(String token){
        return extractAllClaims.apply(token).getSubject();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired.test(token);
    }


}
