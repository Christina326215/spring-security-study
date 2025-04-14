package com.sunnyhsu.springsecuritystudy.dao;

import com.sunnyhsu.springsecuritystudy.model.Member;
import com.sunnyhsu.springsecuritystudy.model.Role;

import java.util.List;

public interface MemberDao {

    Member getMemberById(Integer memberId);

    Member getMemberByEmail(String email);

    Integer createMember(Member member);

    List<Role> getRolesByMemberId(Integer memberId);
}
