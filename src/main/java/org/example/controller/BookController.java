package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.dto.BookPageQuery;
import org.example.entity.Book;
import org.example.entity.Paper;
import org.example.entity.Researcher;
import org.example.service.BookService;
import org.example.service.ResearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ResearcherService researcherService;

    @PostMapping("/page")
    public IPage<Map<String, Object>> page(@RequestBody BookPageQuery query) {

        Page<Book> page = new Page<>(query.getPageNum(), query.getPageSize());
        QueryWrapper<Book> wrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(query.getTitle())) {
            wrapper.like("title", query.getTitle());
        }

        if (StringUtils.isNotBlank(query.getAuthor())) {
            List<Researcher> researchers = researcherService.list(
                    new QueryWrapper<Researcher>()
                            .like("name", query.getAuthor())
            );

            if (!researchers.isEmpty()) {
                List<String> ids = researchers.stream()
                        .map(r -> r.getId().toString())
                        .collect(Collectors.toList());

                wrapper.and(w -> {
                    for (String id : ids) {
                        w.or().like("author", id);
                    }
                });
            } else {
                wrapper.eq("id", -1);
            }
        }

        IPage<Book> bookPage = bookService.page(page, wrapper);

        // 组装 author 名字（和你论文接口一样）
        return convertAuthor(bookPage);
    }

    private IPage<Map<String, Object>> convertAuthor(IPage<Book> bookPage) {

        List<Map<String, Object>> records = bookPage.getRecords().stream().map(b -> {
            Map<String, Object> map = new HashMap<>();

            map.put("id", b.getId());
            map.put("name", b.getName());
            map.put("publisher", b.getPublisher());
            map.put("publishDate", b.getPublishDate());
            map.put("isbn", b.getIsbn());
            map.put("description", b.getDescription());

            if (StringUtils.isNotBlank(b.getAuthor())) {
                String[] authorIds = b.getAuthor().split(",");

                List<Researcher> researchers =
                        researcherService.listByIds(Arrays.asList(authorIds));

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
        result.setCurrent(bookPage.getCurrent());
        result.setSize(bookPage.getSize());
        result.setTotal(bookPage.getTotal());
        result.setRecords(records);

        return result;
    }



    @GetMapping("/list")
    public List<Book> listAll() {
        return bookService.list();
    }

    @PostMapping
    public boolean create(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping
    public boolean update(@RequestBody Book book) {
        return bookService.updateById(book);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return bookService.removeById(id);
    }
}