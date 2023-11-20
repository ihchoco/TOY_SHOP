package site.devdaramg.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.devdaramg.shop.dto.MemberRequestDto;
import site.devdaramg.shop.entity.Member;
import site.devdaramg.shop.service.MemberService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/members")
    public Long joinMember(@RequestBody MemberRequestDto memberRequestDto){
        log.info("request : {}", memberRequestDto);
        return memberService.join(memberRequestDto);
    }

    //회원조회
    @GetMapping("/members/{id}")
    public Member findMemberById(@PathVariable Long id){
        log.info("request : {}", id);
        return memberService.findMemberById(id);
    }

    @GetMapping("/members")
    public List<Member> findMembers(){
        return memberService.findAllMembers();
    }
}
