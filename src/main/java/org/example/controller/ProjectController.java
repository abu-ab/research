package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.entity.Project;
import org.example.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @GetMapping("/page")
    public IPage<Project> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code
    ) {
        Page<Project> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) wrapper.like("name", name);
        if (code != null && !code.isEmpty()) wrapper.like("code", code);
        return projectService.page(page, wrapper);
    }


    @PostMapping
    public boolean create(@RequestBody Project project) {
        return projectService.save(project);
    }

    /**
     * 更新项目
     */
    @PutMapping
    public boolean update(@RequestBody Project project) {
        return projectService.updateById(project);
    }

    /**
     * 删除项目
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return projectService.removeById(id);
    }
}
