package com.boot.elasticsearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

//注意indexName要小写
@Document(indexName = "article_index",indexStoreType = "article")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    private String id;

    @Field(type = FieldType.Text,analyzer = "id_max_word")
    private String title;

    @Field(type = FieldType.Text,analyzer = "id_max_word")
    private String content;

    @Field(type = FieldType.Date,//format=DateFormat.date_hour_minute_second
           format = DateFormat.custom,pattern = "8uuuu-MM-dd HH:mm:ss"
    )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    //分类
    private String category;

    @Field(type=FieldType.Double)
    private double price;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Author> authors;


    public Article(String title) {
        this.title = title;
    }
}

