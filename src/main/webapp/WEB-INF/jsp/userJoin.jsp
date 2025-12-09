<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/layout/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>강의평가 웹사이트</title>
</head>
<body>

    <section class="container mt-3" style="max-width: 400px;">
        <form:form method="post" action="${pageContext.request.contextPath}/user/join" modelAttribute="userDTO">

            <div class="form-group mt-3">
                <label>아이디</label>
                <form:input path="userName" class="form-control" />
                <form:errors path="userName" Class="error-msg" />
            </div>

            <div class="form-group mt-3 mb-3">
                <label>비밀번호</label>
                <form:password path="userPassword" class="form-control" />
                <form:errors path="userPassword" Class="error-msg" />
            </div>

            <div class="form-group mt-3 mb-3">
                <label>이메일</label>
                <form:input path="userEmail" type="email" class="form-control" />
                <form:errors path="userEmail" Class="error-msg" />
            </div>

            <button type="submit" class="btn btn-primary">회원가입</button>
        </form:form>
    </section>
    
    <%@ include file="/WEB-INF/jsp/layout/footer.jsp" %>

</body>
</html>