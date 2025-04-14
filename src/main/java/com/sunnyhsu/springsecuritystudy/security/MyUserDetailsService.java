package com.sunnyhsu.springsecuritystudy.security;

import com.sunnyhsu.springsecuritystudy.dao.MemberDao;
import com.sunnyhsu.springsecuritystudy.model.Member;
import com.sunnyhsu.springsecuritystudy.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 從資料庫中查詢 Member 數據
        Member member = memberDao.getMemberByEmail(username);

        if (member == null){
            throw new UsernameNotFoundException("Member not found for: " + username);
        } else {
            String memberEmail = member.getEmail();
            String password = member.getPassword();

            // 權限
            List<Role> roleList = memberDao.getRolesByMemberId(member.getMemberId());
            List<GrantedAuthority> authorities = convertToAuthorities(roleList);

            // 轉換成 Spring Security 指定的 User 格式
            return new User(memberEmail,password,authorities);
        }
    }

    private List<GrantedAuthority> convertToAuthorities(List<Role> roleList){
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roleList){
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        return authorities;
    }
}
