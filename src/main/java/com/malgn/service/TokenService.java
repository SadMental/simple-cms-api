package com.malgn.service;

import com.malgn.configure.aop.JwtProperties;
import com.malgn.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    @Autowired
    private JwtProperties jwtProperties;

    public String generateAccessToken(Member member){
        Date expire = calculateExpireDate(Calendar.MINUTE, jwtProperties.getExpiration());

        return createJwt(member ,expire);
    }


    public Claims getClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSecretKey())
                .requireIssuer(jwtProperties.getIssuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .requireIssuer(jwtProperties.getIssuer())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getKeyStr().getBytes(StandardCharsets.UTF_8));
    }

    private String createJwt(Member member,Date expireDate) {
        return Jwts.builder()
                .signWith(getSecretKey())
                .expiration(expireDate)//토큰의 만료 시각 설정
                .issuedAt(new Date())//발행 시각 설정
                .issuer(jwtProperties.getIssuer())//발행자 (위변조 방지용)
                .claim("loginName", member.getName())//정보 추가(key,value)
                .claim("loginRole", member.getRole().name())//정보 추가(key,value)
                .compact();
    }

    private Date calculateExpireDate(int unit, int amount) {//시간(unit), 단위(amount)
        Calendar c = Calendar.getInstance();
        c.add(unit, amount);
        return c.getTime();
    }


}
