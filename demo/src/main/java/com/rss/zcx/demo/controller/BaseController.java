package com.rss.zcx.demo.controller;

import java.util.HashMap;

/**
 * Controller
 *
 * @author DuanHu
 * @create 2019-05-30 16:26
 **/
public class BaseController {
    HashMap<String, Object> getResult(int i) {
        HashMap<String, Object> map = new HashMap<>(2);
        if (i > 0) {
            map.put("code", 200);
            map.put("msg", "操作成功");
        } else {
            map.put("code", 500);
            map.put("msg", "操作失败");
        }
        return map;
    }
}

