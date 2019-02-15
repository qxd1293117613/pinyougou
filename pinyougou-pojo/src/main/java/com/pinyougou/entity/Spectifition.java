package com.pinyougou.entity;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;

import java.io.Serializable;
import java.util.List;

//用来封装规格名称和规格选项
public class Spectifition implements Serializable {
    private TbSpecification specification;
    private List<TbSpecificationOption>speclist;

    public TbSpecification getSpecification() {
        return specification;
    }

    public void setSpecification(TbSpecification specification) {
        this.specification = specification;
    }

    public List<TbSpecificationOption> getSpeclist() {
        return speclist;
    }

    public void setSpeclist(List<TbSpecificationOption> speclist) {
        this.speclist = speclist;
    }

    @Override
    public String toString() {
        return "Spectifition{" +
                "specification=" + specification +
                ", speclist=" + speclist +
                '}';
    }
}
