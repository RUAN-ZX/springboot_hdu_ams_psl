package cn.ryanalexander.psl.service.tool;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;

public class JwtService {

    private static final String SIG = "胸膛燃烧着愤怒的火焰\uD83D\uDD25，悔恨与痛苦鞭笞这一颗苦涩的心。";

    private static String getToken(String Tid, int time){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,time);

        return JWT.create()
                .withClaim("Tid",Tid)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC512(SIG));
    }
    public static String getAccessToken(String Tid){
        return getToken(Tid,1);
    }
    public static String getRefreshToken(String Tid){
        return getToken(Tid,7);
    }



    public static String verifyToken(String token){
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC512(SIG)).build().verify(token);
            return "true";
            //如果不为true 那就把这个信息原封不动传给客户端完事
        }
        catch (Exception e){
            return ErrorService.getCode(1,"密保令牌被篡改，请检查自己手机环境，此次查询无法执行").toJSONString();
        }
    }
}
