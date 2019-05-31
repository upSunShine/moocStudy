package com.ext.springboot.springproducer.config.database;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: xting
 * @CreateDate: 2019/5/29 9:11
 *
 *
 * @Configuration 替代XML配置 相当于<Beans></Beans>
 * @AutoConfigureAfter(MybatisDataSourceConfig.class)  先加载MybatisDataSourceConfig，再加载该类
 */
@Configuration
@AutoConfigureAfter(MybatisDataSourceConfig.class)
public class MybatisMapperScanerConfig {


    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
         MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
         mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
         mapperScannerConfigurer.setBasePackage("com.ext.springboot.springproducer.mapper");
         return mapperScannerConfigurer;
    }

}
