package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.SysDict;
import org.example.mapper.SysDictMapper;
import org.example.service.SysDictService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SysDictServiceImpl
        extends ServiceImpl<SysDictMapper, SysDict>
        implements SysDictService {

    @Override
    public List<SysDict> listByType(String dictType) {
        return this.list(
                new LambdaQueryWrapper<SysDict>()
                        .eq(SysDict::getDictType, dictType)
                        .eq(SysDict::getStatus, 1)
                        .orderByAsc(SysDict::getSort)
        );
    }

    @Override
    public IPage<SysDict> pageDict(Page<SysDict> page, String dictType, String dictLabel) {
        QueryWrapper<SysDict> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(dictType)) {
            wrapper.like("dict_type", dictType);
        }
        if (StringUtils.hasText(dictLabel)) {
            wrapper.like("dict_label", dictLabel);
        }
        wrapper.orderByAsc("sort");
        return this.page(page, wrapper);
    }
}

