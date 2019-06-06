package com.rss.zcx.demo.service.impl;

import com.rss.zcx.demo.dao.SourceDao;
import com.rss.zcx.demo.domin.Source;
import com.rss.zcx.demo.service.UserSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户数据实现类
 * @author DuanHu
 **/
@Service
public class UserSourceServiceImpl  implements UserSourceService {
    private SourceDao sourceDao;
    @Override
    public int addSource(long uid, long sid) {
        return sourceDao.addSource(uid,sid);
    }

    @Override
    public int delSource(long uid, long sid) {
        return sourceDao.delSocurce(uid,sid);
    }

    @Override
    public int delSources(long uid, long... ids) {
        return sourceDao.delSocurces(uid,ids);
    }

    @Override
    public List<Source> getSourcesByUserId(long uid) {
        return sourceDao.getSourcesByUserId((int)uid);
    }

    @Override
    public List<Source> getSourcesByUserId(long uid, int limit) {
        return sourceDao.getSourcesByUserIdLimit((int)uid,limit);
    }


    @Autowired
    public void setSourceDao(SourceDao sourceDao) {
        this.sourceDao = sourceDao;
    }
}
