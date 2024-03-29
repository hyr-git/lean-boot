package com.boot.bootdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.mybatis.BootMybatisApplication;
import com.boot.mybatis.entity.Country;
import com.boot.mybatis.mapper.ProductsDao;
import com.boot.mybatis.entity.City;
import com.boot.mybatis.entity.Products;
import com.boot.mybatis.service.ICityService;
import com.boot.mybatis.service.ICountryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest(classes = BootMybatisApplication.class)
class DemoApplicationTests {
    @Resource
    private ICityService cityService;
    @Test
    void contextLoads() {
        QueryWrapper<City> wrapper = new QueryWrapper<>();
        wrapper.likeRight("name","_z");
        wrapper.or();
        //wrapper.between("age",10,20);
        wrapper.orderByDesc("name");
       // wrapper.select("name","age");
        List<City> users = cityService.list(wrapper);
        users.forEach(item->log.info("city:{}",item));
    }

    @Test
    public void insert(){
        City user = new City();
        user.setId(-99);
        user.setName("cxk");
        user.setDistrict("123@qq.com");
        System.out.println(cityService.save(user));
    }
    @Test
    public void update(){
        City user = new City();
        user.setId(-99);
        user.setName("azy");
        user.setDistrict("321@qq.com");
        System.out.println(cityService.updateById(user));
    }
    @Test
    public void selectById(){

        City city = cityService.getById(4);
        log.info("city:{}",city);
    }

    @Test
    public void testPage(){
        Page<City> page = new Page<>(1, 3);//current:当前第几页 size：每页显示条数
        cityService.page(page,null);//把查询分页的结构封装到page对象中
        log.info("testPage-----page:{}",page);
        log.info("testPage-----当前页的记录"+page.getRecords());
        log.info("testPage-----当前页的记录"+page.getRecords());
        log.info("testPage-----获取总条数"+page.getTotal());
    }

//    ==============================================================
    @Resource
    private ProductsDao productsDao;
    @Test
    public void testQueryProductById(){
        Products products = productsDao.selectById("p008");
        log.info("testQueryProductById-----products:{}",products);
    }

    @Test
    public void testInsert(){
        Products products = new Products();
        products.setPname("滑板鞋");
        products.setFlag("2");
        products.setPrice(8888);
        products.setCategory_id("c002");
        products.setPid("p009");
        System.out.println(productsDao.insert(products));
    }
    @Test
    public void testUpdate(){
        Products products = new Products();
        products.setPname("篮球");
        products.setFlag("1");
        products.setPrice(188);
        products.setCategory_id("c002");
        products.setPid("p009");
        System.out.println(productsDao.updateById(products));
    }
    @Test
    public void testProductsPage(){
        Page<Products> page = new Page<>(1, 3);//current:当前第几页 size：每页显示条数
        productsDao.selectPage(page,null);//把查询分页的结构封装到page对象中
        log.info("testPage-----page:{}",page);
        log.info("testPage-----当前页的记录"+page.getRecords());
        log.info("testPage-----当前页的记录"+page.getRecords());
        log.info("testPage-----获取总条数"+page.getTotal());
    }

    @Test
    public void testProductsPage2(){
        Page<Products> page=new Page<>(1,3);
        QueryWrapper<Products> wrapper=new QueryWrapper<>();
        wrapper.gt("price",1000);
        productsDao.findPage(page,wrapper);
        log.info("testPage-----page:{}",page);
        log.info("testPage-----当前页的记录"+page.getRecords());
        log.info("testPage-----当前页的记录"+page.getRecords());
        log.info("testPage-----获取总条数"+page.getTotal());
    }


    @Resource
    private ICountryService countryService;

    @Test
    public void testCountry(){
        List<Country> countryList = countryService.list();
        log.info("testCountry-----countryList:{}",countryList);
    }
}

