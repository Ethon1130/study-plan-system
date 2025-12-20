package com.group3.service;

import com.group3.common.dto.PlanDTO;
import com.group3.common.dto.PlanPageQueryDTO;
import com.group3.common.dto.PlanUpdateDTO;
import com.group3.common.result.PageResult;
import com.group3.common.vo.PlanPageVO;
import org.springframework.stereotype.Service;


public interface PlanService {
    /**
     * 新增计划
     * @param planDTO
     * @return
     */
    Long create(PlanDTO planDTO);

    /**
     * 分页查询计划
     * @param planPageQueryDTO
     * @return
     */
    PageResult<PlanPageVO> pageQuery(PlanPageQueryDTO planPageQueryDTO);

    /**
     * 修改计划
     * @param planUpdateDTO
     */
    void update(PlanUpdateDTO planUpdateDTO);
}
