package com.popping.domain.admin.controller;

import com.popping.domain.admin.dto.AdminLoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/admin")
@Controller
public class AdminController {
    @GetMapping("/login-page")
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AdminLoginDto adminLoginDto) {
        log.info(adminLoginDto.getUsername());
        log.info(adminLoginDto.getPassword());
        return "home";
    }
}
