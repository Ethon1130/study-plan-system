package com.group3.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.group3.common.dto.LoginDTO;
import com.group3.common.dto.UserPageDTO;
import com.group3.common.entity.Admin;
import com.group3.common.exception.BusinessException;
import com.group3.common.result.PageResult;
import com.group3.common.utils.JwtUtils;
import com.group3.common.vo.AdminLoginVO;
import com.group3.common.vo.UserPageVO;
import com.group3.mapper.AdminMapper;
import com.group3.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public AdminLoginVO login(LoginDTO loginDTO) {
        Map<String,Object> claims = new HashMap<>();
        Admin admin = adminMapper.getByUsername(loginDTO.getUsername());
        if(admin == null){
            throw new BusinessException("管理员用户名或者密码错误");
        }
        if(!admin.getPassword().equals(loginDTO.getPassword())){
            throw new BusinessException("管理员用户名或者密码错误");
        }

        claims.put("username",loginDTO.getUsername());
        claims.put("password",loginDTO.getPassword());

        String token = JwtUtils.createToken(claims);

        return AdminLoginVO.builder()
                .username(loginDTO.getUsername())
                .name("管理员")
                .token(token)
                .build();
    }

    @Override
    public PageResult<UserPageVO> pageQuery(UserPageDTO userPAgeDTO) {
        PageHelper.startPage(userPAgeDTO.getPage(), userPAgeDTO.getPageSize());
        Page page = adminMapper.pageQuery(userPAgeDTO);
        return new PageResult<>(page.getTotal(),page.getResult());
    }
}
