package com.group3.service;

import com.group3.common.dto.LoginDTO;
import com.group3.common.dto.UserPageDTO;
import com.group3.common.result.PageResult;
import com.group3.common.vo.AdminLoginVO;
import com.group3.common.vo.UserPageVO;

public interface AdminService {
    /**
     * 管理员登录
     * @param loginDTO
     * @return
     */
    AdminLoginVO login(LoginDTO loginDTO);

    /**
     * 学生账号分页查询
     * @param userPAgeDTO
     * @return
     */
    PageResult<UserPageVO> pageQuery(UserPageDTO userPAgeDTO);
}
