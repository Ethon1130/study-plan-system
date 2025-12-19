package com.group3.service;

import com.group3.common.dto.LoginDTO;
import com.group3.common.entity.User;
import com.group3.common.vo.UserLoginVO;

public interface UserService {
    /**
     * 用户登录接口
     * @param loginDTO
     * @return
     */
    UserLoginVO login(LoginDTO loginDTO);
}
