package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.User;

public interface UserService extends IService<User> {
    User login(String username, String password);
    boolean changePassword(Long userId, String oldPassword, String newPassword);
    boolean register(String username, String password);

    User getByUsername(String username);

    boolean changePasswordByUsername(String username, String oldPassword, String newPassword);
}

