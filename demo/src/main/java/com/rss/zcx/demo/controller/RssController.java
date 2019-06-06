package com.rss.zcx.demo.controller;

import com.auth0.jwt.JWT;
import com.github.pagehelper.PageHelper;
import com.rss.zcx.demo.dao.ItemDao;
import com.rss.zcx.demo.dao.SourceDao;
import com.rss.zcx.demo.domin.Iteam;
import com.rss.zcx.demo.domin.Pager;
import com.rss.zcx.demo.tool.TokenUtil;
import com.rss.zcx.demo.tool.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;

import java.util.*;


/**
 * Controller 
 *
 * @author DuanHu
 * @create 2019-05-14 8:27
 **/
@RestController
@RequestMapping("/rss")
public class RssController extends BaseController {
    /*
      CACHE effective time
     */
    private static long CACHE_TIME = 60;
    private RedisTemplate redisTemplate;
    private SourceDao sourceDao;

    private ItemDao itemDao;

    @UserLoginToken
    @CrossOrigin
    @RequestMapping("")
    public Pager getRss(HttpServletRequest request) {
        //手动添加缓存 key为用户的id
        String ps = request.getParameter("p");
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        PageHelper.startPage(Integer.parseInt(ps), 5);
        List<Iteam> data = itemDao.getAllItemByUserID(Long.parseLong(userId));
        return new Pager(data.size(), data);
    }

    @Autowired
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @CrossOrigin
    @RequestMapping("read")
    public Map<String, Object> read(HttpServletRequest request){
        String sid = request.getParameter("i_id");
        int i =itemDao.addRead(Long.parseLong(TokenUtil.getUserId(request)),Long.parseLong(sid));
        return getResult(i);
    }

    /**
     * 发现页api 逻辑分页
     * @param request http request
     * @return json map
     */
    @CrossOrigin
    @RequestMapping("find")
    public Map<String, Object> getFindSourceDao(HttpServletRequest request) {
        List<Iteam> data;
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("token");
        String userId = JWT.decode(token).getAudience().get(0);
        String ps = request.getParameter("p");
       // ValueOperations<String, List<Iteam> > operations = redisTemplate.opsForValue();
       // if (redisTemplate.hasKey(RedisKeyMap.FIND.name())) {
      //      data = operations.get(RedisKeyMap.FIND.name());
      //      System.out.println("从缓存中恢复");
      //  }else {

      //      System.out.println("击穿缓存了");
       // }
        PageHelper.startPage(Integer.parseInt(ps), 5);
        data = itemDao.getAllItem();
        map.put("pager", new Pager(data.size(), data));
        List<Integer> source = sourceDao.getSourcesidByUserId(Integer.parseInt(userId));
        map.put("source", source);
        // add cache in here
        //operations.set(RedisKeyMap.FIND.name(), data, CACHE_TIME, TimeUnit.SECONDS);
        return map;
    }



    @Autowired
    public void setSourceDao(SourceDao sourceDao) {
        this.sourceDao = sourceDao;
    }
}
