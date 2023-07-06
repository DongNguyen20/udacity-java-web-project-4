package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {
    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemRepository itemRepository;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    public void testGetItemsByName() throws Exception {
        String itemName = "testItem";
        Item item = new Item();
        item.setName(itemName);
        item.setPrice(BigDecimal.valueOf(10.0));
        List<Item> items = Collections.singletonList(item);
        Mockito.when(itemRepository.findByName(itemName)).thenReturn(items);

        mockMvc.perform(get("/api/item/name/{name}", itemName))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetItemsByNameNotFound() throws Exception {
        String itemName = "nonExistentItem";
        Mockito.when(itemRepository.findByName(itemName)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/item/name/{name}", itemName))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetItemById() throws Exception {
        Long itemId = 1L;
        Item item = new Item();
        Mockito.when(itemRepository.findById(itemId)).thenReturn(java.util.Optional.of(item));

        mockMvc.perform(get("/api/item/{id}", itemId))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetItems() throws Exception {
        Long itemId = 1L;
        Item item = new Item();
        List<Item> items = Collections.singletonList(item);
        Mockito.when(itemRepository.findAll()).thenReturn(items);

        mockMvc.perform(get("/api/item/"))
                .andExpect(status().isOk());
    }
}
