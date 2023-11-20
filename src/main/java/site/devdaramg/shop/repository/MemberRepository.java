package site.devdaramg.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.devdaramg.shop.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByName(String name);
}
