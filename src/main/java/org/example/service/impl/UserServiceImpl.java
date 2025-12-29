package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getByUsername(String username) {
        return lambdaQuery()
                .eq(User::getUsername, username)
                .one();
    }

    @Override
    public boolean register(String username, String password) {

        Long count = this.lambdaQuery()
                .eq(User::getUsername, username)
                .count();

        if (count > 0) {
            throw new RuntimeException("用户名已存在");
        }

        String encodedPwd = passwordEncoder.encode(password);

        User user = new User();
        user.setRole("USER");
        user.setUsername(username);
        user.setPassword(encodedPwd);
        user.setCreateTime(LocalDateTime.now());

        return this.save(user);
    }

    @Override
    public User login(String username, String password) {
        User user = lambdaQuery().eq(User::getUsername, username).one();
        if (user != null && new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    @Transactional
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = getById(userId);
        if (user == null) return false;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(oldPassword, user.getPassword())) {
            return false;
        }
        user.setPassword(encoder.encode(newPassword));
        return updateById(user);
    }

    @Override
    public boolean changePasswordByUsername(String username, String oldPassword, String newPassword) {
        User user = this.getOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) return false;

        // 校验旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }

        // 更新新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        return this.updateById(user);
    }


    @Override
    public IPage<User> pageUser(int pageNum, int pageSize, String username) {

        Page<User> page = new Page<>(pageNum, pageSize);

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (username != null && !"".equals(username)) {
            wrapper.like("username", username);
        }

        return this.page(page, wrapper);
    }
}
