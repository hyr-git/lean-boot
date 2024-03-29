package com.boot.elasticsearch.dao;

import com.boot.elasticsearch.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


/*******
 *
 * 使用 NativeSearchQuery
 *
 * //制定字符串作为关键词查询，关键词支持分词
 * QueryBuilders.queryStringQuery()
 *
 * //不指定field字段，查询范围为所有的field
 * QueryBuilders.queryStringQuery("三星").defaultField(“description”);
 *
 * //指定多个field字段
 * QueryBuilders.queryStringQuery("三星").field("title").field("description");
 *
 *
 * QueryBuilders.boolQuery  子方法must可以多条件联查
 *
 * QueryBuilders.termQuery   精确查询指定字段不支持分词
 * QueryBuilders.termQuery("title","华为")
 *
 *
 * QueryBuilders.matchQuery   按分词器模糊查询
 * QueryBuilders.matchQuery("title","华为")
 *
 * QueryBuilders.rangeQuery 按照制定范围进行区间范围查询
 *
 *
 * QueryBuilders.boolQuery().must()  ===and
 * QueryBuilders.boolQuery().should() ===or
 * QueryBuilders.boolQuery().mustNot() ===not
 */
@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article,Long> {

    //下面的这两个查询的作用是一样的。一个采用默认的实现方式，一个采用自定义的实现方式
    Page<Article> findByAuthorsName(String name, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"authors.name\": \"?0\"}}]}}")
    Page<Article> findByAuthorsNameUsingCustomQuery(String name, Pageable pageable);

    //搜索title字段
    Page<Article> findByTitleIsContaining(String word,Pageable pageable);
    
    Page<Article> findByTitle(String title,Pageable pageable);

    /*****
     *
     *
     *
     * {"bool":{
     *     "must":[{
     *         "field":{"title":"?"},"field":{"price":"?"}
     *     }]
     * }}
     * @param title
     * @param price
     * @return
     */
    Page<Article> findByTitleAndPrice(String title,double price,Pageable pageable);


    /*****
     * {"bool":{
     *      "should":[{
     *           "field":{"title":"?"},"field":{"price":"?"}
     *      }]
     * }}
     *
     * @param title
     * @return
     */
    Page<Article> findByTitleOrPrice(String title,double price,Pageable pageable);


    /*****
     * {"bool":{
     *      "must":{"field":{"title":"?"}}
     * }}
     * @param title
     * @return
     */
    List<Article> findByTitle(String title);


    /*****
     * {"bool":{
     *      "must_not":{"field":{"title":"?"}}
     * }}
     * @param title
     * @return
     */
    List<Article> findByTitleNot(String title);

    /*****
     * {"bool":{
     *      "must":{
     *          "range":{
     *              "field":{"price":{"from":?,"to":?,"include_lower":true,"include_upper":true}}
     *          }
     *      }
     * }}
     * @param price
     * @return
     */
    List<Article> findByPriceBetween(double priceFrom,double priceTo);


    /*****
     * {"bool":{
     *     "must":{"range":{
     *         "price":{
     *             "from":null,"to":?,"include_lower":true,"include_upper":true
     *         }
     *     }}
     * }}
     * @param price
     * @return
     */
    List<Article> findByPriceLessThan(double priceTo);


    /******
     * {"bool":{
     *      "must":{
     *          "bool":{
     *             "should":[
     *             {"field":{"name":"?"}},{"field":{"name":"?"}}]
     *
     *      }}
     * }}
     * @param names
     * @return
     */
    List<Article> findByTitleIn(Collection<String> names);


    /******
     * {"bool":[
     *      "must_not":{
     *          "bool":{
     *             "should":[
     *             {"field":{"name":"?"}},{"field":{"name":"?"}}]
     *
     *      }}
     * ]}
     * @param names
     * @return
     */
    List<Article> findByTitleNotIn(Collection<String> names);

    /***
     * {"bool":{
     *     "must":{
     *          "field":{
     *               "title":{"query":"?*","analyze_wildcard":true}
     *          }
     *     }
     * }}
     * @param name
     * @return
     */
    List<Article> findByTitleLike(String name);

    /***
     * {"bool":{
     *     "must":{
     *         "filed":{
     *             "title":{
     *                 "query":"?*","analyze_wildcard":true
     *             }
     *         }
     *     }
     * }}
     * @param name
     * @return
     */
    List<Article> findByTitleStartingWith(String name);


    /*****
     * {  "bool":{
     *       "must":{"filed":{
     *           "title":{
     *               "query":"*?","analyze_wildcard":true
     *           }
     *       }}
     *
     * }}
     * @param name
     * @return
     */
    List<Article> findByTitleEndingWith(String name);

    /*****
     * {  "bool":{
     *       "must":{"filed":{
     *           "title":{
     *               "query":"**?**","analyze_wildcard":true
     *           }
     *       }}
     *
     * }}
     * @param name
     * @return
     */
    List<Article> findByTitleContaining(String name);


    /****
     *
     * @param name
     * @return
     */
    List<Article> findByTitleContains(String name);

}