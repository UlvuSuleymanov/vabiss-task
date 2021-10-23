package com.vabiss.task.service.impl;

import com.vabiss.task.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    String SECRET_KEY = "AAAAAAAAAAAAAAAAAAA";

    @Override
    public String generateAccessToken(Long id) {
        Date start  = new Date();
        Date end = new Date(System.currentTimeMillis() + 3_600_000);

        return Jwts.builder()
                .setSubject(id.toString())
                .setExpiration(end)
                .setIssuedAt(start)
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }

    @Override
    public Long getIdFromToken(String token) {
        System.out.println(token);
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        String id = claims.getSubject();
        return Long.valueOf(id);
    }
}
