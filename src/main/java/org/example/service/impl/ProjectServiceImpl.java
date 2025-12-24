package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.example.dto.ProjectPageQuery;
import org.example.entity.Project;
import org.example.entity.ProjectResearcher;
import org.example.mapper.ProjectMapper;
import org.example.mapper.ProjectResearcherMapper;
import org.example.service.ProjectResearcherService;
import org.example.service.ProjectService;

import org.example.vo.ProjectListVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    private final ProjectMapper projectMapper;
    private final ProjectResearcherMapper projectResearcherMapper;

    @Autowired
    private ProjectResearcherService projectResearcherService;



    @Override
    public IPage<ProjectListVO> page(ProjectPageQuery query) {

        // ① 项目分页
        Page<Project> page = new Page<>(query.getPageNum(), query.getPageSize());

        LambdaQueryWrapper<Project> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.hasText(query.getName()), Project::getName, query.getName());
        wrapper.like(StringUtils.hasText(query.getCode()), Project::getCode, query.getCode());
        wrapper.orderByDesc(Project::getId);

        IPage<Project> projectPage = projectMapper.selectPage(page, wrapper);

        List<Project> records = projectPage.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return new Page<>(query.getPageNum(), query.getPageSize());
        }

        // ② 收集 projectIds
        List<Long> projectIds = records.stream()
                .map(Project::getId)
                .collect(Collectors.toList());

        // ③ 一次性统计成员数
        List<Map<String, Object>> countList =
                projectResearcherMapper.selectMaps(
                        new QueryWrapper<ProjectResearcher>()
                                .select("project_id", "COUNT(researcher_id) AS cnt")
                                .in("project_id", projectIds)
                                .groupBy("project_id")
                );

        // ④ 转 Map：projectId -> memberCount
        Map<Long, Long> memberCountMap =
                countList.stream()
                        .collect(Collectors.toMap(
                                m -> ((Number) m.get("project_id")).longValue(),
                                m -> ((Number) m.get("cnt")).longValue()
                        ));

        // ⑤ 转 VO
        List<ProjectListVO> voList = records.stream().map(p -> {
            ProjectListVO vo = new ProjectListVO();
            BeanUtils.copyProperties(p, vo);
            vo.setMemberCount(memberCountMap.getOrDefault(p.getId(), 0L));
            return vo;
        }).collect(Collectors.toList());

        // ⑥ 返回分页 VO
        Page<ProjectListVO> voPage = new Page<>();
        voPage.setCurrent(projectPage.getCurrent());
        voPage.setSize(projectPage.getSize());
        voPage.setTotal(projectPage.getTotal());
        voPage.setRecords(voList);

        return voPage;
    }



    @Override
    @Transactional
    public boolean deleteProject(Long projectId) {
        projectResearcherService.remove(
                new LambdaQueryWrapper<ProjectResearcher>()
                        .eq(ProjectResearcher::getProjectId, projectId)
        );
        return this.removeById(projectId);
    }
}
