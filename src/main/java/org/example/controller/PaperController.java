package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.dto.PaperPageReq;
import org.example.entity.Paper;
import org.example.entity.Project;
import org.example.entity.Researcher;
import org.example.service.PaperService;
import org.example.service.ResearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/paper")
public class PaperController {
    @Autowired
    private PaperService paperService;

    @Autowired
    private ResearcherService researcherService;

    @PostMapping("/page")
    public IPage<Map<String, Object>> page(@RequestBody PaperPageReq req) {

        Page<Paper> page = new Page<>(
                req.getPageNum() == null ? 1 : req.getPageNum(),
                req.getPageSize() == null ? 10 : req.getPageSize()
        );

        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(req.getTitle())) {
            wrapper.like("title", req.getTitle());
        }
        if (StringUtils.hasText(req.getAuthor())) {
            wrapper.like("author", req.getAuthor());
        }
        if (StringUtils.hasText(req.getSource())) {
            wrapper.eq("source", req.getSource());
        }

        Page<Paper> paperPage = paperService.page(page, wrapper);

        // author ID → name
        List<Map<String, Object>> records = paperPage.getRecords().stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("title", p.getTitle());
            map.put("journal", p.getJournal());
            map.put("source", p.getSource());
            map.put("publishDate", p.getPublishDate());

            if (StringUtils.hasText(p.getAuthor())) {
                List<Long> authorIds = Arrays.stream(p.getAuthor().split(","))
                        .map(Long::valueOf)
                        .collect(Collectors.toList());

                List<Researcher> researchers = researcherService.listByIds(authorIds);
                String authorNames = researchers.stream()
                        .map(Researcher::getName)
                        .collect(Collectors.joining(", "));

                map.put("author", authorNames);
            } else {
                map.put("author", "");
            }

            return map;
        }).collect(Collectors.toList());

        Page<Map<String, Object>> result = new Page<>();
        result.setCurrent(paperPage.getCurrent());
        result.setSize(paperPage.getSize());
        result.setTotal(paperPage.getTotal());
        result.setRecords(records);

        return result;
    }

    @GetMapping("/list")
    public List<Paper> listAll() {
        return paperService.list();
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
