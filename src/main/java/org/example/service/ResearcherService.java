package org.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.Researcher;

import java.util.List;

public interface ResearcherService extends IService<Researcher> {
    IPage<Researcher> pageResearcher(
            int pageNum,
            int pageSize,
            String name
    );

    List<Researcher> listByIds(List<String> ids);
}
