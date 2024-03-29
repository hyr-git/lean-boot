package com.boot.mybatis.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.boot.mybatis.model.Products;
import org.apache.ibatis.annotations.Param;
 
/**
 * @ fileName:ProductsDao
 * @ description:
 * @ author:Azy
 * @ createTime:2023/4/11 18:57
 * @ version:1.0.0
 */
public interface ProductsDao extends BaseMapper<Products> {
    IPage<Products> findPage(IPage<Products> iPage, @Param("ew") Wrapper<Products> wrapper);
}