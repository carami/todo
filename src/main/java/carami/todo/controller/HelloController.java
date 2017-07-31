package carami.todo.controller;

import carami.todo.dto.NaverLoginUser;
import carami.todo.dto.NaverLoginUserResult;
import carami.todo.security.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by 강경미 on 2017. 6. 3..
 */
@Controller
public class HelloController {
    @Autowired
//    @Qualifier("restTemplate")
    RestTemplate restTemplate;

    String clientId = "ZLP834mSIldLNVIRkjnA";//애플리케이션 클라이언트 아이디값";

    @GetMapping(path = "/")
    public String hello(@AuthUser NaverLoginUser naverLoginUser, HttpServletRequest request){
        System.out.println("----------------------------------------------------------");
        System.out.println("ArgumentResolver에서 넘긴 이름 : " + naverLoginUser.getName());
        System.out.println("----------------------------------------------------------");
        String callbackUrl = "http://localhost:8080/naver_callback";
        String naverLoginUrl = getNaverLoginUrl(callbackUrl, request.getSession());
        request.setAttribute("naverLoginUrl", naverLoginUrl);
        return "hello";
    }

    @GetMapping(path = "/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");
        return "redirect:/";
    }

    @GetMapping(path = "/loginpage")
    public String loginpage(){
        return "loginpage";
    }


    @GetMapping(path = "/naver_callback")
    public String naverCallback(
            @RequestParam(name = "code")String code, @RequestParam(name = "state")String state,
            HttpServletRequest request){
//        Enumeration<String> headerNames = request.getHeaderNames();
//        System.out.println("Header Values ----------------------------------");
//        while(headerNames.hasMoreElements()){
//            String headerName = headerNames.nextElement();
//            String hedaerValue = request.getHeader(headerName);
//            System.out.println(headerName + " : " + hedaerValue);
//        }
//
//        System.out.println("request Values ----------------------------------");
//        Enumeration<String> parameterNames = request.getParameterNames();
//        while(parameterNames.hasMoreElements()){
//            String parameterName = parameterNames.nextElement();
//            String parameterValue = request.getParameter(parameterName);
//            System.out.println(parameterName + " : " + parameterValue);
//        }

        HttpSession session = request.getSession();
        String sessionState = (String)session.getAttribute("state"); // 세션에 저장된 state값 읽어오기
        if(!state.equals(sessionState)){ // clientId 혹은  state값이 올바르지 않을 경우
            throw new RuntimeException("잘못된 callback 입니다.");
        }

        Map<String, String> tokenMap = getToken(code, state);
        String accessToken = tokenMap.get("access_token");
        NaverLoginUser naverLoginUser = getNaverLoginUser(accessToken);
        System.out.println(naverLoginUser);

        if(naverLoginUser != null){
            session.setAttribute("loginUser",naverLoginUser);
        }

        return "hello";
    }


    private String getNaverLoginUrl(String redirectURI, HttpSession session){


        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString();
        String naverLoginUrl = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
        naverLoginUrl += "&client_id=" + clientId;
        naverLoginUrl += "&redirect_uri=" + redirectURI;
        naverLoginUrl += "&state=" + state;
        session.setAttribute("state", state);

        return naverLoginUrl;
    }


    private NaverLoginUser getNaverLoginUser(String accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        StringBuilder urlBuilder = new StringBuilder("https://openapi.naver.com/v1/nid/me");

        Map<String, Object> map = null;
        try {
            ResponseEntity<NaverLoginUserResult> responseEntity = restTemplate.exchange(urlBuilder.toString(), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<NaverLoginUserResult>(){});
            NaverLoginUser response = responseEntity.getBody().getResponse();

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Map<String, String> getToken(String code, String state){
        String clientSecret = "1dsQJuSFxk";//애플리케이션 클라이언트 시크릿값";

        String redirectURI = null;
        try {
            redirectURI = URLEncoder.encode("http://localhost:8080/naver_callback", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String apiURL;
        apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
        apiURL += "client_id=" + clientId;
        apiURL += "&client_secret=" + clientSecret;
        apiURL += "&redirect_uri=" + redirectURI;
        apiURL += "&code=" + code;
        apiURL += "&state=" + state;

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        StringBuilder urlBuilder = new StringBuilder(apiURL);

        Map<String, String> map = null;
        try {
            ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(urlBuilder.toString(), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<Map<String,String>>(){});
            map = responseEntity.getBody();

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
