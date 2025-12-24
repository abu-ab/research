package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.entity.Award;
import org.example.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/award")
public class AwardController {

    @Autowired
    private AwardService awardService;

    @GetMapping("/page")
    public IPage<Award> page(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            String name,
            String targetType
    ) {
        Page<Award> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Award> qw = new QueryWrapper<>();

        if (StringUtils.hasText(name)) {
            qw.like("name", name);
        }
        if (StringUtils.hasText(targetType)) {
            qw.eq("target_type", targetType);
        }

        qw.orderByDesc("award_date");
        return awardService.page(page, qw);
    }

    @PostMapping
    public void save(@RequestBody Award award) {
        awardService.save(award);
    }

    @PutMapping
    public void update(@RequestBody Award award) {
        awardService.updateById(award);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        awardService.removeById(id);
    }
}
