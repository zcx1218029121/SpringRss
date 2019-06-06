package com.rss.zcx.demo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rss.zcx.demo.dao.UserDao;
import com.rss.zcx.demo.domin.User;
import com.rss.zcx.demo.tool.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录
 * @author DuanHu
 * @create 2019-05-22 6:28
 **/
@CrossOrigin
@Controller
@RequestMapping("/login")
public class UserController  {
    private UserDao userDao;
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        long id = userDao.validationUser(userName,password);

        if(id !=-1){
            User userForBase=userDao.findUserById(id);
            String token =getToken(userForBase);
            User user = userDao.findUserById(id);
            map.put("nickName",user.getNickName());
            map.put("token",token);
            map.put("message","登录成功");
            map.put("resultCode",200);
        }else {

            map.put("resultCode",401);
            map.put("message","用户名或者密码错误");
        }
        return map;
    }


    private String getToken(User user) {
        String token;
        token= JWT.create().withAudience(user.getId()+"").withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24+7))
                .sign(Algorithm.HMAC256(user.getPassWord()));

        return token;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
