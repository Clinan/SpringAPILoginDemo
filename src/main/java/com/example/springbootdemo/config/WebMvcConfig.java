package com.example.springbootdemo.config;

import com.example.springbootdemo.interceptor.LoginInterceptor;
import com.example.springbootdemo.resolver.LoginUserResolver;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author lachen
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private LoginUserResolver loginUserResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，指定要拦截的url匹配规则为api开头的请求
        registry.addInterceptor(loginInterceptor).addPathPatterns("/api/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginUserResolver);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> httpMessageConverter : converters) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(StandardCharsets.UTF_8);
            }
            if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter json = (MappingJackson2HttpMessageConverter) httpMessageConverter;
                ObjectMapper mapper = json.getObjectMapper();
                mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                SimpleModule simpleModule = new SimpleModule();
                simpleModule.addSerializer(Long.class, new JsonSerializer<Long>() {
                    @Override
                    public void serialize(Long aLong, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                        jsonGenerator.writeString(String.valueOf(aLong));
                    }
                });
                mapper.registerModule(simpleModule);
                mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            }
        }

    }
}
