package com.pinyougou.search.service;

import java.util.List;
import java.util.Map;

public interface ItemSerachService {
    public Map<String,Object> search(Map searchMap);
    //导入数据
    public void importList(List list);
    //删除索引库中的信息
    public void deleteByGoodsIds(List goodsIdList);
}
