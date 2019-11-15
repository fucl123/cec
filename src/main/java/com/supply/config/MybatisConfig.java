package com.supply.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fcl
 * @version v1.0.0
 * @date 2019/10/28
 * @Description
 */
@Configuration
public class MybatisConfig {
        /**
         * 分页插件
         * @return
         */
        @Bean
        public PaginationInterceptor paginationInterceptor() {
            return new PaginationInterceptor();
        }
}
