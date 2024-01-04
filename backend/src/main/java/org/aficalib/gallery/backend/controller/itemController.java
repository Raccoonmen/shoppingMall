package org.aficalib.gallery.backend.controller;

import lombok.RequiredArgsConstructor;
import org.aficalib.gallery.backend.entity.Item;
import org.aficalib.gallery.backend.repository.ItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class itemController {

    private final ItemRepository itemRepository;

    @GetMapping("/api/items")
    public List<Item> getItems() {
        List<Item> items = itemRepository.findAll();
        return items;
    }
}
