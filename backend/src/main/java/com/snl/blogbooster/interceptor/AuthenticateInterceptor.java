package com.snl.blogbooster.interceptor;

import com.snl.blogbooster.exception.ErrorCode;
import com.snl.blogbooster.exception.RestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticateInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("[AuthenticateInterceptor]");
        /*
            register에 등록된 인터셉터 패턴 호출이 들어왔을때 처음 수행되는것.
            세션 확인 할때 사용하면 될것같음
            실제 controller가 수행되기 전에 실행됨.
        */

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        /* controller 가 수행된 후에 실행됨 */
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        /* 뷰가 그려지는 과정까지 모두 끝난 다음에 호출 실행됨*/
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
