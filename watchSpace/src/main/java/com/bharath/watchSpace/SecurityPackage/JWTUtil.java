package com.bharath.watchSpace.SecurityPackage;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;


@Component
public class JWTUtil
{
    int EXP = 1000*60*60;
    private static final String SECRET = "ThisIsMyVerySecretKeyForJwtAuthentication123456";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username)
    {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXP))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }
    public String ExtractUsername(String token)
    {
        Claims body = getClaims(token);
        return body.getSubject();
    }

    private static Claims getClaims(String token) {//used to validate the token
        Claims body = Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return body;
    }

    public Boolean validateToken(String username, UserDetails userDetails, String token)
    {
        return username.equals(userDetails.getUsername()) && getClaims(token).getExpiration().after(new Date());
    }
}
