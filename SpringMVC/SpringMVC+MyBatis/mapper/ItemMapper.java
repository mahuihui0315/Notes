package com.springMVC.demo1.mapper;

import com.springMVC.demo1.pojo.Item;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ItemMapper {
    List<Item> getItemList();
    Item getItemById(Integer id);
    void updateItem(Item item);
}
