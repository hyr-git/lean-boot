package com.boot.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/*****
 * @Document作用在类上,标记实体类为文档对象，一般有四个属性
 * indexName-对于索引库名称
 * type-对应在索引库中的类型
 * shards-分片数量，默认5
 * replicas-副本数量，默认1
 */
@Document(indexName = "goods",type = "detail",shards = 3,replicas = 1)
//@Document(indexName = "goods")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EsGoods {

    //标记一个字段作为ID逐渐
    @Id
    @Field(type = FieldType.Auto)
    String id;

    /***
     * @Field 作用在成员变量，标记为文档的字段，并指定字段映射属性
     * type-字段类型，取值是枚举FieldType
     * index-是否索引，布尔类型，默认是ture
     * store-是否存储，布尔类型，默认是false
     * analyzer-分词器名称：ik_max_word
     * IK分词器由2种分词模式：ik_max_word和ik_smart
     * ik_max_word：会将文本做最细力度的拆分
     * ik_smart：会做最粗力度的拆分
     */
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    String title; //标题
    @Field(type = FieldType.Keyword)
    String category;// 分类
    @Field(type = FieldType.Keyword)
    String brand; // 品牌
    @Field(type = FieldType.Double)
    Double price; // 价格
    @Field(index = false, type = FieldType.Keyword)
    String images; // 图片地址
}
