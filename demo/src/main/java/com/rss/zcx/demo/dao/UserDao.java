package com.rss.zcx.demo.dao;

import com.rss.zcx.demo.domin.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * 用户数据库类
 *
 * @author DuanHu
 * @create 2019-05-22 7:02
 **/
@Mapper
@Component
public interface UserDao {
    /**
     * 验证用户登录的方法如果为空就返回-1
     * @param username 用户名
     * @param passWord 用户密码
     * @return 用户id如果为空就返回-1
     */
    @Select("select ifnull( (select u_id from user where username=#{username} and password=#{passWord}),-1)")
    int validationUser(@Param("username") String username,@Param("passWord") String passWord);

    @Select("select * from user  where u_id=#{id} ")
    @Results(id = "user", value = {@Result(id = true, property = "id", column = "u_id"),
            @Result(property = "userName", column = "username"),
            @Result(property = "passWord", column = "password"),
            @Result(property = "nickName",column = "nickName")
    })
    User findUserById(@Param("id") long id);
}
