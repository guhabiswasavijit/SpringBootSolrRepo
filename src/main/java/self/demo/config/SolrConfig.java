package self.demo.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableSolrRepositories(basePackages = "self.demo.repository", namedQueriesLocation = "classpath:solr-named-queries.properties")
@ComponentScan
public class SolrConfig {

    @Bean
    public SolrClient solrClient() {
        return new HttpSolrClient.Builder("http://localhost:8082/solr" ).build();
    }
    @Bean
    public SolrTemplate solrTemplate(SolrClient client) throws Exception {
        return new SolrTemplate(client);
    }
    @Bean
    public CustomRestTemplateCustomizer customRestTemplateCustomizer() {
        return new CustomRestTemplateCustomizer();
    }
    @Bean
    @DependsOn(value = {"customRestTemplateCustomizer"})
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder(customRestTemplateCustomizer());
    }
    @Bean(name="simpleRestTemplate")
    @Primary
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        RestTemplate template = restTemplateBuilder.build();
        return template;
    }
}