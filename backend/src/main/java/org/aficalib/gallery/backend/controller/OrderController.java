package org.aficalib.gallery.backend.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aficalib.gallery.backend.dto.OrderDto;
import org.aficalib.gallery.backend.entity.Cart;
import org.aficalib.gallery.backend.entity.Item;
import org.aficalib.gallery.backend.entity.Order;
import org.aficalib.gallery.backend.repository.CartRepository;
import org.aficalib.gallery.backend.repository.ItemRepository;
import org.aficalib.gallery.backend.repository.OrderRepository;
import org.aficalib.gallery.backend.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final JwtService jwtService;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @GetMapping("/api/orders")
    public ResponseEntity getOrder(
            @CookieValue(value = "token", required = false) String token
    ) {
        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(memberId);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/api/orders")
    public ResponseEntity pushOrder(
            @RequestBody OrderDto dto,
            @CookieValue(value = "token", required = false) String token
    ) {
        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Order newOrder = new Order();
        int memberId = jwtService.getId(token);
        newOrder.setMemberId(memberId);
        newOrder.setName(dto.getName());
        newOrder.setAddress(dto.getAddress());
        newOrder.setPayment(dto.getPayment());
        newOrder.setCardNumber(dto.getCardNumber());
        newOrder.setItems(dto.getItems());
        orderRepository.save(newOrder);

        cartRepository.deleteByMemberId(memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
