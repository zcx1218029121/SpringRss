package com.rss.zcx.demo.controller;

import com.auth0.jwt.JWT;
import com.rss.zcx.demo.dao.SourceDao;
import com.rss.zcx.demo.domin.Source;
import com.rss.zcx.demo.tool.TokenUtil;
import com.rss.zcx.demo.tool.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 源管管理
 *
 * @author DuanHu
 * @create 2019-05-27 14:37
 * 订阅源 subscribe
 * 取消订阅 unsubscribe
 * 返回用户订阅的源列表
 * 订阅源
 **/

@RestController
@RequestMapping("/source")
public class SourceController extends BaseController {

    private SourceDao sourceDao;


    @Autowired
    public void setSourceDao(SourceDao sourceDao) {
        this.sourceDao = sourceDao;
    }

    /**
     * 用户订阅源
     * @param request request 获得 token --> decode to id
     * @return {code:200 msg:"操作成功"}
     */
    @CrossOrigin
    @RequestMapping(value = "subscribe", method = RequestMethod.POST)
    public Map<String, Object> subscribe(HttpServletRequest request) {
        String sid = request.getParameter("sid");
        String uid = TokenUtil.getUserId(request);
        int i = sourceDao.addSource(Long.parseLong(uid), Long.parseLong(sid));
        return getResult(i);
    }

    /**
     * 用户取消订阅源
     * @param request request 获得 token  和表单参数sid --> token decode to id
     * @return {code:200 msg:"删除成功"}
     */
    @UserLoginToken
    @CrossOrigin
    @RequestMapping(value = "unsubscribe", method = RequestMethod.POST)
    public Map<String, Object> unsubscribe(HttpServletRequest request) {
        String sid = request.getParameter("sid");
        int i = sourceDao.delSocurce(Long.parseLong(TokenUtil.getUserId(request))
                , Long.parseLong(sid));

        return getResult(i);
    }



    /**
     * get sources which user was subscribed  by userId
     * @param request httpRequest
     * @return {source:[],username:"username",img:""}
     */
    @UserLoginToken
    @CrossOrigin
    @RequestMapping(value = "id", method = RequestMethod.POST)
    private HashMap<String, Object> getUserSourceIds(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>(1);
        List<Integer> list = sourceDao.getSourcesidByUserId(Integer.parseInt(TokenUtil.getUserId(request)));
        map.put("source", list);
        return map;
    }
    @UserLoginToken
    @CrossOrigin
    @RequestMapping(value = "", method = RequestMethod.GET)
    private HashMap<String, Object> getUserSources(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>(1);
        List<Source> list = sourceDao.getSourcesByUserId(Integer.parseInt(TokenUtil.getUserId(request)));
        map.put("source", list);
        return map;
    }

    /**
     * 添加源默认添加到数据库的值
     * @param request
     * @return
     */
    @UserLoginToken
    @CrossOrigin
    @RequestMapping(value = "", method = RequestMethod.POST)
    private HashMap<String, Object> addSource(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>(1);
        List<Source> list = sourceDao.getSourcesByUserId(Integer.parseInt(TokenUtil.getUserId(request)));
        map.put("source", list);
        return map;
    }

   /* @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }*/

}
