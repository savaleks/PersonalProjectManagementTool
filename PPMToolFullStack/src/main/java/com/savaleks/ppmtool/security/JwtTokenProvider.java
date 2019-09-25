package com.savaleks.ppmtool.security;

import com.savaleks.ppmtool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.savaleks.ppmtool.security.SecurityConst.EXPIRATION_TIME;
import static com.savaleks.ppmtool.security.SecurityConst.SECRET_KEY;

@Component
public class JwtTokenProvider {

    // generate the token
    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime()+EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String,Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("fullName", user.getFullName());

        return Jwts.builder().setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    // validate the token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException e){
            System.out.println("Invalid JWT signature.");
        } catch (MalformedJwtException e){
            System.out.println("Invalid JWT token.");
        } catch (ExpiredJwtException e){
            System.out.println("Expired JWT token.");
        } catch (UnsupportedJwtException e){
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException e){
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }

    // get user id from token
    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");
        return Long.parseLong(id);
    }
}
