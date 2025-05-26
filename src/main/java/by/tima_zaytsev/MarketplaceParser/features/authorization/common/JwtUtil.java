package by.tima_zaytsev.MarketplaceParser.features.authorization.common;

import by.tima_zaytsev.MarketplaceParser.common.exceptions.BadRequestException;
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
    public final SecretKey SECRET_ACCESS;
    public final SecretKey SECRET_REFRESH;
    public final SecretKey SECRET_EMAIL;
    @Value("${jwt.duration.access}")
    private Duration durationAccess;
    @Value("${jwt.duration.refresh}")
    private Duration durationRefresh;
    @Value("${jwt.duration.email}")
    private Duration durationEmail;

    public JwtUtil(@Value("${jwt.secret.access}") String jwtAccessSecret,
                   @Value("${jwt.secret.refresh}") String jwtRefreshSecret,
                   @Value("${jwt.secret.email}") String jwtEmailSecret) {
        this.SECRET_ACCESS = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.SECRET_REFRESH = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
        this.SECRET_EMAIL = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtEmailSecret));
    }

    public String generateEmailToken(String email) {
        Date created = new Date();
        Date expired = new Date(created.getTime() + durationEmail.toMillis());
        String token = Jwts.builder()
                .setSubject(email)
                .setExpiration(expired)
                .signWith(SECRET_EMAIL)
                .compact();
        return token;
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
                .signWith(SECRET_ACCESS)
                .compact();

        return token;
    }

    public String generateRefreshToken(UserDetails user) {
        Date created = new Date();
        Date expired = new Date(created.getTime() + durationRefresh.toMillis());
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(expired)
                .signWith(SECRET_REFRESH)
                .compact();
    }

    public boolean validateToken(@NonNull String token, @NonNull Key secret) {
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
        return getClaims(token, SECRET_ACCESS);
    }

    public String getMail(@NonNull String token, Key secret) throws BadRequestException {
        try {
            return getClaims(token, secret).getSubject();
        } catch (ExpiredJwtException e) {
            throw new BadRequestException("Try to send message again");
        }
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, SECRET_REFRESH);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public List<String> getRoles(@NonNull String accessToken) {
        return getClaims(accessToken, this.SECRET_ACCESS).get(ROLE, List.class);
    }
}

