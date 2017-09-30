package com.chengshi.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器配置
 *
 * @author xuxinlong
 * @version 2017年07月22日
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Bean
    H5CheckLoginInterceptor localInterceptor() {
        return new H5CheckLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localInterceptor())
                .excludePathPatterns("/mobile/wechat/**",
                        "/mobile/pay/getWxOrderNotify*")
                .addPathPatterns("/mobile/**");
    }

}
