package org.kin.demo.spring.mybatisplus.dao;

import org.kin.demo.spring.mybatisplus.entity.User;
import org.kin.demo.spring.mybatisplus.mapper.UserMapper;
import org.kin.framework.mybatis.BaseDao;
import org.springframework.stereotype.Component;

/**
 * @author huangjianqin
 * @date 2021/1/10
 */
@Component
public class UserDao extends BaseDao<User, UserMapper> {
    public User getById(int id) {
        return mapper.selectById(id);
    }
}
