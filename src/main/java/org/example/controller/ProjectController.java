package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.dto.AddMembersRequest;
import org.example.dto.ProjectPageQuery;
import org.example.dto.RemoveMemberRequest;
import org.example.entity.Project;
import org.example.entity.Researcher;
import org.example.service.ProjectResearcherService;
import org.example.service.ProjectService;
import org.example.vo.ProjectListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectResearcherService projectResearcherService;

    @PostMapping("/page")
    public IPage<ProjectListVO> page(@RequestBody ProjectPageQuery query) {
        return projectService.page(query);
    }

    @GetMapping("/list")
    public List<Project> listAll() {
        return projectService.list();
    }


    @PostMapping
    public Project create(@RequestBody Project project) {
         projectService.save(project);
         return project;
    }

    @PutMapping
    public Project update(@RequestBody Project project) {
         projectService.updateById(project);
         return project;
    }


    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return projectService.deleteProject(id);
    }

    @GetMapping("/{projectId}/members")
    public List<Researcher> getProjectMembers(@PathVariable Long projectId) {
        return projectResearcherService.getMembersByProjectId(projectId);
    }

    @PostMapping("/members")
    public boolean addProjectMembers(@RequestBody AddMembersRequest request) {
        return projectResearcherService.addMembers(request.getProjectId(), request.getResearcherIds());
    }


    @PostMapping("/members/remove")
    public boolean removeProjectMember(@RequestBody RemoveMemberRequest request) {
        return projectResearcherService.removeMember(request.getProjectId(), request.getResearcherId());
    }

}
