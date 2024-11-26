package com.pokejava.pokejava.service;

import com.pokejava.pokejava.dto.MemberForm;
import com.pokejava.pokejava.dto.ResponseDTO;
import com.pokejava.pokejava.entity.Member;
import com.pokejava.pokejava.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;


@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public ResponseDTO signIn(MemberForm form) {
        try {
            String email = form.getMemberEmail();
            String password = form.getMemberPassword();

            // 이메일로 회원 조회
            Member member = memberRepository.findByMemberEmail(email);

            if (member == null) {
                return new ResponseDTO(false, "이메일을 찾을 수 없습니다.");
            }
            // 저장된 비밀번호와 입력된 비밀번호 비교
            if (!Objects.equals(password, member.getMemberPassword())) {
                return new ResponseDTO(false, "비밀번호가 일치하지 않습니다.");
            }

            return new ResponseDTO(true, "로그인 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(false, "로그인 실패");
        }
    }

    @Transactional
    public ResponseDTO signUp(MemberForm form) {
        try {
            String email = form.getMemberEmail();
            // 이메일로 회원 조회
            Member member = memberRepository.findByMemberEmail(email);
            if (member != null) {
                return new ResponseDTO(false, "이미 존재하는 이메일입니다.");
            }

            member = form.toEntity(); // DTO -> Entity 변환
            memberRepository.save(member);   // 회원 저장

            return new ResponseDTO(true, "회원가입 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(false, "회원가입 실패");
        }
    }

    // 이메일로 회원의 memberId를 조회하는 메서드
    public Long getMemberIdByEmail(String email) {
        Member member = memberRepository.findByMemberEmail(email);
        if (member != null) {
            return member.getMemberId();  // memberId 반환
        } else {
            return null;  // 이메일에 해당하는 회원이 없으면 null 반환
        }
    }
}