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

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    @Override
    public List<Researcher> listByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return this.baseMapper.selectBatchIds(ids);
    }


    @Override
    public boolean saveResearcher(Researcher researcher) {
        long count = this.count(
                new QueryWrapper<Researcher>()
                        .eq("code", researcher.getCode())
        );

        if (count > 0) {
            throw new RuntimeException("科研人员工号已存在");
        }

        return this.save(researcher);
    }

    @Override
    public boolean updateResearcher(Researcher researcher) {
        long count = this.count(
                new QueryWrapper<Researcher>()
                        .eq("code", researcher.getCode())
                        .ne("id", researcher.getId())
        );

        if (count > 0) {
            throw new RuntimeException("科研人员工号已存在");
        }

        return this.updateById(researcher);
    }


}