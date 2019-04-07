package com.springMVC.demo1.controller;

import com.springMVC.demo1.pojo.Item;
import com.springMVC.demo1.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("itemList")
    public ModelAndView itemList(ModelAndView modelAndView){
        List<Item> itemList=itemService.getItemList();
        modelAndView.addObject("itemList",itemList);
        return modelAndView;
    }

    @RequestMapping("itemEdit")
    public String itemEdit(Model model, int id){
        Item item=itemService.getItemById(id);
        model.addAttribute("item",item);
        return "itemEdit";
    }

    @RequestMapping("updateItem")
    public String updateItem(Model model, Item item){
        itemService.updateItem(item);
        model.addAttribute("item",item);
        model.addAttribute("msg","修改成功");
        return "itemEdit";
    }
}
