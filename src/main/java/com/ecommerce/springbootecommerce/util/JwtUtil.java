package com.ecommerce.springbootecommerce.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.constant.SystemConstant;
import com.ecommerce.springbootecommerce.entity.AccountEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component  
public class JwtUtil {
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
    }

    public String generateAccessToken(AccountEntity accountEntity) {
      return generateAccessToken(new HashMap<>(), accountEntity);
    }

    public String generateAccessToken(
        Map<String, Object> extraClaims,
        AccountEntity accountEntity
    ) {
      return buildToken(extraClaims, accountEntity, SystemConstant.JWT_ACCESS_TOKEN_EXPIRATION);
    }

    public String generateRefreshToken(
        AccountEntity accountEntity
    ) {
      return buildToken(new HashMap<>(), accountEntity, SystemConstant.JWT_REFRESH_TOKEN_EXPIRATION);
    }
    
    public boolean isTokenValid(String token, AccountEntity account) {
        String username = extractUsername(token);
        return (username.equals(account.getUsername())) && !isTokenExpired(token);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            AccountEntity accountEntity,
            long expiration
    ) {
      return Jwts
              .builder()
              .setClaims(extraClaims)
              .setSubject(accountEntity.getUsername())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + expiration))
              .signWith(getSignInKey(), SignatureAlgorithm.HS256)
              .compact();
    }

    private boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
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
      byte[] keyBytes = Decoders.BASE64.decode(SystemConstant.JWT_SECRET_KEY);
      return Keys.hmacShaKeyFor(keyBytes);
    }

}
