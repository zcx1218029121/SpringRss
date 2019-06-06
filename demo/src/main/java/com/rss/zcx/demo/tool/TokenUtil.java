package com.rss.zcx.demo.tool;

import com.auth0.jwt.JWT;
import javax.servlet.http.HttpServletRequest;

/**
 * JWP TOKEN 工具类
 *
 * @author DuanHu
 * @create 2019-05-30 9:57
 **/
public class TokenUtil {
    public static String getUserId(String token){
        return  JWT.decode(token).getAudience().get(0);
    }
    public static String getUserId(HttpServletRequest request){
        String token = request.getHeader("token");

        return getUserId(token);
    }
}
