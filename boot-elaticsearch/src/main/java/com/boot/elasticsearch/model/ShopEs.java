package com.boot.elasticsearch.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "zth")
@Data
public class ShopEs implements Serializable {
 private static final long serialVersionUID = 2006762641515872124L;

 private Long id;

 //商品名称
 @Field(type = FieldType.Text, analyzer = "ik_max_word")
 private String shopName;

 // 分类
 @Field(type = FieldType.Keyword)
 private String  shopCategory;

 //优惠价格
 private Double reducedPrice;

 //生产日期
 @Field(type =FieldType.Date,format= DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
 private Date createTime;
}