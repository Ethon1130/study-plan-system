package com.group3.mapper;

import com.group3.common.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 根据username搜索用户
     * @param username
     * @return
     */

    public User selectByUsername(String username);


    void insert(User user);
}
