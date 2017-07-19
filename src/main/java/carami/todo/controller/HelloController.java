package carami.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Enumeration;

/**
 * Created by 강경미 on 2017. 6. 3..
 */
@Controller
public class HelloController {
    @GetMapping(path = "/")
    public String hello(HttpServletRequest request){
        String callbackUrl = "http://localhost:8080/naver_callback";
        String naverLoginUrl = getNaverLoginUrl(callbackUrl, request.getSession());
        request.setAttribute("naverLoginUrl", naverLoginUrl);
        return "hello";
    }

    @GetMapping(path = "/naver_callback")
    public String naverCallback(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println("Header Values ----------------------------------");
        while(headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            String hedaerValue = request.getHeader(headerName);
            System.out.println(headerName + " : " + hedaerValue);
        }

        System.out.println("request Values ----------------------------------");
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();
            String parameterValue = request.getParameter(parameterName);
            System.out.println(parameterName + " : " + parameterValue);
        }

        return "hello";
    }


    private String getNaverLoginUrl(String redirectURI, HttpSession session){
        String clientId = "ZLP834mSIldLNVIRkjnA";//애플리케이션 클라이언트 아이디값";

        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString();
        String naverLoginUrl = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
        naverLoginUrl += "&client_id=" + clientId;
        naverLoginUrl += "&redirect_uri=" + redirectURI;
        naverLoginUrl += "&state=" + state;
        session.setAttribute("state", state);

        return naverLoginUrl;
    }

}
