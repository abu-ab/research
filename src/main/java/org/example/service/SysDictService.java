package org.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.SysDict;
import org.example.mapper.SysDictMapper;

import java.util.List;


public interface SysDictService extends IService<SysDict> {
    List<SysDict> listByType(String dictType);

    IPage<SysDict> pageDict(Page<SysDict> page, String dictType, String dictLabel);

}
