//package ru.test_app.user_service.security;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class JwtService {
//
//    private final String SECRET = "secret-key";
//
//    public String generateToken(UserDetails user) {
//        return Jwts.builder()
//                .setSubject(user.getUsername())
//                .claim("roles", user.getAuthorities())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
//                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(SECRET.getBytes())
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//}
