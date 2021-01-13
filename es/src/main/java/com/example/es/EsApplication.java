package com.example.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = {"com.example.es.dao"})//开启Elasticsearch，并且指定ES的DAO包扫描
public class EsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsApplication.class, args);
    }

}
