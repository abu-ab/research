package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.entity.Paper;
import org.example.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paper")
public class PaperController {
    @Autowired
    private PaperService paperService;

    @GetMapping("/page")
    public IPage<Paper> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String source
    ) {
        Page<Paper> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        if (title != null && !title.isEmpty()) wrapper.like("title", title);
        if (author != null && !author.isEmpty()) wrapper.like("author", author);
        if (source != null && !source.isEmpty()) wrapper.eq("source", source);
        return paperService.page(page, wrapper);
    }

    @PostMapping
    public boolean create(@RequestBody Paper paper) {
        return paperService.save(paper);
    }

    @PutMapping
    public boolean update(@RequestBody Paper paper) {
        return paperService.updateById(paper);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return paperService.removeById(id);
    }
}
