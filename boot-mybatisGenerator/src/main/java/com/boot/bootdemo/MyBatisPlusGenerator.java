package com.boot.bootdemo;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;

public class MyBatisPlusGenerator {

    public static void main(String[] args) {
        //String projectPath = System.getProperty("user.dir");
        String projectPath = "d://";
        // 数据源配置
        DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig
                .Builder(
                "jdbc:mysql://127.0.0.1:3306/world?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8",
                "root",
                "root")
                .dbQuery(new MySqlQuery())
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler());

        FastAutoGenerator.create(dataSourceConfig)
                .globalConfig(builder -> {
                    builder.author("Author") //设置作者
                            .commentDate("YYYY-MM-DD HH:mm:ss")//注释日期
                            .outputDir(projectPath + "/service-user/src/main/java/com/demo/user"); //指定输出目录

                })
                .packageConfig(builder -> {
                    builder.parent(""); // 设置父包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("tbl_user") // 设置需要生成的表名
                            .addTablePrefix("tbl_"); // 设置过滤表前缀
                    builder.entityBuilder().enableLombok();//开启 lombok 模型
                    builder.entityBuilder().enableTableFieldAnnotation();//开启生成实体时生成字段注解
                    builder.controllerBuilder().enableRestStyle();//开启生成@RestController 控制器

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}