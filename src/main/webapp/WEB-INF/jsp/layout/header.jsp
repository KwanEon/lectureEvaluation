<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 <!-- Bootstrap CSS -->
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
 <!-- 커스텀 CSS -->
 <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">강의평가 웹 사이트</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
            data-bs-target="#navbar">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div id="navbar" class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active"
                       href="${pageContext.request.contextPath}/">메인</a>
                </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button"
                       data-bs-toggle="dropdown">
                        회원 관리
                    </a>

                    <ul class="dropdown-menu">
                        <c:choose>
                            <%-- 로그인 상태 --%>
                            <c:when test="${not empty sessionScope.loginUser}">
                                <li class="dropdown-item text-primary fw-bold text-center">
                                    ${sessionScope.loginUser.userName} 님
                                </li>
                                <li><hr class="dropdown-divider"></li>
                                <li>
                                    <a class="dropdown-item"
                                       href="${pageContext.request.contextPath}/user/logout">
                                        로그아웃
                                    </a>
                                </li>
                            </c:when>

                            <%-- 비로그인 상태 --%>
                            <c:otherwise>
                                <li>
                                    <a class="dropdown-item"
                                       href="${pageContext.request.contextPath}/user/login">
                                        로그인
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item"
                                       href="${pageContext.request.contextPath}/user/join">
                                        회원가입
                                    </a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<%-- 메시지 알림 --%>
<c:if test="${not empty message}">
    <script>
        alert("${message}");
    </script>
</c:if>
