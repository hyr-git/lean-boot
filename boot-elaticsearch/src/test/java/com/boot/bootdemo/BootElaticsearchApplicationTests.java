package com.boot.bootdemo;

import cn.hutool.core.util.RandomUtil;
import com.boot.elasticsearch.BootESApplication;
import com.boot.elasticsearch.dao.GoodsRepository;
import com.boot.elasticsearch.model.EsGoods;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.InternalAvg;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import javax.annotation.Resource;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = BootESApplication.class)
@Slf4j
class BootElaticsearchApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    @Resource
    private GoodsRepository goodsRepository;
    /**
     * 创建索引和映射
     */
    @Test
    public void createIndexAndMapping() {
        //创建索引，会根据EsGoods类的@Document注解信息来创建
        elasticsearchRestTemplate.createIndex(EsGoods.class);
        //配置映射，会根据EsGoods类中的id、Field等字段来自动完成映射
        //elasticsearchTemplate.putMapping(EsGoods.class);
    }



    @Test
    public void addEsGoodsDocument() {
        List<EsGoods> goodsList = new ArrayList<>();
        goodsList.add(new EsGoods("1", "联想-拯救者", "电脑", "联想", 5000.0, "1.jpg"));
        goodsList.add(new EsGoods("2", "apple-pro", "电脑", "苹果", 15000.0, "2.jpg"));
        goodsList.add(new EsGoods("3", "戴尔-灵越", "电脑", "戴尔", 6000.0, "3.jpg"));
        goodsList.add(new EsGoods("4", "华硕-A45", "电脑", "华硕", 4000.0, "4.jpg"));
        goodsList.add(new EsGoods("5", "小米-pro", "电脑", "小米", 3000.0, "5.jpg"));
        goodsList.add(new EsGoods("6", "华为-Meta10", "电脑", "华为", 5000.0, "6.jpg"));
        //保存一个
        //goodsRepository.save();
        //保存多个
        goodsRepository.saveAll(goodsList);
    }


    /**
     * 修改文档
     * 修改文档这块和jpa 的操作如出一辙。
     */
    @Test
    public void updateDocument() {
        EsGoods esGoods = new EsGoods("8", "联想-拯救者", "电脑", "联想", 6500.0, "8.jpg");
        //修改价格
        goodsRepository.save(esGoods);
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            double s = RandomUtil.randomDouble(0.1,0.8,1, RoundingMode.HALF_UP);

            System.out.println((1.0+s)*5000.0);

        }
    }

    @Test
    public void saveBatchDocument() {
        List<EsGoods> goodsList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            double s = RandomUtil.randomDouble(0.1,0.8,1, RoundingMode.DOWN);

            //goodsList.add(new EsGoods(1L, "联想-", "电脑", "联想", 5000.0, "1.jpg"));
            EsGoods goods =  EsGoods.builder()
                    .title("联想-小新系列14 pro"+i)
                    .category("电脑")
                    .brand("联想")
                    .price(8000.0*(1+s))
                    .images( (1+i)+".jpg")
                    .build();
            goodsList.add(goods);

            //goodsList.add(new EsGoods(3L, "戴尔-灵越", "电脑", "戴尔", 6000.0, "3.jpg"));
            EsGoods goods2 =  EsGoods.builder()
                    .title("戴尔-灵越-"+i)
                    .category("电脑")
                    .brand("戴尔")
                    .price(7000.0*(1+s))
                    .images( (2+i)+".jpg")
                    .build();
            goodsList.add(goods);

            //goodsList.add(new EsGoods(4L, "华硕-A45", "电脑", "华硕", 4000.0, "4.jpg"));
            EsGoods goods3 =  EsGoods.builder()
                    .title("华硕-A45-"+i)
                    .category("电脑")
                    .brand("华硕")
                    .price(4000.0*(1+s))
                    .images( (3+i)+".jpg")
                    .build();
            goodsList.add(goods);

            //goodsList.add(new EsGoods(5L, "小米-pro", "电脑", "小米", 3000.0, "5.jpg"));
            EsGoods goods4 =  EsGoods.builder()
                    .title("小米-pro"+i)
                    .category("电脑")
                    .brand("小米")
                    .price(3000*(1+s))
                    .images( (4+i)+".jpg")
                    .build();
            goodsList.add(goods);

            //goodsList.add(new EsGoods(6L, "华为-Meta10", "电脑", "华为", 5000.0, "6.jpg"));
            EsGoods goods5 =  EsGoods.builder()
                    .title("华为-Meta10-"+i)
                    .category("电脑")
                    .brand("华为")
                    .price(6000.0*(1+s))
                    .images( (5+i)+".jpg")
                    .build();
            goodsList.add(goods);
        }
        //保存多个
        Iterable<EsGoods> esGoods = goodsRepository.saveAll(goodsList);
        esGoods.forEach(System.out::print);
    }

    /**
     * 删除索引
     */
    @Test
    public  void  deleteIndex(){
        elasticsearchRestTemplate.deleteIndex("goods");
    }




    /**
     * 根据id 查询
     */
    @Test
    public void findById() {
        Optional<EsGoods> optional = goodsRepository.findById("2");


    }
    /**
     * 查询所有
     */
    @Test
    public void findAll() {
        Iterable<EsGoods> all = goodsRepository.findAll();
        for (EsGoods esGoods : all) {
            System.out.println(esGoods);
        }
    }

    /**
     * 自定义价查询
     */
    @Test
    public void findByPrice() {
        //价格大于5000的
        List<EsGoods> result = goodsRepository.findByPriceAfter(5000);
        System.out.println("============价格大于等于5000的============");
        System.out.println(result);
        //价格小于5000的
        System.out.println("============价格小于等于5000的============");
        List<EsGoods> byPriceBefore = goodsRepository.findByPriceBefore(5000);
        System.out.println(byPriceBefore);
        //价格等于5000的
        System.out.println("============价格等于5000的============");
        List<EsGoods> byPrice = goodsRepository.findByPrice(5000);
        System.out.println(byPrice);
    }

    /**
     * 词条查询
     */
    @Test
    public void testQuery(){
        // 词条查询
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "pro");
        // 执行查询
        Iterable<EsGoods> items = goodsRepository.search(queryBuilder);
        items.forEach(System.out::println);
    }
  /*  EsGoods(id=5, title=小米-pro, category=电脑, brand=小米, price=3000.0, images=5.jpg)
    EsGoods(id=2, title=apple-pro, category=电脑, brand=苹果, price=15000.0, images=2.jpg)*/


    /**
     * title包含pro自定义查询
     */
    @Test
    public void customizeTitleQuery() {
        //构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "pro"));
        //执行搜索,获取结果
        Page<EsGoods> search = goodsRepository.search(queryBuilder.build());
        System.out.println("总页数：" + search.getTotalPages());
        System.out.println("总记录数:" + search.getTotalElements());
        search.forEach(System.out::println);
    }

   /* 总页数：1
    总记录数:2
    EsGoods(id=5, title=小米-pro, category=电脑, brand=小米, price=3000.0, images=5.jpg)
    EsGoods(id=2, title=apple-pro, category=电脑, brand=苹果, price=15000.0, images=2.jpg)*/

    /**
     * title包含pro或者价格区间[5000-20000]自定义查询
     */
    @Test
    public void customizeTitleOrPriceQuery() {
        //构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.boolQuery()
                //等价与mysql中的or
                .should(QueryBuilders.matchQuery("title", "pro"))
                .should(QueryBuilders.rangeQuery("price").gte(5000).lte(20000)));

        //执行搜索,获取结果
        Page<EsGoods> search = goodsRepository.search(queryBuilder.build());
        System.out.println("总页数：" + search.getTotalPages());
        System.out.println("总记录数:" + search.getTotalElements());
        search.forEach(System.out::println);
    }

   /* 总页数：1
    总记录数:2
    EsGoods(id=5, title=小米-pro, category=电脑, brand=小米, price=3000.0, images=5.jpg)
    EsGoods(id=2, title=apple-pro, category=电脑, brand=苹果, price=15000.0, images=2.jpg)*/


    /**
     * title包含pro或者价格区间[5000-20000]自定义查询
     */
    @Test
    public void customizeTitleAndPriceQuery(){
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.boolQuery()
                //等价与mysql中的and
                .must(QueryBuilders.matchQuery("title", "pro"))
                .must(QueryBuilders.rangeQuery("price").lte(20000).gte(5000))
        ).build();
        Page<EsGoods> search = goodsRepository.search(nativeSearchQuery);
        System.out.println("总页数：" + search.getTotalPages());
        System.out.println("总记录数:" + search.getTotalElements());
        search.forEach(System.out::println);
    }
    /****
     * 总页数：1
     * 总记录数:1
     * EsGoods(id=2, title=apple-pro, category=电脑, brand=苹果, price=15000.0, images=2.jpg)
     */


    /****
     * 高亮显示搜索结果(结果不是很明显)
     */
    @Test
    public void findCustomWithHighlightFields(){
        String keyword = "Spring Data";
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.matchQuery("title",keyword));

        HighlightBuilder.Field titleField = new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>");
        builder.withHighlightFields(titleField);

        List<EsGoods> goodsList = goodsRepository.search(builder.build()).getContent();
        for (EsGoods goods:goodsList) {
            String title = goods.getTitle();
            System.out.println(title);

        }
    }

    /***
     * 将品牌为联想的拆分为具体的子品牌
     */
    @Test
    public void testUpdateBatch(){
        String keyword = "小新系列14";//联想-小新系列14
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(QueryBuilders.matchQuery("title",keyword));


        List<EsGoods> goodsList = goodsRepository.search(searchQueryBuilder.build()).getContent();
        for (EsGoods goods:goodsList) {
            String title = goods.getTitle();
            System.out.println(title);
            goods.setBrand("联想-小新系列14");
        }
        goodsRepository.saveAll(goodsList);

    }


    /***
     * TODO 待实现
     */
    @Test
    public void testFindByFilter(){
        /*String keyword = "联想";
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(QueryBuilders.matchQuery("title",keyword))
                .withFilter(MatchQueryBuilder.);*/

    }

    /**
     * 分页+排序查询
     */
    @Test
    public void testNativePageQuery() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本的分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "电脑"));
        // 设置分页参数 从第0 也开始每页显示十个数据
        queryBuilder.withPageable(PageRequest.of(0, 10));
        //设置排序 按照电脑的价格进行升序排列
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        // 执行搜索，获取结果
        Page<EsGoods> items = goodsRepository.search(queryBuilder.build());
        // 打印总条数
        System.out.println("总条数：" + items.getTotalElements());
        // 打印总页数
        System.out.println("总页数:" + items.getTotalPages());
        // 每页大小
        System.out.println("每页大小：" + items.getSize());
        // 当前页
        System.out.println("当前页：" + items.getNumber());
        items.forEach(System.out::println);
    }

   /* 总条数：6
    总页数:1
    每页大小：10
    当前页：0
    EsGoods(id=5, title=小米-pro, category=电脑, brand=小米, price=3000.0, images=5.jpg)
    EsGoods(id=4, title=华硕-A45, category=电脑, brand=华硕, price=4000.0, images=4.jpg)
    EsGoods(id=6, title=华为-Meta10, category=电脑, brand=华为, price=5000.0, images=6.jpg)
    EsGoods(id=1, title=联想-拯救者, category=电脑, brand=联想, price=6000.0, images=1.jpg)
    EsGoods(id=3, title=戴尔-灵越, category=电脑, brand=戴尔, price=6000.0, images=3.jpg)
    EsGoods(id=2, title=apple-pro, category=电脑, brand=苹果, price=15000.0, images=2.jpg)*/


    /**
     * 聚合查询
     * 按品牌分组查询
     */
    @Test
    public void brandAggregated() {
        //构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brands
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand"));
        // 查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<EsGoods> aggPage = (AggregatedPage<EsGoods>) goodsRepository.search(queryBuilder.build());
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称 和 文档数量
            System.out.println(bucket.getKeyAsString() + ":" +bucket.getDocCount());
        }
    }
  /*  华为:1
    华硕:1
    小米:1
    戴尔:1
    联想:1
    苹果:1*/


    /**
     * 嵌套查询
     */
    @Test
    public void testSubAvgAgg(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                        .subAggregation(AggregationBuilders.avg("avgPrice").field("price")) // 在品牌聚合桶内进行嵌套聚合，求平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<EsGoods> aggPage = (AggregatedPage<EsGoods>) this.goodsRepository.search(queryBuilder.build());
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        buckets.forEach(bucket -> {
            // 3.4、获取桶中的key，即品牌名称 ; 获取桶中的文档数量 ;获取平均值结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("avgPrice");
            System.out.println(bucket.getKeyAsString() + "共" + bucket.getDocCount() +",平均售价："+ avg.getValue() );
        });
    }
    /*华为共1,平均售价：5000.0
    华硕共1,平均售价：4000.0
    小米共1,平均售价：3000.0
    戴尔共1,平均售价：6000.0
    联想共1,平均售价：6000.0
    苹果共1,平均售价：15000.0*/
}
