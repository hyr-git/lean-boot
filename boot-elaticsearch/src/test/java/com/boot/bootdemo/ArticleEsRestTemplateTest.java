package com.boot.bootdemo;

import cn.hutool.core.util.RandomUtil;
import com.boot.elasticsearch.BootESApplication;
import com.boot.elasticsearch.dao.ArticleRepository;
import com.boot.elasticsearch.model.Article;
import com.boot.elasticsearch.model.Author;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.math.RoundingMode;
import java.util.*;

@Slf4j
@SpringBootTest(classes = BootESApplication.class)
public class ArticleEsRestTemplateTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    //检查相应的索引是否存在，如果spring.data.elasticsearch.repositories.enabled=True,则会自动创建索引
    private boolean checkIndexExists(Class<?> cls){
        boolean isExist = elasticsearchRestTemplate.indexOps(cls).exists();
        //获取索引名
        String indexName = cls.getAnnotation(Document.class).indexName();
        System.out.printf("index %s is %s\n", indexName, isExist ? "exist" : "not exist");
        return isExist;
    }
    @Test
    void checkIndex() {
        checkIndexExists(Article.class);
    }


    @Test
    void save(){
        Article article = new Article("Spring Data Elasticsearch");
        article.setAuthors(asList(new Author("LaoAlex"),new Author("John")));
        articleRepository.save(article);

        article = new Article("Spring Data Elasticsearch2");
        article.setAuthors(asList(new Author("LaoAlex"),new Author("King")));
        articleRepository.save(article);

        article = new Article("Spring Data Elasticsearch3");
        article.setAuthors(asList(new Author("LaoAlex"),new Author("Bill")));
        articleRepository.save(article);
    }

    private List<Author> asList(Author laoAlex, Author john) {
        return Arrays.asList(laoAlex,john);
    }


    @Test
    void queryAuthorName() throws JsonProcessingException {
        Page<Article> articles = articleRepository.findByAuthorsName("LaoAlex", PageRequest.of(0,10));
        //将对象转为Json字符串
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(articles);
        System.out.println(json);
    }

    //使用自定义查询
    @Test
    void queryAuthorNameByCustom() throws JsonProcessingException {
        Page<Article> articles = articleRepository.findByAuthorsNameUsingCustomQuery("John",PageRequest.of(0,10));
        //将对象转为Json字符串
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(articles);
        System.out.println(json);
    }


    //使用Template进行关键字查询
    //关于正则表达式可以参考https://www.runoob.com/java/java-regular-expressions.html
    //.*data.* 可以匹配ddata, dataa等
    /*@Test
    void queryTileContainByTemplate() throws JsonProcessingException {
        Query query = new NativeSearchQueryBuilder().withFilter(regexpQuery("title",".*elasticsearch2.*")).build();
        SearchHits<Article> articles = elasticsearchRestTemplate.search(query, Article.class, IndexCoordinates.of("blog"));
        //将对象转为Json字符串
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(articles);
        System.out.println(json);
    }*/



    @Test
    void update() throws JsonProcessingException {
        Page<Article> articles = articleRepository.findByTitle("Spring Data Elasticsearch",PageRequest.of(0,10));
        //将对象转为Json字符串
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = objectWriter.writeValueAsString(articles);
        System.out.println(json);

        Article article = articles.getContent().get(0);
        System.out.println(article);
        article.setAuthors(null);
        articleRepository.save(article);
    }


    @Test
    void delete(){
        Page<Article> articles = articleRepository.findByTitle("Spring Data Elasticsearch", PageRequest.of(0,10));
        Article article = articles.getContent().get(0);
        articleRepository.delete(article);
    }

    @Test
    public void testSaveArticle(){
        Article article = new Article();
        article.setTitle("Spring Data Es");
        article.setContent("Spring Data Elasticsearch");
        article.setPrice(9999);
        article.setCreateTime(new Date());
        article.setAuthors(Arrays.asList(new Author("将网吧3"),new Author("李海王3")));
        Article saveArticle = articleRepository.save(article);
        log.info("saveArticle:{}",saveArticle);
        Assertions.assertNotNull(saveArticle,"保存失败");
    }


    @Test
    public void testFindById(){
       Optional<Article> articleOptional = articleRepository.findById(1L);
       if(articleOptional.isPresent()){
           Article article = articleOptional.get();
           log.info("article:{}",article);
       }
    }

    @Test
    public void testFindByKeyWord(){
        Pageable pageable = PageRequest.of(0,10);
        String keyWord = "easy";
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("title", keyWord))
                .withPageable(pageable).build();

       Page<Article>  articlePage = articleRepository.search(nativeSearchQuery);

        List<Article> articleList = articlePage.getContent();

        for (Article article : articleList) {
            log.info("article:{}",article);
        }
    }


    /****
     * 使用matchQuery进行全文检索,查询title是否包含关键字
     */
    @Test
    public void  testFindPageByMatchTitle(){
        Pageable pageable = PageRequest.of(0,10);
        String keyWord = "Spring";

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                //使用matchQuery进行全文检索,查询title是否包含关键字
                .withQuery(QueryBuilders.matchQuery("title",keyWord))
                .withPageable(pageable).build();


        Page<Article> articlePage = articleRepository.search(nativeSearchQuery);
        List<Article> articleList = articlePage.getContent();
        for (Article article: articleList) {
            log.info("article:{}",article);
        }
    }


    /****
     * 测四批量初始化数据
     */
    @Test
    public void testSaveBatchArticle(){
        List<Article> articleList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setTitle("Spring Data Es "+i);
            article.setContent("Spring Data Elasticsearch "+ RandomUtil.randomNumbers(5));
            double randomPrice = RandomUtil.randomDouble(4000, 100000, 2, RoundingMode.HALF_UP);
            article.setPrice(randomPrice);
            article.setCreateTime(DateUtils.addMinutes(new Date(),i));
            article.setAuthors(Arrays.asList(new Author("jianzonlin_wanba"+i),new Author("李海王"+i)));

            String category = "a";
            if(i%2==0){
                category = "b";
            }else if(i%3==0){
                category = "c";
            }else if(i%5==0){
                category = "d";
            }else if(i%7==0){
                category = "e";
            }
            article.setCategory(category);
            log.info("articleList-----article:{}",article);
            articleList.add(article);
        }

        Iterable<Article> articles = articleRepository.saveAll(articleList);
        log.info("saveArticle:{}",articles);
    }


    /****
     * 查询文章中category不为空的数据
     */
    @Test
    public void  testFindPageCategoryEmpty(){
        Pageable pageable = PageRequest.of(0,10);
        String keyWord = "Spring";

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                //使用matchQuery进行全文检索,查询title是否包含关键字
                .withQuery(QueryBuilders.existsQuery("category"))
                .withPageable(pageable).build();

        Page<Article> articlePage = articleRepository.search(nativeSearchQuery);
        List<Article> articleList = articlePage.getContent();
        for (Article article: articleList) {
            log.info("article:{}",article);
        }

    }


    /****
     *  //查询价格>=500  and <=10000
     */
    @Test
    public void  testFindPageByPriceRange(){
        Pageable pageable = PageRequest.of(0,10,Sort.by(Sort.Direction.DESC,"price"));
        String keyWord = "Spring";

        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                //查询价格>=500  and <=10000
                .withQuery(QueryBuilders.rangeQuery("price").lte(10000).gte(500))
                .withPageable(pageable).build();
        //articleRepository.searchSimilar()

        Page<Article>  articlePage = articleRepository.search(nativeSearchQuery);
        List<Article> articleList = articlePage.getContent();
        for (Article article: articleList) {
            log.info("article:{}",article);
        }
    }


    /****
     * //查询全部并按照价格降序排序
     */
    @Test
    public void testSort(){
        //查询全部并按照价格降序排序
        Iterable<Article> articles = articleRepository.findAll(Sort.by(Sort.Direction.DESC, "price"));
        for (Article article: articles) {
            log.info("articles------article:{}",article);
        }

        Iterable<Article> articleIterable = articleRepository.findAll(Sort.by(Sort.Order.desc("price")));
        for (Article article: articleIterable) {
            log.info("articleIterable------article:{}",article);
        }
    }


    @Test
    public void testFindByTitleIsContaining(){
        Page<Article> articlePage = articleRepository.findByTitleIsContaining("Spring",
                PageRequest.of(0, 10, Sort.by(Sort.Order.desc("price"))));

        List<Article> articleList = articlePage.getContent();
        for (Article article: articleList) {
            log.info("articleIterable------article:{}",article);
        }
    }

    @Test
    public void testFindByAuthorsName(){
        Page<Article> byAuthorsName = articleRepository.findByAuthorsName("wanba", PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id"))));
        List<Article> articleList = byAuthorsName.getContent();
        for (Article article: articleList) {
            log.info("articleIterable------article:{}",article);
        }
    }


    @Test
    public void testCustom(){
        Page<Article> byAuthorsName = articleRepository.findByAuthorsNameUsingCustomQuery("wanba", PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id"))));
        List<Article> articleList = byAuthorsName.getContent();
        for (Article article: articleList) {
            log.info("articleIterable------article:{}",article);
        }
    }

    @Test
    public void testNativeQuery(){
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                //查询titile包含spring
                //.withQuery(QueryBuilders.matchQuery("title","Spring"))
                .withQuery(QueryBuilders.boolQuery()
                                .must(QueryBuilders.queryStringQuery("Spring").defaultField("title"))
                                .should(QueryBuilders.queryStringQuery("Spring").defaultField("content"))
                ).withPageable(PageRequest.of(0,10)).build();
        SearchHits<Article> search = elasticsearchRestTemplate.search(nativeSearchQuery, Article.class);
        List<SearchHit<Article>> searchHits = search.toList();

        for (SearchHit<Article> article: searchHits) {
            log.info("articleIterable------article:{}",article);
        }

    }
}
