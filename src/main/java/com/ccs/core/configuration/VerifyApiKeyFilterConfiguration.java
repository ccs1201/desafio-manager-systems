package com.ccs.core.configuration;

import com.ccs.core.filter.VerifyApiKeyFilter;
import com.ccs.core.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class VerifyApiKeyFilterConfiguration {
    private final TokenRepository repository;

    @Bean
    public FilterRegistrationBean<VerifyApiKeyFilter> verifyApiKeyFilter(){
        var filterFilterRegistrationBean = new FilterRegistrationBean<VerifyApiKeyFilter>();
        filterFilterRegistrationBean.setFilter(new VerifyApiKeyFilter(repository));
        filterFilterRegistrationBean.addUrlPatterns("/api/v1/*");
        return filterFilterRegistrationBean;
    }
}
