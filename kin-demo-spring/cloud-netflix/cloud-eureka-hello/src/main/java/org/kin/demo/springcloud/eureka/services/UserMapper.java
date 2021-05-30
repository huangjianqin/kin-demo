package org.kin.demo.springcloud.eureka.services;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author huangjianqin
 * @date 2020/9/23
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
