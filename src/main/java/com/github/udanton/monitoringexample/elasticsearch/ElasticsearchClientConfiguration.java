package com.github.udanton.monitoringexample.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchClientConfiguration {

    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private int port;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        Header basicHeader1 = new BasicHeader("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
        Header basicHeader2 = new BasicHeader("Content-Type", "application/vnd.elasticsearch+json;compatible-with=7");
        var restClient = RestClient.builder(new HttpHost(host, port))
            .setDefaultHeaders(new Header[]{basicHeader1, basicHeader2})
            .build();
        var transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

}
