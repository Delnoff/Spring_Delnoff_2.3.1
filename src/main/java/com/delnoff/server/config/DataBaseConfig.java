package com.delnoff.server.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan("com.delnoff.server")
public class DataBaseConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(env.getRequiredProperty("db.url"));
        basicDataSource.setUsername(env.getRequiredProperty("db.username"));
        basicDataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        basicDataSource.setPassword(env.getRequiredProperty("db.password"));

        basicDataSource.setInitialSize(Integer.parseInt(env.getRequiredProperty("db.initialSize")));
        basicDataSource.setMinIdle(Integer.parseInt(env.getRequiredProperty("db.minIdle")));
        basicDataSource.setMaxIdle(Integer.parseInt(env.getRequiredProperty("db.maxIdle")));
        basicDataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(env.getRequiredProperty("db.timeBetweenEvictionRunMillis")));
        basicDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(env.getRequiredProperty("db.minEvictableIdleTimeMillis")));
        basicDataSource.setTestOnBorrow(Boolean.parseBoolean(env.getRequiredProperty("db.testOnBorrow")));
        basicDataSource.setValidationQuery(env.getRequiredProperty("db.validationQuery"));
        return basicDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean locen = new LocalContainerEntityManagerFactoryBean();
        locen.setDataSource(getDataSource());
        locen.setPackagesToScan(env.getRequiredProperty("db.entity.package"));

        Properties properties = new Properties();
        properties.put("hibernate.show_sql",env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto",env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));

        locen.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        locen.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        locen.setJpaProperties(properties);

        return locen;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(getEntityManagerFactory().getObject());
        return manager;
    }

}
