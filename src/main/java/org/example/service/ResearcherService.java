package org.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.Researcher;

public interface ResearcherService extends IService<Researcher> {
    IPage<Researcher> pageResearcher(
            int pageNum,
            int pageSize,
            String name
    );
}
