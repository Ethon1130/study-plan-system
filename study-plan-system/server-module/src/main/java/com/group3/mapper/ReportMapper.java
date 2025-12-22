package com.group3.mapper;

import com.group3.common.vo.GanttDataVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReportMapper {
    
    @Select("SELECT COUNT(*) FROM t_plan WHERE user_id = #{userId}")
    Integer countTotalPlans(Long userId);
    
    @Select("SELECT COUNT(*) FROM t_plan WHERE user_id = #{userId} AND status = 2")
    Integer countCompletedPlans(Long userId);
    
    @Select("SELECT COUNT(*) FROM t_plan WHERE user_id = #{userId} AND status = 0")
    Integer countTodoPlans(Long userId);
    
    @Select("SELECT COUNT(*) FROM t_plan WHERE user_id = #{userId} AND status != 2 AND end_time < NOW()")
    Integer countOverduePlans(Long userId);
    
    @Select("SELECT title as taskName, FORMATDATETIME(start_time, 'yyyy-MM-dd') as start, " +
            "FORMATDATETIME(end_time, 'yyyy-MM-dd') as endDate, status " +
            "FROM t_plan WHERE user_id = #{userId} ORDER BY start_time")
    List<GanttDataVO> getGanttData(Long userId);
    
    @Select("SELECT * FROM t_plan WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<com.group3.common.entity.Plan> getAllPlansByUserId(Long userId);
}
