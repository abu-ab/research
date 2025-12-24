package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.ProjectResearcher;
import org.example.entity.Researcher;
import org.example.mapper.ProjectResearcherMapper;
import org.example.service.ProjectResearcherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectResearcherServiceImpl extends ServiceImpl<ProjectResearcherMapper, ProjectResearcher> implements ProjectResearcherService {
    private final ProjectResearcherMapper mapper;

    public ProjectResearcherServiceImpl(ProjectResearcherMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Researcher> getMembersByProjectId(Long projectId) {
        return mapper.getResearchersByProjectId(projectId);
    }

    @Override
    @Transactional
    public boolean addMembers(Long projectId, List<Long> researcherIds) {
        if (researcherIds == null || researcherIds.isEmpty()) return false;

        // 先删除已有成员（如果需要覆盖）
        mapper.deleteByProjectId(projectId);

        List<ProjectResearcher> list = researcherIds.stream().map(id -> {
            ProjectResearcher pr = new ProjectResearcher();
            pr.setProjectId(projectId);
            pr.setResearcherId(id);
            return pr;
        }).collect(Collectors.toList());

        return saveBatch(list);
    }

    @Override
    public boolean removeMember(Long projectId, Long researcherId) {
        return lambdaUpdate()
                .eq(ProjectResearcher::getProjectId, projectId)
                .eq(ProjectResearcher::getResearcherId, researcherId)
                .remove();
    }
}
