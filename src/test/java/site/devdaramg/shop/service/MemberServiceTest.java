package site.devdaramg.shop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.devdaramg.shop.dto.MemberRequestDto;
import site.devdaramg.shop.entity.Address;
import site.devdaramg.shop.entity.Cart;
import site.devdaramg.shop.entity.Member;
import site.devdaramg.shop.repository.CartRepository;
import site.devdaramg.shop.repository.MemberRepository;

import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CartRepository cartRepository;

    @BeforeEach
    public void clean(){
        memberRepository.deleteAll();
        cartRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입_테스트")
    void MemberServiceTest1(){
        //given
        MemberRequestDto requestMember = MemberRequestDto.builder()
                .name("박기연")
                .address(new Address("경기도", "산본로 299", "10213"))
                .build();

        //when
        memberService.join(requestMember);

        //then
        Assertions.assertThat(memberRepository.count()).isEqualTo(1L);

        Cart cart = cartRepository.findAll().get(0);
        System.out.println(cart);

    }

    @Test
    @DisplayName("멤버찾기_테스트")
    void MemberServiceTest2(){
        //given
        MemberRequestDto requestMember = MemberRequestDto.builder()
                .name("박기연")
                .address(new Address("경기도", "산본로 299", "10213"))
                .build();

        //when
        memberService.join(requestMember);
        Member findMember = memberService.findMemberByName(requestMember.getName());

        //then
        Assertions.assertThat(findMember.getName()).isEqualTo("박기연");
    }

    @Test
    @DisplayName("중복회원 등록_실패테스트")
    void MemberServiceTest3(){
        //given
        MemberRequestDto requestMember = MemberRequestDto.builder()
                .name("박기연")
                .address(new Address("경기도", "산본로 299", "10213"))
                .build();

        //when
        memberService.join(requestMember);

        //then
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            memberService.join(requestMember);
        });

        Assertions.assertThat(illegalArgumentException.getMessage()).isEqualTo("회원중에 동일한 이름을 가진 중복이 존재합니다");
    }
}