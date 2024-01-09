package org.aficalib.gallery.backend.controller;

import lombok.RequiredArgsConstructor;
import org.aficalib.gallery.backend.entity.Cart;
import org.aficalib.gallery.backend.entity.Item;
import org.aficalib.gallery.backend.repository.CartRepository;
import org.aficalib.gallery.backend.repository.ItemRepository;
import org.aficalib.gallery.backend.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final JwtService jwtService;

    @GetMapping("/api/cart/items")
    public ResponseEntity getCartItems(@CookieValue(value = "token", required = false) String token){
        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        List<Cart> carts = cartRepository.findByMemberId(memberId);
        List<Integer> itemsIds = carts.stream().map(Cart::getItemId).toList();
        List<Item> items = itemRepository.findByIdIn(itemsIds);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }


    @PostMapping("/api/cart/items/{itemId}")
    public ResponseEntity pushCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value = "token", required = false) String token
    ) {
        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        if(cart == null) {
            Cart newCart = new Cart();
            newCart.setMemberId(memberId);
            newCart.setItemId(itemId);
            cartRepository.save(newCart);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/cart/items/{itemId}")
    public ResponseEntity removeCartItem(
            @PathVariable("itemId") int itemId,
            @CookieValue(value = "token", required = false) String token
    ){
        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        cartRepository.delete(cart);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
