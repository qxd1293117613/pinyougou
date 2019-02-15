package com.pinyougou.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbItemCatMapper;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.service.ItemPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Service
public class ItemPageServiceImpl implements ItemPageService {

    @Autowired
private FreeMarkerConfig freeMarkerConfig;
    @Value("${pagedir}")
    private String pagedir;
    @Autowired
    private TbGoodsMapper tbGoodsMapper;
    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public Boolean findbyid(Long goodsid) {
        Configuration configuration = freeMarkerConfig.getConfiguration();
        try {
            //获得模板
            Template template = configuration.getTemplate("item.ftl");
            //下面就是在给模板赋值
            Map date = new HashMap<>();
            TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(goodsid);
            date.put("goods",tbGoods);
            TbGoodsDesc tbGoodsDesc = tbGoodsDescMapper.selectByPrimaryKey(goodsid);
            date.put("goodsDesc",tbGoodsDesc);
//            //3.读取商品分类
//
//            String itemCat1 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id()).getName();
//            String itemCat2 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id()).getName();
//            String itemCat3 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName();
//            date.put("itemCat1", itemCat1);
//            date.put("itemCat2", itemCat2);
//            date.put("itemCat3", itemCat3);
            //生成的静态化列表的输出目录
            Writer writer=new FileWriter(pagedir+goodsid+".html");
            template.process(date,writer);
            writer.close();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
