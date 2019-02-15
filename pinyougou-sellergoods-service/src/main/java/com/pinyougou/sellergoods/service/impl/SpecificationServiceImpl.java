package com.pinyougou.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import com.pinyougou.entity.Spectifition;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import com.pinyougou.sellergoods.service.SpecificationService;

import com.pinyougou.entity.PageResult;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Spectifition specification) {
		TbSpecification tbpecification = specification.getSpecification();
		List<TbSpecificationOption> speclist = specification.getSpeclist();
		specificationMapper.insert(tbpecification);
		for (TbSpecificationOption tb : speclist) {
			tb.setSpecId(tbpecification.getId());
			specificationOptionMapper.insert(tb);
		}
//		 TbSpecification spec = specification.getSpecification();
//		 List<TbSpecificationOption> speclist = specification.getSpeclist();
//		  specificationMapper.insert(spec);
//		for (TbSpecificationOption option : speclist) {
//			option.setSpecId(spec.getId());
//			specificationOptionMapper.insert(option);
//		}

	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Spectifition specification){
TbSpecification specification1 = specification.getSpecification();
		//获取规格实体
		specificationMapper.updateByPrimaryKey(specification1);
		//删除规格对应的选项
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(specification1.getId());
		specificationOptionMapper.deleteByExample(example);
		//获取规格实体
		List<TbSpecificationOption> speclist = specification.getSpeclist();
		for (TbSpecificationOption option : speclist) {
			option.setSpecId(specification1.getId());
			specificationOptionMapper.insert(option);
		}




	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Spectifition findOne(Long id){
		Spectifition spectifition=new Spectifition();
		TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
		spectifition.setSpecification(tbSpecification);
		TbSpecificationOptionExample example=new TbSpecificationOptionExample();
		TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(id);
		List<TbSpecificationOption> list = specificationOptionMapper.selectByExample(example);

		spectifition.setSpeclist(list);
		return  spectifition;


	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			specificationMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}



}
