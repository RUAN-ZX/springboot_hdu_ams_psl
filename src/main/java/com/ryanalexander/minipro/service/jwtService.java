package com.ryanalexander.minipro.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.oracle.xmlns.internal.webservices.jaxws_databinding.JavaWsdlMappingType;
import com.ryanalexander.minipro.entries.C;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;

import java.util.Calendar;

public class jwtService {

    private static String SIG = "喵喵喵~SST~乌拉~胸膛燃烧着愤怒的火焰~悔恨与痛苦鞭笞这一颗苦涩的心~";
    public static String getToken(String Tid){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);

        return JWT.create()
                .withClaim("Tid",Tid)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC512(SIG));
    }


    public static String verifyToken(String token){
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC512(SIG)).build().verify(token);
            return "true";
            //如果不为true 那就把这个信息原封不动传给客户端完事
        }
        catch (Exception e){
            return errorService.getCode(1,"密保令牌被篡改，请检查自己手机环境，此次查询无法执行");
        }
    }
}
