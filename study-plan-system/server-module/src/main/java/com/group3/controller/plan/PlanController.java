package com.group3.controller.plan;

import com.group3.common.dto.PlanDTO;
import com.group3.common.dto.PlanPageQueryDTO;
import com.group3.common.dto.PlanUpdateDTO;
import com.group3.common.result.PageResult;
import com.group3.common.result.Result;
import com.group3.common.vo.PlanPageVO;
import com.group3.service.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "计划管理模块")
@RestController()
@RequestMapping("/user/plan")
@Slf4j
public class PlanController {
    @Autowired
    private PlanService planService;

    @Operation(summary = "新增计划")
    @PostMapping("/add")
    public Result addPlan(@RequestBody PlanDTO planDTO){
        Long planId = planService.create(planDTO);
        return Result.success(planId);
    }

    /**
     * 计划分页查询
     * @param planPageQueryDTO
     * @return
     */
    @Operation(summary = "计划分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(@RequestBody PlanPageQueryDTO planPageQueryDTO) {
        PageResult<PlanPageVO> pageResult = planService.pageQuery(planPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 修改计划
     */
    @Operation(summary = "修改计划")
    @PutMapping()
    public Result update(@RequestBody PlanUpdateDTO planUpdateDTO){
        planService.update(planUpdateDTO);
        return Result.success();
    }

    /**
     * 批量删除计划
     * @param ids
     * @return
     */
    @Operation(summary = "批量删除计划")
    @DeleteMapping()
    public Result deleteBatch(@RequestParam("ids") String ids){
        log.info("批量删除计划，ids: {}", ids);
        String cleanIds = ids.replaceAll("[()]", "").trim();
        List<Long> idList = java.util.Arrays.stream(cleanIds.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(java.util.stream.Collectors.toList());
        planService.deleteBatch(idList);
        return Result.success();
    }



}
