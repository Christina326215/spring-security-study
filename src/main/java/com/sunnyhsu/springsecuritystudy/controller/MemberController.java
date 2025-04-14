package com.sunnyhsu.springsecuritystudy.controller;

import com.sunnyhsu.springsecuritystudy.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @PostMapping("/register")
//    public Member register(@RequestBody Member member){
//
//        // hash 原始密碼
//        String hashedPassword = passwordEncoder.encode(member.getPassword());
//        member.setPassword(hashedPassword);
//
//        // 在資料庫中插入 Member 數據
//        Integer memberId = memberDao.createMember(member);
//
//        return memberDao.getMemberById(memberId);
//    }
}

