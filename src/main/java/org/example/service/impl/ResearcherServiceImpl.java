package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Researcher;
import org.example.mapper.ResearcherMapper;
import org.example.service.ResearcherService;
import org.springframework.stereotype.Service;

@Service
public class ResearcherServiceImpl extends ServiceImpl<ResearcherMapper, Researcher> implements ResearcherService {

    @Override
    public IPage<Researcher> pageResearcher(
            int pageNum,
            int pageSize,
            String name
    ) {
        Page<Researcher> page = new Page<>(pageNum, pageSize);

        QueryWrapper<Researcher> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }

        wrapper.orderByDesc("id");

        return this.page(page, wrapper);
    }
}