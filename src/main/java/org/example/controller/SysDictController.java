package org.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.entity.SysDict;
import org.example.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;

    @GetMapping("/{type}")
    public List<SysDict> listByType(@PathVariable String type) {
        return sysDictService.listByType(type);
    }

    @GetMapping("/page")
    public IPage<SysDict> page(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize,
            @RequestParam(required = false) String dictType,
            @RequestParam(required = false) String dictLabel
    ) {
        Page<SysDict> page = new Page<>(pageNum, pageSize);
        return sysDictService.pageDict(page, dictType, dictLabel);
    }

    @PostMapping
    public boolean save(@RequestBody SysDict dict) {
        return sysDictService.save(dict);
    }

    /**
     * 修改字典
     */
    @PutMapping
    public boolean update(@RequestBody SysDict dict) {
        return sysDictService.updateById(dict);
    }

    /**
     * 删除字典
     */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return sysDictService.removeById(id);
    }
}

