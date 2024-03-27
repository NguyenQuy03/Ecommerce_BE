package com.ecommerce.springbootecommerce.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ecommerce.springbootecommerce.constant.JWTConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

  @Value("${security.secret.key}")
  private String SECRET_KEY;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateAccessToken(UserDetails account) {
    Map<String, Object> map = new HashMap<>();
    List<String> roles = account.getAuthorities().stream()
        .map(Object::toString)
        .collect(Collectors.toList());
    map.put("roles", roles);
    return generateAccessToken(map, account);
  }

  private String generateAccessToken(
      Map<String, Object> extraClaims,
      UserDetails account) {
    return buildToken(extraClaims, account, JWTConstant.JWT_ACCESS_TOKEN_EXPIRATION);
  }

  public String generateRefreshToken(
      UserDetails account) {
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

  public List<String> extractRolesFromToken(String token) {
    Claims claims = extractAllClaims(token);
    return (List<String>) claims.get("roles");
  }

  private String buildToken(
      Map<String, Object> extraClaims,
      UserDetails account,
      long expiration) {
    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(account.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenExpired(String token) {
    try {
      return extractExpiration(token).before(new Date());
    } catch (ExpiredJwtException e) {
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
