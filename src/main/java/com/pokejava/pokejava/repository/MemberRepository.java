package com.pokejava.pokejava.repository;

import com.pokejava.pokejava.entity.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByMemberEmail(String memberEmail);  // 이메일로 회원 검색
}
