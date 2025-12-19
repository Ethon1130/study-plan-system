package com.group3.service.impl;

import com.group3.common.dto.LoginDTO;
import com.group3.common.entity.User;
import com.group3.common.exception.BusinessException;
import com.group3.common.utils.JwtUtils;
import com.group3.common.vo.UserLoginVO;
import com.group3.mapper.UserMapper;
import com.group3.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserLoginVO login(LoginDTO loginDTO) {
        User user = userMapper.selectByUsername(loginDTO.getUsername());

        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() == 0) {
            throw new BusinessException("账号被禁用");
        }
        if (!loginDTO.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("userName", user.getUsername());
        String token = JwtUtils.createToken(claims);
        log.info("用户成功登录：{}", token);
        return UserLoginVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();
    }
}