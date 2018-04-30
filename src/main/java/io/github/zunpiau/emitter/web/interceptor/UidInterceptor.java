package io.github.zunpiau.emitter.web.interceptor;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class UidInterceptor extends HandlerInterceptorAdapter {

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoggerFactory.getLogger(getClass()).debug("preHandle [{}]", request.getServletPath());
        if (request.getMethod().equals(HttpMethod.POST.name())
            && request.getContentLength() <= 0) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        Map<String, String> pathVariables = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String uid;
        if (pathVariables.size() != 1
            || (uid = pathVariables.get("uid")) == null
            || uid.length() != 8) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }
        return super.preHandle(request, response, handler);
    }
}
