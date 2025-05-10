package by.tima_zaytsev.matketplace_parser.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class JwtUtil {
    private final String ROLE = "roles";
    private final SecretKey secretAccess;
    private final SecretKey secretRefresh;
    private Map<String, String> tokens = new HashMap<>();
    @Value("${jwt.duration.access}")
    private Duration durationAccess;
    @Value("${jwt.duration.refresh}")
    private Duration durationRefresh;

    public JwtUtil(@Value("${jwt.secret.access}") String jwtAccessSecret,
                   @Value("${jwt.secret.refresh}") String jwtRefreshSecret) {
        this.secretAccess = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.secretRefresh = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put(ROLE, roles);
        Date created = new Date();
        Date expired = new Date(created.getTime() + durationAccess.toMillis());
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setExpiration(expired)
                .setIssuedAt(created)
                .signWith(secretAccess)
                .compact();

        tokens.put(userDetails.getUsername(), token);
        return token;
    }

    public String generateRefreshToken(UserDetails user) {
        Date created = new Date();
        Date expired = new Date(created.getTime() + durationRefresh.toMillis());
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(expired)
                .signWith(secretRefresh)
                .compact();
    }

    public Duration getDurationRefresh() {
        return durationRefresh;
    }
    public Duration getDurationAccess(){
        return durationAccess;
    }
    public boolean isContainToken(String accessToken){
        return  tokens.containsValue(accessToken);
    }
    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, secretAccess);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, secretRefresh);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        }
        return false;
    }
    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, secretAccess);
    }

    public String getMail(@NonNull String token){
        return getClaims(token, secretAccess).getSubject();
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, secretRefresh);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public List<String> getRoles(@NonNull String accessToken){
        return getClaims(accessToken, this.secretAccess).get(ROLE, List.class);
    }

    public String findTokenByName(String name){
        return tokens.get(name);
    }

}

