package com.rss.zcx.demo;

import com.rss.zcx.demo.dao.ItemDao;
import com.rss.zcx.demo.dao.SourceDao;
import com.rss.zcx.demo.dao.UserDao;
import com.rss.zcx.demo.domin.Iteam;
import com.rss.zcx.demo.domin.Source;
import com.rss.zcx.demo.domin.User;
import com.rss.zcx.demo.service.Crawler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
   Logger logger=LoggerFactory.getLogger("test now");

   @Autowired
    Crawler crawler;
   @Autowired
   ItemDao userDao;
    @Test
    public void contextLoads() {

    }
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test()  {
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testObj() throws Exception {

        userDao.getAllItem().forEach(iteam -> System.out.println(iteam.getDate()));

        // Assert.assertEquals("aa", operations.get("com.neo.f").getUserName());
    }
}
