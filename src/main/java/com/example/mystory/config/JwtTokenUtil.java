package com.example.mystory.config;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final long serialVersionUID = 2525325252325L;
    @Value("${value.secret}")
    private String secret;
    @Value("${value.expire-time}")
    private int expireTime;

    /**
     * Generate TOKEN
     *
     * @param authentication
     * @return
     */
    public String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expireTime);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Validate token
     *
     * @param authToken
     * @return
     */
    public boolean validateToken(String authToken, UserDetails userDetails) {
        try {
            Claims claims = getAllClaimsFromToken(authToken);
            final String username = getUsernameFromToken(claims.getSubject());
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(authToken));
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    /**
     * Validate token
     *
     * @param authToken
     * @return
     */
    public boolean validateToken(String authToken) {
        try {
            Claims claims = getAllClaimsFromToken(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
