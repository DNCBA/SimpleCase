package com.mhc.elasticsearch.restClient;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;


/**
 * @author ：menghui.cao, menghui.cao@leyantech.com
 * @date ：2020-05-08 15:13
 */
public class ESOperation {

  public static void main(String[] args) throws IOException {

//    restLowLevelClientTest();

    restHightLevelClientTest();


  }

  private static void restHightLevelClientTest() throws IOException {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        ElasticSearchClientConfig.class);
    RestHighLevelClient client = applicationContext.getBean(RestHighLevelClient.class);

//    //创建索引
//    CreateIndexRequest request = new CreateIndexRequest("simple");
//    request.settings(Settings.builder().put("index.number_of_shards", 1)
//        .put("index.number_of_replicas", 0));
//
//    request.mapping(
//        "{\"doc\":{\"properties\":{\"org_id\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\"}}},\"class_name\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\"}}},\"name\":{\"type\":\"text\",\"fields\":{\"keyword\":{\"type\":\"keyword\"}}},\"age\":{\"type\":\"integer\"}}}}",
//        XContentType.JSON);
//    CreateIndexResponse createIndexResponse = client.indices()
//        .create(request, RequestOptions.DEFAULT);

    UserInfoDTO user = new UserInfoDTO();
    user.setName(UUID.randomUUID().toString());
    user.setAge(13);
    user.setId(99);

    System.out.println(JSON.toJSONString(user));

    //添加数据
    IndexRequest saveRequest = new IndexRequest("simple");
    saveRequest.source(JSON.toJSONString(user), XContentType.JSON);

    IndexResponse saveResponse = client.index(saveRequest, RequestOptions.DEFAULT);
    Result result = saveResponse.getResult();
    System.out.println(result);

    //根据id查询数据
    GetRequest getRequest = new GetRequest("simple", "nb0y9HEBkY-_CmMLWUQh");
    //展示字段
    String[] includes = new String[]{"age", "name"};
    String[] excludes = Strings.EMPTY_ARRAY;
    FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
    getRequest.fetchSourceContext(fetchSourceContext);

    GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
    String sourceAsString = getResponse.getSourceAsString();
    System.out.println(sourceAsString);

    //search查询
    SearchRequest searchRequest = new SearchRequest("simple");


    SearchSourceBuilder builder = new SearchSourceBuilder();

    //匹配查询1
    MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders
        .matchPhraseQuery("age", "13");

    //匹配查询2
    MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();


    //组装两个查询条件
    BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
    boolQueryBuilder.filter(matchPhraseQueryBuilder).must(matchAllQueryBuilder);

    builder.query(boolQueryBuilder);
//    builder.query(matchAllQueryBuilder);
    builder.fetchSource(false); //是否返回source字段
    builder.sort(new FieldSortBuilder("_id").order(SortOrder.ASC));//按照指定顺序排序
//    builder.from(2); //从哪里开始
//    builder.size(2); //返回数量


    builder.fetchSource(new FetchSourceContext(true,new String[]{"name","age","id"},Strings.EMPTY_ARRAY));

    searchRequest.source(builder);



    SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
    for (SearchHit hit : searchResponse.getHits()) {
      String id = hit.getId();
      String sdata = hit.getSourceAsString();
      System.out.println(id + sdata);
    }

  }


  private static void restLowLevelClientTest() throws IOException {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
        ElasticSearchClientConfig.class);

    RestClient restClient = applicationContext.getBean(RestClient.class);

    Request request = new Request("GET", "");

    Response response = restClient.performRequest(request);

    HttpHost host = response.getHost();
    System.out.println(host);

    int statusCode = response.getStatusLine().getStatusCode();
    System.out.println(statusCode);

    String s = response.toString();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    IOUtils.copy(response.getEntity().getContent(), outputStream);
//    System.out.println(response.getEntity().getContentEncoding().getValue());
    String data = new String(outputStream.toByteArray());
    System.out.println(data);

    System.out.println(s);
  }

}
