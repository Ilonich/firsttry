package ru.ilonych.testtask.configclasses;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.ilonych.testtask.entity.User;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Никола on 25.07.2016.
 */

@Configuration
@EnableTransactionManagement
public class Datconfig {

    private static final String USER_NAME = "root";
    private static final String USER_PASSWORD = "root";
    //_______________________________________________________________\DB NAME/__https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-configuration-properties.html
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8_unicode_ci&characterSetResults=utf8";

    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(USER_NAME);
        dataSource.setPassword(USER_PASSWORD);
        return dataSource;
    }

    @Bean
    public SessionFactory sessionFactory(){
        return new LocalSessionFactoryBuilder(dataSource()).addAnnotatedClasses(User.class).buildSessionFactory();
/*      LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setAnnotatedClasses(ru.ilonych.testtask.entity.User.class);

        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.show_sql", false);
        properties.put("hibernate.format_sql", false);
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.current_session_context_class", "thread");
        localSessionFactoryBean.setHibernateProperties(properties);
        try {
            localSessionFactoryBean.afterPropertiesSet();
        } catch (IOException e) {
            //точн?
        }
        return localSessionFactoryBean.getObject();*/
    }

    @Bean
    public HibernateTransactionManager hibTransMan(){
        return new HibernateTransactionManager(sessionFactory());
    }

/*
    //test
    @Bean (name = "UserDAOimpl")
    public UserDAOimpl userDAOimpl(){
        UserDAOimpl userDAOimpl = new UserDAOimpl();
        userDAOimpl.setSessionFactory(localSessionFactoryBean().getObject());
        return userDAOimpl;
    }*/
}
