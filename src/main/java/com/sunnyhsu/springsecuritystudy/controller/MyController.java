package com.sunnyhsu.springsecuritystudy.controller;

import com.sunnyhsu.springsecuritystudy.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class MyController {

    @Autowired
    private MyService myService;

    @GetMapping("/hello")
    public String hello(Authentication authentication) {

        // 取得使用者的帳號
        String username = authentication.getName();

        // 取得使用者的權限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return "Hello " + username + "! 你的權限為: " + authorities;
    }

//    @PostMapping("/welcome")
//    public String welcome() {
//        return "Welcome!";
//    }

    @RequestMapping("/getMovie")
    public String getMovie() {
        System.out.println("執行 MyController 的 getMovie 方法");

        myService.getMovie();

        return "成功取得電影";
    }

    @RequestMapping("/deleteMovie")
    public String deleteMovie() {
        System.out.println("執行 MyController 的 deleteMovie 方法");

        myService.deleteMovie();

        return "成功刪除電影";
    }
}
