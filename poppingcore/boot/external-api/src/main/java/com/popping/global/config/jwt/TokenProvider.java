package com.popping.global.config.jwt;

import com.popping.data.member.entity.role.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Period;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtProperties jwtProperties;

    public String createRefreshToken(Long memberPk, Role role){
        return createToken(Token.REFRESH_TOKEN, memberPk, role);
    }

    public String createAccessToken(Long memberPk, Role role){
        return createToken(Token.ACCESS_TOKEN, memberPk, role);
    }

    private String createToken(Token token, Long memberPk, Role role){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(new Date(new Date().getTime() + token.getExpirationMs()))
                .setSubject(token.getName())
                .claim("id", memberPk)
                .claim("role", role.toString())
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValidToken(String jwtToken){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(jwtToken);
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    public boolean isExpired(String jwtToken){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(jwtToken);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    public Authentication getAuthentication(String jwtToken){
         Role role = Role.valueOf(getRole(jwtToken));

         return createAuthentication(jwtToken, role);
    }

    private Authentication createAuthentication(String jwtToken, Role role){
        String memberPk = getMemberPk(jwtToken);

        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(role.getRoleName()));
        User user = new User(memberPk, "", authorities);

        return new UsernamePasswordAuthenticationToken(user, jwtToken, authorities);
    }

    public Claims getClaim(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getKey().getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public String getMemberPk(String jwtToken){
        Claims claims = getClaim(jwtToken);
        return String.valueOf(claims.get("id"));
    }

    public String getRole(String jwtToken){
        Claims claims = getClaim(jwtToken);
        return String.valueOf(claims.get("role"));
    }

    public Date getExpirationTime(String jwtToken){
        Claims claim = getClaim(jwtToken);
        return claim.getExpiration();
    }

    public String getSubject(String jwtToken){
        Claims claim = getClaim(jwtToken);
        return claim.getSubject();
    }

    public boolean isRefreshToken(String jwtToken){
        return getSubject(jwtToken).equals(Token.REFRESH_TOKEN.getName());
    }

    @Getter
    private enum Token {
        REFRESH_TOKEN(Period.ofMonths(6).toTotalMonths() * Duration.ofDays(30L).toMillis(), "RefreshToken"),
        ACCESS_TOKEN(Duration.ofDays(1).toMillis(), "AccessToken");

        private final long expirationMs;
        private final String name;

        Token(long expirationMs, String name) {
            this.expirationMs = expirationMs;
            this.name = name;
        }
    }
}
