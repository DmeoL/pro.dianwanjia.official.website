package pro.dianwanjia.official.website.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;


/**
 * @author LX
 * @version V1.0.0
 * @date: 2019/8/20 12:03
 * @description: swagger配置
 */
@Configuration
@ConditionalOnClass(DataSourceInitializer.class)
//@ConditionalOnResource(resources = "classpath:mybatis-mybatis-mapper/*.xml")
public class DataBaseConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseConfig.class);

    @Primary
    @Bean(name = "dbDataSource")
    @ConfigurationProperties(prefix = "datasource.db")
    public DataSource dbDataSource() {
        LOGGER.info("===>>>>>>   datasource create build");
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dbDataSourceInitializer")
    public DataSourceInitializer dataSourceInitializer(@Qualifier("dbDataSource") DataSource dbDataSource) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dbDataSource);
        ClassPathResource classPathResource_db = new ClassPathResource("db.sql");

        if (classPathResource_db.exists()) {
            LOGGER.info("get db.sql");
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
            databasePopulator.addScript(classPathResource_db);
            dataSourceInitializer.setDatabasePopulator(databasePopulator);
            dataSourceInitializer.setEnabled(true);

        } else {
            dataSourceInitializer.setEnabled(false);
        }

        ClassPathResource classPathResource_table = new ClassPathResource("table.sql");

        if (classPathResource_table.exists()) {
            LOGGER.info("get table.sql");
            ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
            databasePopulator.addScript(classPathResource_table);
            dataSourceInitializer.setDatabasePopulator(databasePopulator);
            dataSourceInitializer.setEnabled(true);
        } else {
            dataSourceInitializer.setEnabled(false);
        }
        return dataSourceInitializer;
    }

    @Bean(name = "dbSqlSessionFactory")
    public SqlSessionFactory dbSqlSessionFactory(@Qualifier("dbDataSource") DataSource dbDataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dbDataSource);
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mybatis-mybatis-mapper/*" +
                    ".xml");
            if (resources != null) {
                bean.setMapperLocations(resources);
                LOGGER.info("set mybatis-mybatis-mapper resources:{}", resources);
            }
        } catch (Exception e) {
        }
        return bean.getObject();
    }

    @Bean(name = "dbTransactionManager")
    public DataSourceTransactionManager dbTransactionManager(@Qualifier("dbDataSource") DataSource dbDataSource) {
        return new DataSourceTransactionManager(dbDataSource);
    }

    @Bean(name = "dbSqlSessionTemplate")
    public SqlSessionTemplate dbSqlSessionTemplate(
            @Qualifier("dbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    

}
