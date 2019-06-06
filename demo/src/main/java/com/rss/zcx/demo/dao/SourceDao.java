package com.rss.zcx.demo.dao;

import com.rss.zcx.demo.domin.Source;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * dao
 *
 * @author DuanHu
 **/
@SuppressWarnings("unused")
@Mapper
@Component
public interface SourceDao {
    /**
     * 返回关注源的数组
     *
     * @param uid 用户id
     * @return 查询数组
     */
    @Select("select * from source  where s_id = any(select sid from user_source where uid=#{id}) ")
    @Results(id = "source", value = {
            @Result(property = "sid", column = "s_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "url", column = "url")
    })
    List<Source> getSourcesByUserId(@Param("id") int uid);
    /**
     * 返回订阅源的id
     * @return 查询数组
     */
    @Select("select sid from user_source where uid=#{id} ")
    List<Integer> getSourcesidByUserId(@Param("id") int uid);
    /**
     * 返回全部源的数组
     * @return 查询数组
     */
    @Select("select * from source")
    @Results(id = "source2", value = {
            @Result(property = "sid", column = "s_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "url", column = "url")
    })
    List<Source> getAllScores();



    /**
     * 查询源返回指定数量的
     * @param uid 用户id
     * @param limit  limit数量
     * @return 返回码
     */
    @Select("select * from source  where s_id = any(select sid from user_source where uid=#{id}) limit ")
    List<Source> getSourcesByUserIdLimit(@Param("id") int uid, int limit);

    /**
     * 添加关系表中的数据
     *
     * @param uid 用户id
     * @param sid 源id
     * @return 返回值
     */
    @Update("INSERT ignore INTO  user_source" +
            "(uid,sid)" +
            "VALUES" +
            "(#{uid},#{sid})")
    int addSource(@Param("uid") long uid, @Param("sid") long sid);

    /**
     * 批量添加用户订阅的源
     * @param uid 用户id
     * @param sid 源的可变量
     * @return sql操作返回值
     */
    @UpdateProvider(type = SocurceBulider.class, method = "addSocurces")
    int addSources(long uid, long... sid);

    /**
     * 删除源
     *
     * @param uid 用户id
     * @param sid 源id
     * @return 返回值
     */
    @Delete("DELETE FROM user_source WHERE uid =#{uid} and sid=#{sid} ")
    int delSocurce(@Param("uid") long uid, @Param("sid") long sid);

    @DeleteProvider(type = SocurceBulider.class, method = "delSocurces")
    int delSocurces(@Param("uid") long uid, @Param("sid") long... sid);


    class SocurceBulider {
        public String delSocurces(long uid, long... sid) {
            StringBuffer sql = new StringBuffer();
            sql.append("DELETE FROM user_source WHERE uid=").append(uid).append(" and sid in(");
            for (int i = 0; i < sid.length; i++) {
                if (i == sid.length - 1) {
                    sql.append(sid[i]);
                } else {
                    sql.append(sid[i]).append(",");
                }
            }
            sql.append(")");
            System.out.println(sql);
            return sql.toString();
        }

        public String addSocurces(long uid, long... sid) {
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO  user_source" +
                    "(uid,sid)" +
                    "VALUES");
            for (long s : sid) {
                sql.append("(").append(uid).append(",").append(s).append(")");
                if (s!=sid[sid.length-1]){
                    sql.append(",");
                }
            }
            System.out.println(sql);
            return sql.toString();
        }
    }
}
