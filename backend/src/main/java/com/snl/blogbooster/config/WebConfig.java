package com.snl.blogbooster.config;

import com.snl.blogbooster.interceptor.AuthenticateInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
/* Bean 설정을 담당하는 Class Annotation*/
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    /*
    WebMvcConfigurer 를 사용하면 @EnableWebMvc가 자아동적으로 세팅해주는 설정에 커스텀 할수있음
    */

    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        /*Cors란 다른 출처의 자원을 공유할수있도록 설정하는 권한체제 */
        registry.addMapping("/**") // Cors에 적용할 URL패턴
                .allowedMethods("*") // 허용할 호출방식 설정 ex) POST , GET
                .allowedOrigins("*");// 자원공유를 허락할 Origin설정 ex) http://localhost:8080
    }
    /**
     * Interceptor
     */
    @Bean
    public AuthenticateInterceptor authenticateInterceptor() {
        return new AuthenticateInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticateInterceptor()) // Interceptor 클라스를 등록하고
                .addPathPatterns("/v1/**") // /v1/ 으로 시작하는 모든 호출에 Interceptor를 적용 
                .excludePathPatterns("/v1/data/influence"); // Interceptor를 예외를 두는 호출패턴 등록
    }
}
