# Naver Login

## Naver에서 애플리케이션 등록

1. https://developers.naver.com/apps/#/list 에서 Application 등록버튼 클릭

2. 애플리케이션 정보 등록

1.png
2.png
3.png 

참고

3. 로그인, 사용자 정보등을 가지고 오기 위해서 Naver 문서를 참조한다

https://developers.naver.com/docs/login/devguide/

예제>
https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=CLIENT_ID&state=STATE_STRING&redirect_uri=CALLBACK_URL

로그인 버튼을 누르면 위의 URL로 요청이 간다.

- 네이버에 로그을을 하지 않은 상황이라면 todo 를 사용하려면 로그인 해야합니다 라는 메시지와 함께 id, password입력창이 보여진다. 아이디, 암호를 입력하여 로그인을 하면 todo앱에 어떤 정보를 제공하는지 보여준다.
  "동의하기"버튼을 클릭하게 되면 callback URL로 redirect하게 된다
  
- callBack으로 넘어온 정보를 출력하면 다음과 같다. 제대로 넘어왔는지 확인하기 위하여 Session에 저장해둔 state값과 파라미터로 넘어온 State값을 비교한다.

Header Values ----------------------------------
host : localhost:8080
connection : keep-alive
cache-control : max-age=0
upgrade-insecure-requests : 1
user-agent : Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36
accept : text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8
referer : http://localhost:8080/naver_callback?code=v2p7b2jcu30CmeJ1&state=916882313730767219999586828602446552295
accept-encoding : gzip, deflate, br
accept-language : ko,en-US;q=0.8,en;q=0.6
cookie : Idea-6296546d=26b39535-a7fb-4ddc-8989-f3f7b4b66bb9; SCOUTER=xs2ri0r5003jj; JSESSIONID=8B1C39C006177C355F532784D661C3E5
request Values ----------------------------------
code : v2p7b2jcu30CmeJ1
state : 916882313730767219999586828602446552295

4. 여기까지 내용을 구현해 보자.

4-1. hello.jsp 에 다음의 내용을 추가한다.

```
<a href="${naverLoginUrl}"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>

```

네이버 이미지를 클릭하면 ${naverLoginUrl}로 이동한다. 위에서 말한 네이버 로그인 URL

4.2. HelloController를 다음과 같이 수정한다.

```
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
```

getNaverLoginUrl() 메소드는 redirectUrl을 전달받아서 naver login을 위한 url 문자열을 만든다. 그리고 session에 state값을 random하게 만들어 저장한다.
추후에 redirect_url에 파라미터로 넘어온 값과 비교하여 해당 url이 올바른지 검증하는 목적으로 사용된다.

/naver_callback 은 네이버에서 앱을 등록할 때 입력한 URL이다. 여기에서는 redirect되었을 때 네이버에서 어떤 값을 넘기는지 확인하는 용도로 사용해보았다.
code, state 값이 파라미터로 넘어오는 것을 알 수 있다. 2가지 값을 callback_url에서 확인해야한다.

