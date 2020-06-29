package com.epam.lab.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
@Configuration
@EnableTransactionManagement
@ComponentScan("com.epam.lab")
public class TestRepositoryConfig {
    @Bean
    DataSource embeddedDataSource() {
        EmbeddedPostgres postgres = null;
        try {
            postgres = EmbeddedPostgres.builder().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (postgres != null) ? postgres.getPostgresDatabase() : null;
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean() throws SQLException {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(embeddedDataSource());
        em.setPackagesToScan(new String[]{"com.epam.lab.model"});
        em.setJpaVendorAdapter(vendorAdapter());
        em.setJpaProperties(hibernateProperties());
        return em;
    }
    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
                Environment.DIALECT, "org.hibernate.dialect.PostgreSQL92Dialect");
        hibernateProperties.setProperty(
                Environment.GLOBALLY_QUOTED_IDENTIFIERS, "true");
        hibernateProperties.setProperty(
                Environment.SHOW_SQL, "true");
        hibernateProperties.setProperty(
                Environment.HBM2DDL_AUTO, "create");
        return hibernateProperties;
    }
    @Bean
    public PlatformTransactionManager hibernateTransactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
    @Bean(initMethod = "migrate", destroyMethod = "clean")
    public Flyway setFlyway() {
        FluentConfiguration fluentConfiguration;
        fluentConfiguration = Flyway.configure().dataSource(embeddedDataSource())
                .cleanDisabled(false);
        Flyway flyway = new Flyway(fluentConfiguration);
        flyway.baseline();
        //flyway.clean();
        return flyway;
    }
    private HibernateJpaVendorAdapter vendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }
}
