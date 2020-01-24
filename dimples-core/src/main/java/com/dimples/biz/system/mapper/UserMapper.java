package com.dimples.biz.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dimples.biz.system.po.User;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/24
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}