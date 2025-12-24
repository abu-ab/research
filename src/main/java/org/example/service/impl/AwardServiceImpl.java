package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Award;
import org.example.mapper.AwardMapper;
import org.example.service.AwardService;
import org.springframework.stereotype.Service;

@Service
public class AwardServiceImpl extends ServiceImpl<AwardMapper, Award>
        implements AwardService {
}
