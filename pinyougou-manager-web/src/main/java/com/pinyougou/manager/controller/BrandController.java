package com.pinyougou.manager.controller;

import java.util.List;
import java.util.Map;

import com.pinyougou.entity.PageResult;
import com.pinyougou.entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;



/**
 * 品牌管理的控制层的类
 * @author jt
 *
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

	@Reference
	private BrandService brandService;
	
	@RequestMapping("/findAll")
	public List<TbBrand> findAll(){
		return brandService.findAll();
	}
	
/*	@RequestMapping("/findByPage")
	public PageResult findByPage(int page,int rows){
		return brandService.findByPage(page, rows);
	}*/
	
	@RequestMapping("/save")
	public Result save(@RequestBody TbBrand brand){
		try{
			brandService.add(brand);
			
			return new Result(true,"保存成功!");
		}catch(Exception e){
			e.printStackTrace();
			return new Result(false,"保存失败!");
		}
	}
	
	@RequestMapping("/findById")
	public TbBrand findById(Long id){
		return brandService.show(id);
	}
	
	@RequestMapping("/update")
	public Result update(@RequestBody TbBrand brand){
		try{
			brandService.update(brand);
			
			return new Result(true,"修改成功!");
		}catch(Exception e){
			e.printStackTrace();
			return new Result(false,"修改失败!");
		}
	}
	
	@RequestMapping("/delete")
	public Result delete(Long[] ids){
		try{
			brandService.delete(ids);
			
			return new Result(true,"删除成功!");
		}catch(Exception e){
			e.printStackTrace();
			return new Result(false,"删除失败!");
		}
	}
	
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbBrand brand, int page, int rows){
		return brandService.findPages(page,rows,brand);
	}
	
	
	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList(){
		return brandService.selectlist();
	}
	
	
	
	
	
}
