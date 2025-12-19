package com.group3.controller.user;

import com.group3.common.dto.LoginDTO;
import com.group3.common.entity.User;
import com.group3.common.result.Result;
import com.group3.common.vo.UserLoginVO;
import com.group3.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/user/user")
@Api(tags = "用户相关接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param loginDTO
     * @return
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody LoginDTO loginDTO){
        UserLoginVO userLoginVO = userService.login(loginDTO);
        if (userLoginVO == null) {
            return Result.error("用户名或密码错误");
        }
        return Result.success(userLoginVO);
    }
}
