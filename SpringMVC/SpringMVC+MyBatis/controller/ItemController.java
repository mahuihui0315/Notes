package com.springMVC.demo1.controller;

import com.springMVC.demo1.exception.MyException;
import com.springMVC.demo1.pojo.Item;
import com.springMVC.demo1.pojo.QueryVo;
import com.springMVC.demo1.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    @RequestMapping(value="updateItem",method = RequestMethod.POST)
    public String updateItem(Model model, Item item, MultipartFile pictureFile) throws IOException {
        //获取随机序列用于图片的新名字
        String name= UUID.randomUUID().toString();
        //获取图片的旧名字，以获取图片后缀
        String oldName=pictureFile.getOriginalFilename();
        String suf=oldName.substring(oldName.lastIndexOf("."));
        //存储图片到指定路径
        File file=new File("C:\\Users\\MHH\\Pictures\\ACG\\"+name+suf);
        pictureFile.transferTo(file);
        //跟新item的图片id
        item.setPictureId(name+suf);
        itemService.updateItem(item);
        //包装进Model中
        model.addAttribute("item",item);
        model.addAttribute("msg","修改成功");
        return "itemEdit";
    }

    @RequestMapping(value = "queryItem",method = {RequestMethod.POST,RequestMethod.GET})
    public String queryItem(Model model, Integer[] ids,QueryVo vo){
        if (ids!=null&&ids.length>0){
            for (Integer id:ids){
                System.out.println(id);
            }
        }
        if (vo.getItems()!=null&&vo.getItems().size()>0){
            for (Item item:vo.getItems()) {
                System.out.println(item.toString());
            }
        }
        model.addAttribute("itemList",vo.getItems());
        return "itemList";
    }

    @RequestMapping("queryVoid")
    public void queryVoid(Integer id) throws MyException {
        if (id!=1||id==null){
            throw new MyException("目标商品不存在");
        }
    }
}
