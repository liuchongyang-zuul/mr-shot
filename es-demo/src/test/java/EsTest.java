import com.baidu.lcy.es.RunTestEsApplication;
import com.baidu.lcy.es.entity.GoodsEntity;
import com.baidu.lcy.es.repository.GoodsEsRepository;
import com.baidu.lcy.es.util.ESHighLightUtil;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName EsTest
 * @Description: TODO
 * @Author liuchongyang
 * @Date 2020/9/14
 * @Version V1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { RunTestEsApplication.class})
public class EsTest {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Resource
    private GoodsEsRepository goodsEsRepository;

    @Test
    public void createGoodsIndex(){
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("goods"));
        indexOperations.create();//创建索引
        System.out.println(indexOperations.exists()?"索引创建成功":"索引创建失败");
    }

    @Test
    public void createGoodsMapping(){

        //此构造函数会检查有没有索引存在,如果没有则创建该索引,如果有则使用原来的索引
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(GoodsEntity.class);

        //indexOperations.createMapping();//创建映射,不调用此函数也可以创建映射,这就是高版本的强大之处
        System.out.println("映射创建成功");
    }

    @Test
    public void deleteGoodsIndex(){
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(GoodsEntity.class);

        indexOperations.delete();
        System.out.println("索引删除成功");
    }

    @Test
    public void saveData(){

        GoodsEntity entity = new GoodsEntity();
        entity.setId(1L);
        entity.setBrand("小米");
        entity.setCategory("手机");
        entity.setImages("xiaomi.jpg");
        entity.setPrice(4000D);
        entity.setTitle("小米3");

        goodsEsRepository.save(entity);

        System.out.println("新增成功");
    }

    @Test
    public void saveAllData(){

        GoodsEntity entity = new GoodsEntity();
        entity.setId(2L);
        entity.setBrand("苹果");
        entity.setCategory("手机");
        entity.setImages("pingguo.jpg");
        entity.setPrice(5000D);
        entity.setTitle("iphone11手机");

        GoodsEntity entity2 = new GoodsEntity();
        entity2.setId(3L);
        entity2.setBrand("三星");
        entity2.setCategory("手机");
        entity2.setImages("sanxing.jpg");
        entity2.setPrice(3000D);
        entity2.setTitle("w2019手机");

        GoodsEntity entity3 = new GoodsEntity();
        entity3.setId(4L);
        entity3.setBrand("华为");
        entity3.setCategory("手机");
        entity3.setImages("huawei.jpg");
        entity3.setPrice(4000D);
        entity3.setTitle("华为mate30手机");

        goodsEsRepository.saveAll(Arrays.asList(entity,entity2,entity3));

        System.out.println("批量新增成功");
    }

    @Test
    public void updateData(){

        GoodsEntity entity = new GoodsEntity();
        entity.setId(5L);
        entity.setBrand("小米");
        entity.setCategory("手机");
        entity.setImages("xiaomi.jpg");
        entity.setPrice(1000D);
        entity.setTitle("小米4");

        goodsEsRepository.save(entity);

        System.out.println("修改成功");
    }

    @Test
    public void delData(){

        GoodsEntity entity = new GoodsEntity();
        entity.setId(1L);

        goodsEsRepository.delete(entity);

        System.out.println("删除成功");
    }

    @Test
    public void searchAll(){
        //查询总条数
        long count = goodsEsRepository.count();
        System.out.println(count);
        //查询所有数据
        Iterable<GoodsEntity> all = goodsEsRepository.findAll();
        all.forEach(goods -> {
            System.out.println(goods);
        });
    }

    @Test
    public void customizeSearch(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field title = new HighlightBuilder.Field("title");
        title.preTags("<span style=color:red'>");
        title.postTags("</span>");
        highlightBuilder.field(title);
        queryBuilder.withHighlightBuilder(highlightBuilder);

        queryBuilder.withQuery(QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("title","小米"))
                .must(QueryBuilders.rangeQuery("price").gte(1000).lte(5000))
        );
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        queryBuilder.withPageable(PageRequest.of(0,5));

        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(queryBuilder.build(), GoodsEntity.class);

        List<SearchHit<GoodsEntity>> searchHits = search.getSearchHits();

        List<SearchHit<GoodsEntity>> title1 = searchHits.stream().map(list -> {
            Map<String, List<String>> highlightFields = list.getHighlightFields();
            list.getContent().setTitle(highlightFields.get("title").get(0));

            return list;
        }).collect(Collectors.toList());
        System.out.println(title1);
    }

    @Test
    public void customizeSearchHighLightUtil(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder.withHighlightBuilder(ESHighLightUtil.getHighlightBuilder("title"));

        queryBuilder.withQuery(QueryBuilders.boolQuery()
            .must(QueryBuilders.matchQuery("title","华为"))
            .must(QueryBuilders.rangeQuery("price").gte(1000).lte(10000))
        );
        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(queryBuilder.build(), GoodsEntity.class);

        List<SearchHit<GoodsEntity>> searchHits = search.getSearchHits();

        //重新设置title
        List<SearchHit<GoodsEntity>> highLightHit = ESHighLightUtil.getHighLightHit(searchHits);

        System.out.println(highLightHit);
    }

    @Test
    public void searchAgg(){

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.addAggregation(AggregationBuilders.terms("brand_agg").field("brand"));

        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(queryBuilder.build(), GoodsEntity.class);

        Aggregations aggregations = search.getAggregations();

        Terms terms = aggregations.get("brand_agg");

        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        buckets.forEach(bucket -> {
            System.out.println(bucket.getKeyAsString() + ":" + bucket.getDocCount());
        });
        System.out.println(search);
    }

    @Test
    public void searchAggUtil(){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        queryBuilder.addAggregation(AggregationBuilders.terms("brand_agg").field("brand")
            .subAggregation(AggregationBuilders.max("max_price").field("price"))
        );

        SearchHits<GoodsEntity> search = elasticsearchRestTemplate.search(queryBuilder.build(), GoodsEntity.class);

        Aggregations aggregations = search.getAggregations();
        Terms terms = aggregations.get("brand_agg");

        List<? extends Terms.Bucket> buckets = terms.getBuckets();

        buckets.forEach(bucket ->{
            Aggregations aggregations1 = bucket.getAggregations();
            Map<String, Aggregation> map = aggregations1.asMap();
            Max max_price = (Max) map.get("max_price");
            System.out.println(bucket.getKeyAsString() + ":" + bucket.getDocCount());
            System.out.println(max_price.getValue());
        });
    }
}
