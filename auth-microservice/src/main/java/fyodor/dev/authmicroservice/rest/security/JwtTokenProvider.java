package fyodor.dev.authmicroservice.rest.security;


import fyodor.dev.authmicroservice.domain.exception.AccessDeniedException;
import fyodor.dev.authmicroservice.domain.user.Role;
import fyodor.dev.authmicroservice.rest.UserDto;
import fyodor.dev.authmicroservice.rest.dto.auth.JwtResponse;
import fyodor.dev.authmicroservice.service.properties.JwtProperties;
import fyodor.dev.authmicroservice.service.properties.LinkProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final LinkProperties linkProperties;
    private final UserDetailsService userDetailsService;
    private final RestTemplate restTemplate;
    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(final UUID userId, final String username, final Set<Role> roles) {
        Claims claims = Jwts.claims()
                .subject(username)
                .id(userId.toString())
                .add("roles", resolveRoles(roles)).build();
        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);

        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    private List<String> resolveRoles(Set<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public String createRefreshToken(UUID userId, String username) {
        Claims claims = Jwts.claims()
                .subject(username)
                .id(userId.toString())
                .build();
        Instant validity = Instant.now().plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);

        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshUserTokens(String refreshToken) {
        if (!validateToken(refreshToken)) {
            throw new AccessDeniedException("Ошибка валидации токена.");
        }

        JwtResponse jwtResponse = new JwtResponse();
        UUID userId = getId(refreshToken);

        // TODO [12.06.2024]: разобраться
        ResponseEntity<UserDto> response = restTemplate
                .getForEntity(linkProperties.getUserServiceLink() + "/{userId}", UserDto.class, userId);

        if (response.getStatusCode().is2xxSuccessful()) {
            UserDto userDto = response.getBody();
            jwtResponse.setId(userId);
            jwtResponse.setUsername(userDto.getUsername());
            jwtResponse.setAccessToken(createAccessToken(userId, userDto.getUsername(), userDto.getRoles()));
            jwtResponse.setRefreshToken(createRefreshToken(userId, userDto.getUsername()));

            return jwtResponse;
        } else {
            throw new AccessDeniedException("Ошибка определения пользователя");
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts
                    .parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException ignored) {}

        return false;
    }

    private UUID getId(String token) {
        String uuid = Jwts
                .parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getId();
        return UUID.fromString(uuid);
    }

    public Authentication getAuthentication(String token) {
        String userName = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        return Jwts
                .parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
