package org.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.dto.ProjectPageQuery;
import org.example.entity.Project;
import org.example.vo.ProjectListVO;

public interface ProjectService extends IService<Project> {

    IPage<ProjectListVO> page(ProjectPageQuery query);

    boolean deleteProject(Long projectId);
}
