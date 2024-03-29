package com.boot.elasticsearch.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/*****
 * 你也可以不用继承AbstractElasticsearchConfiguration类，而将ESConfiguration携程一般的配置类类型
 * 不过继承AbstractElasticsearchConfiguration好处在于，他已经帮我们配置好了elasticsearchTemplate,可以直接使用
 */
public class ESConfiguration extends AbstractElasticsearchConfiguration {
    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("127.0.0.1:9200").build();
        return RestClients.create(clientConfiguration).rest();
    }
}
