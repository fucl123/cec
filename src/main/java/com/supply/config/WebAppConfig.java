package com.supply.config;

import com.supply.handler.LoginUserInformationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author fcl
 * @version v1.0.0
 * @date 2019/10/22
 * @Description
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {


    @Autowired
    private LoginUserInformationInterceptor loginUserInformationInterceptor;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(this.loginUserInformationInterceptor).addPathPatterns("/**");
    }


}
