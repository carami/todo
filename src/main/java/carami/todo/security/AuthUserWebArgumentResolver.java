package carami.todo.security;

import carami.todo.dto.NaverLoginUser;
import org.springframework.core.MethodParameter;

import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthUserWebArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        System.out.println("supportsParameter!!!!!!!!!!!!!!!!!!");
        AuthUser loginUser = parameter.getParameterAnnotation( AuthUser.class );
        if(loginUser == null)
            return false;
        else
            return true;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        System.out.println("resolveArgument!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        // 파라미터에 LoginUser어노테이션이 적용된 파라미터를 찾는다.
        AuthUser loginUser = parameter.getParameterAnnotation( AuthUser.class );

        if( loginUser == null ) {
            // 어노테이션이 없을 경우에는 값 할당을 null로 바꾼다. SecurityContext로부터 값을 읽어들일 수 없도록 한다.
            SecurityContext.loginUser.set(null);
            return WebArgumentResolver.UNRESOLVED;
        }

        // 인터셉터에서 설정한 NaverLoginUser객체를 반환한다.
        return SecurityContext.loginUser.get();
    }
}