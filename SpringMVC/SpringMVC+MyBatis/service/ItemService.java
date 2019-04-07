package com.springMVC.demo1.service;


import com.springMVC.demo1.pojo.Item;

import java.util.List;

public interface ItemService {
    List<Item> getItemList();
    Item getItemById(Integer id);
    void updateItem(Item item);
}
