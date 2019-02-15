package com.pinyougou.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.Result;
import com.pinyougou.pojo.TbBrandExample;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper brandMapper;
	
	@Override
	public List<TbBrand> findAll() {

		return brandMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int pagenum, int size) {
		PageHelper.startPage(pagenum,size);//自动实现分页
		Page<TbBrand> p = (Page<TbBrand>) brandMapper.selectByExample(null);
		return new PageResult(p.getTotal(),p.getResult());
	}

	public void add(TbBrand tbBrand) {
		brandMapper.insert(tbBrand);
	}
	//根据id查询数据展示在修改框
	public TbBrand show(Long id){
		return brandMapper.selectByPrimaryKey(id);
	}
  //保存更新
	public void update(TbBrand tbBrand){
		brandMapper.updateByPrimaryKey(tbBrand);
	}
   @Override
	public void delete(Long []ids) {
		for (Long id : ids) {
			 brandMapper.deleteByPrimaryKey(id);
		}

	}

	@Override
	public PageResult findPages(int pagenum, int size, TbBrand tbBrand) {
		TbBrandExample example=new TbBrandExample();
		TbBrandExample.Criteria criteria = example.createCriteria();
		if(tbBrand!=null){
			if(tbBrand.getName()!=null&&tbBrand.getName().length()>0){
				criteria.andNameLike("%"+tbBrand.getName()+"%");
			}
			if(tbBrand.getFirstChar()!=null&&tbBrand.getFirstChar().length()>0){
				criteria.andFirstCharLike("%"+tbBrand.getFirstChar()+"%");
			}
		}


		PageHelper.startPage(pagenum,size);//自动实现分页
		Page<TbBrand> p = (Page<TbBrand>) brandMapper.selectByExample(example);
		return new PageResult(p.getTotal(),p.getResult());
	}

	@Override
	public List<Map> selectlist() {
		return brandMapper.selectlist();
	}


}
