package com.group3.mapper;

import com.github.pagehelper.Page;
import com.group3.common.dto.UserPageDTO;
import com.group3.common.entity.Admin;
import com.group3.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.sql.ConnectionBuilder;

@Mapper
public interface AdminMapper {


    /**
     * 根据username 查询管理员
     * @param username
     * @return
     */
    @Select("select * from t_admin where username = #{username}")
    Admin getByUsername(String username);

    /**
     * 学生账号分页查询
     * @param userPAgeDTO
     * @return
     */
    Page pageQuery(UserPageDTO userPAgeDTO);

    void startOrStop(User user);
}
