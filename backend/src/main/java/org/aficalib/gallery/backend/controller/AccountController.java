package org.aficalib.gallery.backend.controller;

import lombok.RequiredArgsConstructor;
import org.aficalib.gallery.backend.entity.Item;
import org.aficalib.gallery.backend.entity.Member;
import org.aficalib.gallery.backend.repository.ItemRepository;
import org.aficalib.gallery.backend.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final MemberRepository memberRepository;

    @PostMapping("/api/account/login")
    public int login(@RequestBody Map<String, String> params) {
        Member member = memberRepository.findByEmailAndPassword(params.get("email"),params.get("password"));

        if (member != null) {
            return member.getId();
        }
        throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
