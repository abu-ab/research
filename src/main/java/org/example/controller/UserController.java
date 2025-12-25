package org.example.controller;

import org.example.dto.UserRegisterDTO;
import org.example.entity.User;
import org.example.service.UserService;
import org.example.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody UserRegisterDTO dto) {
        User user = userService.getByUsername(dto.getUsername());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        String token = JwtUtil.generateToken(user.getUsername());

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", user);
        return map;
    }

    @PostMapping("/change-password")
    public boolean changePassword(@RequestBody Map<String, String> body) {
        // 从 SecurityContext 获取用户名
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) auth.getPrincipal();

        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");

        return userService.changePasswordByUsername(username, oldPassword, newPassword);
    }



    @PostMapping("/register")
    public boolean register(@RequestBody UserRegisterDTO req) {
        return userService.register(req.getUsername(), req.getPassword());
    }
}

