package com.rss.zcx.demo.service;

import com.rss.zcx.demo.domin.Source;

import java.util.List;

/**
 * 用户订阅源服务
 *
 * @author DuanHu
 **/
public interface UserSourceService {
    /**
     * 用户添加订阅源
     * @param uid 用户id
     * @param sid 源id
     * @return sql 操作返回码
     */
    int addSource(long uid,long sid);

    /**
     * 用户删除订阅源
     * @param uid 用户id
     * @param sid  源id
     * @return sql 操作返回码
     */
    int delSource(long uid,long sid);

    /**
     * 批量删除订阅源 mybatis
     * @param uid 用户id
     * @param ids 要删除的订阅源id
     * @return 删除操作的返回码
     */
    int delSources(long uid,long...ids);

    /**
     * 查询改用户下的所有源
     * @param uid 用户id
     * @return 结果集
     */
    List<Source> getSourcesByUserId(long uid);

    /**
     * 查询改用户下的所有源
     * @param uid 用户id
     * @param limit 限制返回数量
     * @return 结果集
     */
    List<Source> getSourcesByUserId(long uid,int limit);

}
