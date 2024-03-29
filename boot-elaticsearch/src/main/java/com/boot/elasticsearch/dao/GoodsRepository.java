package com.boot.elasticsearch.dao;

import com.boot.elasticsearch.model.EsGoods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: sjx
 * @date: 2019/11/23 22:34
 * @description: 文档操作
 */
@Repository
public interface GoodsRepository extends ElasticsearchRepository<EsGoods, String> {

    /**
     * 查询价格大于等于 price 的商品
     * @param price
     * @return
     */
    List<EsGoods> findByPriceAfter(double price);
    /**
     * 查询商品价格小于等于 price的商品
     */
    List<EsGoods> findByPriceBefore(double price);

    /**
     * 查询价格等于price 的商品
     */
    List<EsGoods> findByPrice(double price);
}
