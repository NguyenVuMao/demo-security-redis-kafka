package com.example.project2.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
  // Lấy ra biến môi trường bằng cách sử dụng annotation @Value và gán vào các biến (secretKey, jwtExpiration, refreshExpiration)
  @Value("${application.security.jwt.secret-key}")
  private String secretKey;
  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;
  // Giải mã token
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }
  // Tạo chuỗi token
  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }
  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails
  ) {
    return buildToken(extraClaims, userDetails, jwtExpiration);
  }
  // Tạo RefreshToken tương tự như tạo token và có thời gian tồn tại dài hơn
  public String generateRefreshToken(
      UserDetails userDetails
  ) {
    return buildToken(new HashMap<>(), userDetails, refreshExpiration);
  }
  // Tạo ra token
  private String buildToken(
          Map<String, Object> extraClaims, // Đối tượng map
          UserDetails userDetails, // Đối tượng là userDetails
          long expiration // Thời gian tồn tại của token
  ) {
    return Jwts
            .builder()
            .setClaims(extraClaims) // Khởi tạo 1 object payload
            // thêm các giá trị vào bên trong payload
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256) // thêm chữ ký và thuật toán dùng cho token
            .compact(); // tạo ra token
  }
// Kiểm tra xem token có hợp lệ không
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }
  // Kiểm tra token còn hạn hay không
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
  // Lấy thời gian tồn tại của token
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }
  // Giải mã token lấy ra payload
  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
  // Giải mã chữ ký bằng secretKey
  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
