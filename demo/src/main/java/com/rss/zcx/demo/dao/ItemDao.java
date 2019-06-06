package com.rss.zcx.demo.dao;

import com.fasterxml.jackson.databind.JavaType;
import com.rss.zcx.demo.domin.Iteam;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * itemdao
 *
 * @author DuanHu
 * @create 2019-05-23 0:44
 **/
@Component
@Mapper
public interface ItemDao {


    @Insert("INSERT ignore INTO  item" +
            "(link,guid,des,source,source_id,title)" +
            "VALUES" +
            "(#{link},#{guid},#{des},#{source},#{sourceId},#{title})")
    int addItem(Iteam iteam);

    /**
     *  非常不建议使用 order by rand（） 这种方法会导致mysql 每个表都查询2次效率非常低
     *  在30万数据以下可以使用
     *
     * @return 数据
     */
    @Select("SELECT * FROM item order by rand()")
    @Results(id = "ITEM",
            value = {
                    @Result(id = true, property = "u_id", column = "u_id"),
                    @Result(property = "title", column = "title"),
                    @Result(property = "sourceId", column = "source_id"),
                    @Result(property = "link", column = "link"),
                    @Result(property = "des", column = "des"),
                    @Result(property = "source", column = "source"),
                    @Result(property = "guid", column = "guid"),
                    @Result(property = "date", column = "time_creat", javaType = java.util.Date.class, jdbcType = JdbcType.TIMESTAMP)
            })
    List<Iteam> getAllItem();

    @Select("select * from item where source_id = any(select sid from user_source where uid=#{uid}) and u_id  not in(select makerread.i_id from makerread where u_id = #{uid}) order by u_id desc")
    @Results(id = "ITEMs",
            value = {
                    @Result(id = true, property = "u_id", column = "u_id"),
                    @Result(property = "title", column = "title"),
                    @Result(property = "sourceId", column = "source_id"),
                    @Result(property = "link", column = "link"),
                    @Result(property = "des", column = "des"),
                    @Result(property = "source", column = "source"),
                    @Result(property = "guid", column = "guid"),
                    @Result(property = "date", column = "time_creat", javaType = java.util.Date.class, jdbcType = JdbcType.TIMESTAMP)
            })
    List<Iteam> getAllItemByUserID(@Param("uid") long uid);

    @Update("INSERT ignore INTO  makerread" +
            "(u_id,i_id)" +
            "VALUES" +
            " (#{uid},#{iid})")
    int addRead(@Param("uid") long uid,@Param("iid") long sid);
}
