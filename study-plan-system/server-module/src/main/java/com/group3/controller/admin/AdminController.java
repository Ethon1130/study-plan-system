package com.group3.controller.admin;

import com.group3.common.dto.LoginDTO;
import com.group3.common.dto.UserPageDTO;
import com.group3.common.result.PageResult;
import com.group3.common.result.Result;
import com.group3.common.vo.AdminLoginVO;
import com.group3.common.vo.UserPageVO;
import com.group3.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "管理员相关接口")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;
    @PostMapping("/employee/login")
    @Operation(summary = "管理员登录")
    public Result login(@RequestBody LoginDTO loginDTO){
        AdminLoginVO adminLoginVO = adminService.login(loginDTO);
        return Result.success(adminLoginVO);
    }

    /**
     *  学生账号分页查询
     * @return
     */
    @GetMapping("/student/page")
    @Operation(summary = "学生账号分页查询")
    public Result<PageResult> pageQuery(@ParameterObject UserPageDTO userPAgeDTO){
        PageResult<UserPageVO> pageResult = adminService.pageQuery(userPAgeDTO);
        return Result.success(pageResult);
    }





}
