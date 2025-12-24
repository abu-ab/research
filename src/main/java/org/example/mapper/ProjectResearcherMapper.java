package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.example.entity.ProjectResearcher;
import org.example.entity.Researcher;

import java.util.List;

@Mapper
public interface ProjectResearcherMapper extends BaseMapper<ProjectResearcher> {
    @Select("SELECT r.* FROM researcher r " +
            "JOIN project_researcher pr ON r.id = pr.researcher_id " +
            "WHERE pr.project_id = #{projectId}")
    List<Researcher> getResearchersByProjectId(@Param("projectId") Long projectId);

    @Delete("DELETE FROM project_researcher WHERE project_id = #{projectId}")
    void deleteByProjectId(@Param("projectId") Long projectId);
}
