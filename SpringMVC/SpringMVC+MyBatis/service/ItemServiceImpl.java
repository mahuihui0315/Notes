package com.springMVC.demo1.service;

import com.springMVC.demo1.mapper.ItemMapper;
import com.springMVC.demo1.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<Item> getItemList(){
        return itemMapper.getItemList();
    }

    @Override
    public Item getItemById(Integer id){
        return itemMapper.getItemById(1);
    }

    @Override
    public void updateItem(Item item){
        itemMapper.updateItem(item);
    }
}
