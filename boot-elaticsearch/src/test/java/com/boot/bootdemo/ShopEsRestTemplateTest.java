package com.boot.bootdemo;


import com.boot.elasticsearch.BootESApplication;
import com.boot.elasticsearch.dao.ShopEsRepository;
import com.boot.elasticsearch.model.ShopEs;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import javax.annotation.Resource;

@SpringBootTest(classes = BootESApplication.class)
@Slf4j
class ShopEsRestTemplateTest {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Resource
    private ShopEsRepository shopEsRepository;

    @org.junit.jupiter.api.Test
    void contextLoads() {
    }

    @Test
    public void testSaveShop(){

        ShopEs shopEs = new ShopEs();
        shopEs.setShopName("蒋宗霖无耻之徒");
        shopEs.setId(-1L);
        shopEs.setShopCategory("王八");
        shopEs.setReducedPrice(9.9);
        //shopEs.setCreateTime();
        ShopEs save = shopEsRepository.save(shopEs);
        log.info("----testSaveShop:{}",shopEs);
    }
    
    
    public void testFindAll(){
        Iterable<ShopEs> all = shopEsRepository.findAll();
        for(ShopEs shopEs : all){
            log.info("testFindAll----shopEx:{}",shopEs);
        }
    }

}
