package com.group3.mapper;

import com.github.pagehelper.Page;
import com.group3.common.dto.PlanPageQueryDTO;
import com.group3.common.dto.PlanUpdateDTO;
import com.group3.common.entity.Plan;
import com.group3.common.vo.PlanPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PlanMapper {
    /**
     * 新增计划
     * @param plan
     * @return
     */
    Long insert(Plan plan);

    /**
     * 分页查询功能实现
     * @param planPageQueryDTO
     * @return
     */
    Page<PlanPageVO> pageQuery(PlanPageQueryDTO planPageQueryDTO);

    /**
     * 更新功能
     * @param planUpdateDTO
     */
    void update(PlanUpdateDTO planUpdateDTO);

    /**
     * 批量删除计划
     * @param ids
     */
    void deleteByIds(@Param("ids") List<Long> ids);
    
    /**
     * 统计用户的总计划数
     */
    @Select("SELECT COUNT(*) FROM t_plan WHERE user_id = #{userId}")
    Integer countTotalPlans(Long userId);
    
    /**
     * 统计用户的已完成计划数
     */
    @Select("SELECT COUNT(*) FROM t_plan WHERE user_id = #{userId} AND status = 2")
    Integer countCompletedPlans(Long userId);
    
    /**
     * 统计用户的进行中计划数
     */
    @Select("SELECT COUNT(*) FROM t_plan WHERE user_id = #{userId} AND status = 1")
    Integer countInProgressPlans(Long userId);
}
