<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>hello</title>
</head>
<body>
<h1>Hello World</h1>


<c:if test="${loginUser eq null}">
    <a href="${naverLoginUrl}"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
</c:if>
<c:if test="${loginUser ne null}">
    email : ${loginUser.email}<br>
    별명 : ${loginUser.nickname}<br>
    <img src="${loginUser.profileImage}">이름 : ${loginUser.name}<br>
    <a href="/logout">로그아웃</a>
</c:if>

</body>
</html>
