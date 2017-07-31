package carami.todo.security;

import carami.todo.dto.NaverLoginUser;

public class SecurityContext {
    public static ThreadLocal<NaverLoginUser> loginUser = new ThreadLocal<NaverLoginUser>();
}