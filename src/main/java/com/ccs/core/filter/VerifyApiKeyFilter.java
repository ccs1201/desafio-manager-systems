package com.ccs.core.filter;

import com.ccs.core.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@RequiredArgsConstructor
public class VerifyApiKeyFilter extends GenericFilterBean {
    private static final String HEADER_API_KEY = "api-key";
    private final TokenRepository repository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;

        var apiKey = httpRequest.getHeader(HEADER_API_KEY);

        if (!isBlank(apiKey) && isValidApiKey(apiKey)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            sendUnauthorizedError(httpResponse, apiKey);
        }
    }

    private boolean isValidApiKey(String apiKey) {

//        var token = repository.findByToken(apiKey);
//
//        if (token.isPresent()) {
//            if (token.get().getExpiracao().isAfter(LocalDateTime.now())) {
//                return true;
//            }
//        }
//        return false;

        var result =  repository
                .findByToken(apiKey)
                .filter(t -> t.getExpiracao().isAfter(LocalDateTime.now()))
                .isPresent();

        return result;
    }

    private void sendUnauthorizedError(HttpServletResponse response, String apiKey) throws IOException {
        var errorMessage = isBlank(apiKey) ? "API Key não informada" : "API Key inválida";
        log.error(errorMessage);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentLength(errorMessage.length());
        response.setContentType("plain/text");

        try (Writer out = response.getWriter()) {
            out.write(errorMessage);
        }
    }
}
