package com.group3.controller.admin;

import com.group3.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Api(tags = "管理员相关接口")
@Slf4j
public class AdminController {

    @PostMapping("/employee/login")
    @ApiOperation("员工/管理员登录")
    public Result login(){
        return null;
    }







}
