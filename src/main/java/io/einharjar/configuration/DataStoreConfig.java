package io.einharjar.configuration;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import javax.sql.DataSource;

@Configuration
@EnableJpaAuditing
@EntityScan(value = {"io.einharjar.domain.persistence.entity", "io.einharjar.domain.persistence.entity.common"})
@EnableJpaRepositories("io.einharjar.domain.persistence.repository")
public class DataStoreConfig {

    @Bean
    @ConfigurationProperties(prefix="app.datasource")
    public DataSource dataSource() throws Exception{
        return DataSourceBuilder.create().build();
    }

}
