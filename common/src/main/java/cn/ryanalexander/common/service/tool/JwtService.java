package cn.ryanalexander.common.service.tool;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;

public class JwtService {

    private static final String SIG = "胸膛燃烧着愤怒的火焰\uD83D\uDD25，悔恨与痛苦鞭笞这一颗苦涩的心。";

    private static String getToken(int userId, int time){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,time);

        return JWT.create()
                .withClaim("userId", userId)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC512(SIG));
    }
    public static String getAccessToken(int userId){
        return getToken(userId,1);
    }
    public static String getRefreshToken(int userId){
        return getToken(userId,7);
    }



    public static boolean verifyToken(String token){
        try {
            JWT.require(Algorithm.HMAC512(SIG)).build().verify(token);
            return true;
            //如果不为true 那就把这个信息原封不动传给客户端完事
        }
        catch (Exception e){
            return false;
        }
    }
}
