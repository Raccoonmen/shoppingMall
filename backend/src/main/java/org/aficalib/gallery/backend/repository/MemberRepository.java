package org.aficalib.gallery.backend.repository;

import org.aficalib.gallery.backend.entity.Item;
import org.aficalib.gallery.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByEmailAndPassword(String email, String password);
}
