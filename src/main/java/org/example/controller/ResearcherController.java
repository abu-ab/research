package org.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.entity.Researcher;
import org.example.service.ResearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/researcher")
public class ResearcherController {
    @Autowired
    private ResearcherService researcherService;


    @GetMapping("/page")
    public IPage<Researcher> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code
    ) {
        Page<Researcher> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Researcher> wrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like("name", name);
        }
        if (code != null && !code.isEmpty()) {
            wrapper.like("code", code);
        }
        return researcherService.page(page, wrapper);
    }

    @GetMapping("/list")
    public List<Researcher> listAll() {
        return researcherService.list();
    }

    @PostMapping
    public boolean add(@RequestBody Researcher researcher) {
        return researcherService.save(researcher);
    }

    @PutMapping
    public boolean update(@RequestBody Researcher researcher) {
        return researcherService.updateById(researcher);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return researcherService.removeById(id);
    }

    @GetMapping("/{id}")
    public Researcher detail(@PathVariable Long id) {
        return researcherService.getById(id);
    }
}
