<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>main page</title>
</head>
<body>
<h1>main page</h1>
<hr>

로그인 폼<br>
<c:if test="${sessionScope.loginInfo == null}">

<form method="post" action="/members/login">
    email : <input type="text" name="email"><br>
    암호 : <input type="password" name="passwd"><br>
    <input type="submit" value="확인">

</form>
</c:if>

<c:if test="${sessionScope.loginInfo != null}">
    "${sessionScope.loginInfo.name}" 님 환영합니다. <a href="/members/logout">로그아웃</a>
</c:if>
<br><br>


회원 가입 폼<br>
<form method="post" action="/members">
    이름 : <input type="text" name="name"><br>
    email : <input type="text" name="email"><br>
    암호 : <input type="password" name="passwd1"><br>
    암호 확인 : <input type="password" name="passwd2"><br>
    <input type="submit" value="확인">

</form>
</body>
</html>
