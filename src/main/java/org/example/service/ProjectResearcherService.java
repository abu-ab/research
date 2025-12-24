package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.ProjectResearcher;
import org.example.entity.Researcher;

import java.util.List;

public interface ProjectResearcherService extends IService<ProjectResearcher> {

    List<Researcher> getMembersByProjectId(Long projectId);

    boolean addMembers(Long projectId, List<Long> researcherIds);

    boolean removeMember(Long projectId, Long researcherId);
}
