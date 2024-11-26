package com.pokejava.pokejava.repository;

import com.pokejava.pokejava.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByMemberEmail(String memberEmail);  // 이메일로 회원 검색
}
