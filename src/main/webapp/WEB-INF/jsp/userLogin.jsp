<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/layout/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>강의평가 웹사이트</title>
</head>
<body>

    <section class="container mt-3" style="max-width: 400px;">
        <form method="post" action="${pageContext.request.contextPath}/user/login">
            <div class="form-group mt-3">
                <label>아이디</label>
                <input type="text" name="userName" class="form-control">
            </div>
            <div class="form-group mt-3 mb-3">
                <label>비밀번호</label>
                <input type="password" name="userPassword" class="form-control">
            </div>
            <button type="submit" class="btn btn-primary">로그인</button>
        </form>
    </section>

	<%@ include file="/WEB-INF/jsp/layout/footer.jsp" %>
	
</body>
</html>
