package com.ecommerce.springbootecommerce.util;

import com.ecommerce.springbootecommerce.constant.JWTConstant;
import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.entity.AccountEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component  
public class JwtUtil{

    @Value("${security.secret.key}")
    private String SECRET_KEY;
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
      Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
    }

    public String generateAccessToken(UserDetails accountEntity) {
      return generateAccessToken(new HashMap<>(), accountEntity);
    }

    public String generateAccessToken(
        Map<String, Object> extraClaims,
        UserDetails account
    ) {
      return buildToken(extraClaims, account, JWTConstant.JWT_ACCESS_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(
            UserDetails account
    ) {
      return buildToken(new HashMap<>(), account, JWTConstant.JWT_REFRESH_TOKEN_EXPIRATION * 1000);
    }

    public boolean isTokenValid(String token, UserDetails account) {
        try {
            String username = extractUsername(token);
            return username.equals(account.getUsername()) && !isTokenExpired(token);
        } catch (JwtException jwtException) {
            return false;
        }
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails account,
            long expiration
    ) {
      return Jwts
              .builder()
              .setClaims(extraClaims)
              .setSubject(account.getUsername())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + expiration))
              .signWith(getSignInKey(), SignatureAlgorithm.HS256)
              .compact();
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch(ExpiredJwtException e) {
            return true;
        }
    }

    private Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
      return Jwts
          .parserBuilder()
          .setSigningKey(getSignInKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
    }

    private Key getSignInKey() {
      byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
      return Keys.hmacShaKeyFor(keyBytes);
    }

}
