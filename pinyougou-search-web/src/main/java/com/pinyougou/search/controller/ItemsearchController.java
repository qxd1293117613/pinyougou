package com.pinyougou.search.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.search.service.ItemSerachService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
@RestController
@RequestMapping("/itemsearch")
public class ItemsearchController {

    @Reference
    private ItemSerachService itemSearchService;

    @RequestMapping("/search")
    public Map search(@RequestBody Map searchMap){

        return itemSearchService.search(searchMap);

    }

}

