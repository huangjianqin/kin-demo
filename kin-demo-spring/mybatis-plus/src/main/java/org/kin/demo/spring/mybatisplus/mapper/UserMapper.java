package org.kin.demo.spring.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.kin.demo.spring.mybatisplus.entity.User;

/**
 * @author huangjianqin
 * @date 2021/1/10
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
