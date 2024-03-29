package com.boot.mybatis.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import javax.servlet.MultipartConfigElement;
import java.util.Properties;

/**
 * @Company:wftdlx
 * @Author: wjf
 * @Description:  分页插件
 * @Date: Created in 16:41 2018/12/27
 */
//Spring boot方式
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {
    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】 打印sql语句
     */

/*   @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        //格式化sql语句
        Properties properties = new Properties();
        properties.setProperty("format", "false");
        performanceInterceptor.setProperties(properties);
        return new PerformanceInterceptor();
    }*/


    /**
     * 分页插件，自动识别数据库类型 多租户，请参考官网【插件扩展】

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }  */


    //文件上传
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大
        factory.setMaxFileSize(DataSize.of(201400, DataUnit.KILOBYTES)); // KB, MB
        // 设置上传数据总大小
        factory.setMaxRequestSize(DataSize.of(201400, DataUnit.KILOBYTES));
        return factory.createMultipartConfig();
    }
}