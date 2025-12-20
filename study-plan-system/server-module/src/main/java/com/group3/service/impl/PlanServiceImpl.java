package com.group3.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.group3.common.dto.PlanDTO;
import com.group3.common.dto.PlanPageQueryDTO;
import com.group3.common.dto.PlanUpdateDTO;
import com.group3.common.entity.Plan;
import com.group3.common.result.PageResult;
import com.group3.common.vo.PlanPageVO;
import com.group3.mapper.PlanMapper;
import com.group3.service.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PlanServiceImpl implements PlanService {
    @Autowired
    private PlanMapper planMapper;

    /**
     * 新增计划
     * @param planDTO
     * @return
     */
    @Override
    public Long create(PlanDTO planDTO) {
        Plan plan = new Plan();
        BeanUtils.copyProperties(planDTO,plan);
        plan.setUserId(planDTO.getUserId());
        plan.setCreateTime(LocalDateTime.now());
        plan.setStatus(0);
        plan.setProgress(0);
        Long planId = planMapper.insert(plan);
        return planId;
    }

    /**
     * 分页查询计划
     * @param planPageQueryDTO
     * @return
     */
    @Override
    public PageResult<PlanPageVO> pageQuery(PlanPageQueryDTO planPageQueryDTO) {
        PageHelper.startPage(planPageQueryDTO.getPage(),planPageQueryDTO.getPageSize());
        Page<PlanPageVO> page = planMapper.pageQuery(planPageQueryDTO);

        return new PageResult<>(page.getTotal(),page.getResult());
    }

    /**
     * 修改计划
     * @param planUpdateDTO
     */
    @Override
    public void update(PlanUpdateDTO planUpdateDTO) {
        planMapper.update(planUpdateDTO);
    }
}
