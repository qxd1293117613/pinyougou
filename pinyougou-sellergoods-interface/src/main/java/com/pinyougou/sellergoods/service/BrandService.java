package com.pinyougou.sellergoods.service;

import java.util.List;
import java.util.Map;

import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.Result;
import com.pinyougou.pojo.TbBrand;

/**
 * 品牌接口
 * @author Administrator
 *
 */
public interface BrandService {

	public List<TbBrand> findAll();
	//品牌分页，当前页码，每页记录数
	public PageResult findPage(int pagenum,int size);
	//增加品牌
	public void add(TbBrand tbBrand);
	//根据id查询品牌
	public TbBrand show(Long id);
	//修改
	public void update(TbBrand tbBrand);
	//根据id删除选择框的品牌
	public void delete(Long []ids);
	//根据条件查询
	public PageResult findPages(int pagenum,int size,TbBrand tbBrand);
	List<Map>selectlist();
	
}
