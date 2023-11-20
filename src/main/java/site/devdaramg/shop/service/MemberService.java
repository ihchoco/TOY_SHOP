package site.devdaramg.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.devdaramg.shop.dto.MemberRequestDto;
import site.devdaramg.shop.entity.Cart;
import site.devdaramg.shop.entity.Member;
import site.devdaramg.shop.repository.CartRepository;
import site.devdaramg.shop.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    //회원 가입
    public Long join(MemberRequestDto memberRequestDto){
        validDuplicateName(memberRequestDto);
        Member request = Member.builder()
                .id(memberRequestDto.getId())
                .name(memberRequestDto.getName())
                .address(memberRequestDto.getAddress())
                .build();

        Cart cart = Cart.builder()
                .member(request)
                .build();

        Long memberId = memberRepository.save(request).getId();

        cartRepository.save(cart);

        return memberId;
    }

    //회원 조회 By Id
    public Member findMemberById(Long memberId){
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("찾는 회원이 없습니다");
        });
        return findMember;
    }

    //회원 조회 By Name
    public Member findMemberByName(String memberName){
        Member findMember = memberRepository.findByName(memberName).orElseThrow(() -> {
            throw new IllegalArgumentException("찾는 회원이 없습니다");
        });
        return findMember;
    }


    //이름 중복 여부 확인
    public void validDuplicateName(MemberRequestDto memberRequestDto){
        memberRepository.findByName(memberRequestDto.getName()).ifPresent(x -> {
            throw new IllegalArgumentException("회원중에 동일한 이름을 가진 중복이 존재합니다");
        });
    }

    public List<Member> findAllMembers(){
        return memberRepository.findAll();
    }


}
