package carami.todo.intercepter;

import carami.todo.dto.NaverLoginUser;
import carami.todo.security.SecurityContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        NaverLoginUser naverLoginUser = (NaverLoginUser)session.getAttribute("loginUser");
        String path = request.getRequestURI();

        // login정보가 있을 경우 SEcurityContext에 값을 할당한다.
        if(naverLoginUser != null) {
            SecurityContext.loginUser.set(naverLoginUser);
        }

        System.out.println("path : " + path);

        if("/loginpage".equals(path)){
            if(naverLoginUser == null){
                response.sendRedirect("/");
                return false;
            }
        }
        return true;
    }
}
