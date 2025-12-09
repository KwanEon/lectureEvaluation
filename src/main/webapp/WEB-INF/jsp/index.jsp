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

    <section class="container mt-3">
	    <form method="get" action="${pageContext.request.contextPath}/">
	        <div class="row g-2 align-items-center">
	            <div class="col-auto">
	                <select name="lectureDivide" class="form-select">
	                    <option value="전체">전체</option>
	                    <option value="전공">전공</option>
	                    <option value="교양">교양</option>
	                    <option value="기타">기타</option>
	                </select>
	            </div>
	
	            <div class="col-auto">
	                <input type="text" name="search" class="form-control" placeholder="내용을 입력하세요.">
	            </div>
	
	            <div class="col-auto">
	                <button type="submit" class="btn btn-primary">검색</button>
	            </div>
				
				<c:if test="${not empty sessionScope.loginUser}">
		            <div class="col-auto">
		                <a class="btn btn-primary" data-bs-toggle="modal" href="#registerModal">등록하기</a>
		            </div>
		        </c:if>

	        </div>
	    </form>
	    
	    <!-- 평가 목록 -->
	    <c:forEach var="eval" items="${evaluations}">
	        <div class="card bg-light mt-3">
	            <div class="card-header bg-light">
	                <div class="row align-items-center">
	                    <div class="col text-start">
	                        <c:out value="${eval.lectureName}"/>&nbsp;<small><c:out value="${eval.professorName}"/></small>
	                    </div>
	                    <div class="col text-end">
	                        종합 <span class="text-danger">${eval.totalScore}</span>
	                    </div>
	                </div>
	            </div>
	            <div class="card-body">
	                <h5 class="card-title">
	                    <c:out value="${eval.evaluationTitle}"/>&nbsp;<small>(${eval.lectureYear}년 ${eval.semesterDivide})</small>
	                </h5>
	                <p class="card-text">
	                    <c:out value="${eval.evaluationContent}"/>
	                </p>
	                <div class="row align-items-center">
	                    <div class="col text-start">
	                        <span class="me-3">성적 <span class="text-danger">${eval.creditScore}</span></span>
	                        <span class="me-3">널널 <span class="text-danger">${eval.comfortableScore}</span></span>
	                        <span class="me-3">강의 <span class="text-danger">${eval.lectureScore}</span></span>
	                        <span class="text-success">(추천: ${eval.likeCount})</span>
	                    </div>
	                    <div class="col text-end">
	                        <c:if test="${not empty sessionScope.loginUser and sessionScope.loginUser.id != eval.userId}">
	                        	<form action="${pageContext.request.contextPath}/evaluation/like/${eval.id}" method="post" style="display:inline;" class="me-2">
	                        		<button type="submit" onClick="return confirm('추천하시겠습니까?')" class="btn btn-link p-0 m-0 align-baseline">
	                        			추천
	                        		</button>
	                        	</form>
	                        </c:if>

	                        <c:if test="${sessionScope.loginUser.id == eval.userId}">
							    <form action="${pageContext.request.contextPath}/evaluation/${eval.id}" method="post" style="display:inline;">
							        <input type="hidden" name="_method" value="delete"/>
							        <button type="submit" onclick="return confirm('삭제하시겠습니까?')" class="btn btn-link p-0 m-0 align-baseline">
							            삭제
							        </button>
							    </form>
							</c:if>

	                        <!-- 신고 버튼 -->
	                        <c:if test="${not empty sessionScope.loginUser and sessionScope.loginUser.id != eval.userId}">
	                            <button type="button"
	                                    class="btn btn-link p-0 m-0 align-baseline text-danger"
	                                    data-bs-toggle="modal"
	                                    data-bs-target="#reportModal${eval.id}">
	                                신고
	                            </button>
	                        </c:if>
	                    </div>
	                </div>
	            </div>
	        </div>

	        <!-- 각 평가별 신고 모달 -->
	        <div class="modal fade" id="reportModal${eval.id}" tabindex="-1" aria-labelledby="reportModalLabel${eval.id}" aria-hidden="true">
	            <div class="modal-dialog">
	                <div class="modal-content">
	                    <div class="modal-header">
	                        <h5 class="modal-title" id="reportModalLabel${eval.id}">평가 신고</h5>
	                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	                    </div>

	                    <div class="modal-body">
	                        <form action="${pageContext.request.contextPath}/report/${eval.id}" method="post">
	                            <input type="hidden" name="evaluationId" value="${eval.id}" />

	                            <div class="mb-3">
	                                <label for="reportTitle${eval.id}" class="form-label">신고 제목</label>
	                                <input type="text" name="reportTitle" id="reportTitle${eval.id}" class="form-control" maxlength="50">
	                            </div>
	                            <div class="mb-3">
	                                <label for="reportContent${eval.id}" class="form-label">신고 내용</label>
	                                <textarea name="reportContent" id="reportContent${eval.id}" class="form-control" maxlength="2048" style="height:180px;"></textarea>
	                            </div>

	                            <div class="modal-footer">
	                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	                                <button type="submit" class="btn btn-danger">신고하기</button>
	                            </div>
	                        </form>
	                    </div>
	                </div>
	            </div>
	        </div>

	    </c:forEach>

	    <!-- 페이징 블록 -->
	    <c:if test="${not empty paginationInfo}">
	        <c:set var="pi" value="${paginationInfo}" />
	
	        <nav aria-label="Page navigation">
	            <ul class="pagination justify-content-center mt-4">
	
	                <!-- 이전 페이지 -->
	                <li class="page-item ${pi.currentPageNo == 1 ? 'disabled' : ''}">
	                    <c:url var="prevUrl" value="/">
	                        <c:if test="${not empty lectureDivide}">
	                            <c:param name="lectureDivide" value="${lectureDivide}" />
	                        </c:if>
	                        <c:if test="${not empty search}">
	                            <c:param name="search" value="${search}" />
	                        </c:if>
	                        <c:param name="page" value="${pi.currentPageNo - 1}" />
	                    </c:url>
	
	                    <a class="page-link" href="${prevUrl}">이전</a>
	                </li>
	
	                <!-- 페이지 블록 -->
	                <c:forEach var="i"
	                           begin="${pi.firstPageNoOnPageList}"
	                           end="${pi.lastPageNoOnPageList}">
	
	                    <li class="page-item ${i == pi.currentPageNo ? 'active' : ''}">
	                        <c:url var="pageUrl" value="/">
	                            <c:if test="${not empty lectureDivide}">
	                                <c:param name="lectureDivide" value="${lectureDivide}" />
	                            </c:if>
	                            <c:if test="${not empty search}">
	                                <c:param name="search" value="${search}" />
	                            </c:if>
	                            <c:param name="page" value="${i}" />
	                        </c:url>
	
	                        <a class="page-link" href="${pageUrl}">${i}</a>
	                    </li>
	                </c:forEach>
	
	                <!-- 다음 페이지 -->
	                <li class="page-item ${pi.currentPageNo == pi.totalPageCount ? 'disabled' : ''}">
	                    <c:url var="nextUrl" value="/">
	                        <c:if test="${not empty lectureDivide}">
	                            <c:param name="lectureDivide" value="${lectureDivide}" />
	                        </c:if>
	                        <c:if test="${not empty search}">
	                            <c:param name="search" value="${search}" />
	                        </c:if>
	                        <c:param name="page" value="${pi.currentPageNo + 1}" />
	                    </c:url>
	
	                    <a class="page-link" href="${nextUrl}">다음</a>
	                </li>
	
	            </ul>
	        </nav>
	    </c:if>
	    
	</section>

    <!-- 등록하기 모달 -->
	<div class="modal fade" id="registerModal" tabindex="-1" aria-labelledby="registerModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="registerModalLabel">평가 등록</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	            </div>
	
	            <div class="modal-body">
	                <form action="${pageContext.request.contextPath}/evaluation/add" method="post">
	                    <!-- 강의명 / 교수명 -->
	                    <div class="row mb-3">
	                        <div class="col-sm-6">
	                            <label for="lectureName" class="form-label">강의명</label>
	                            <input type="text" name="lectureName" id="lectureName" class="form-control" maxlength="50">
	                        </div>
	                        <div class="col-sm-6">
	                            <label for="professorName" class="form-label">교수명</label>
	                            <input type="text" name="professorName" id="professorName" class="form-control" maxlength="20">
	                        </div>
	                    </div>
	
	                    <!-- 수강 연도 / 학기 / 강의 구분 -->
	                    <div class="row mb-3">
	                        <div class="col-sm-4">
	                            <label for="lectureYear" class="form-label">수강 연도</label>
	                            <select name="lectureYear" id="lectureYear" class="form-control">
	                                <option value="2016">2016</option>
	                                <option value="2017">2017</option>
	                                <option value="2018">2018</option>
	                                <option value="2019">2019</option>
	                                <option value="2020">2020</option>
	                                <option value="2021">2021</option>
	                                <option value="2022">2022</option>
	                                <option value="2023">2023</option>
	                                <option value="2024">2024</option>
	                                <option value="2025" selected>2025</option>
	                                <option value="2026">2026</option>
	                                <option value="2027">2027</option>
	                                <option value="2028">2028</option>
	                            </select>
	                        </div>
	                        <div class="col-sm-4">
	                            <label for="semesterDivide" class="form-label">수강 학기</label>
	                            <select name="semesterDivide" id="semesterDivide" class="form-control">
	                                <option value="1학기" selected>1학기</option>
	                                <option value="여름학기">여름학기</option>
	                                <option value="2학기">2학기</option>
	                                <option value="겨울학기">겨울학기</option>
	                            </select>
	                        </div>
	                        <div class="col-sm-4">
	                            <label for="lectureDivide" class="form-label">강의 구분</label>
	                            <select name="lectureDivide" id="lectureDivide" class="form-control">
	                                <option value="전공" selected>전공</option>
	                                <option value="교양">교양</option>
	                                <option value="기타">기타</option>
	                            </select>
	                        </div>
	                    </div>
	
	                    <!-- 제목 -->
	                    <div class="mb-3">
	                        <label for="evaluationTitle" class="form-label">제목</label>
	                        <input type="text" name="evaluationTitle" id="evaluationTitle" class="form-control" maxlength="50">
	                    </div>
	
	                    <!-- 내용 -->
	                    <div class="mb-3">
	                        <label for="evaluationContent" class="form-label">내용</label>
	                        <textarea name="evaluationContent" id="evaluationContent" class="form-control" maxlength="2048" style="height: 180px;"></textarea>
	                    </div>
	
	                    <!-- 점수 -->
	                    <div class="row mb-3">
	                        <div class="col-sm-3">
	                            <label for="totalScore" class="form-label">종합</label>
	                            <select name="totalScore" id="totalScore" class="form-control">
	                                <option value="A" selected>A</option>
	                                <option value="B">B</option>
	                                <option value="C">C</option>
	                                <option value="D">D</option>
	                                <option value="F">F</option>
	                            </select>
	                        </div>
	                        <div class="col-sm-3">
	                            <label for="creditScore" class="form-label">성적</label>
	                            <select name="creditScore" id="creditScore" class="form-control">
	                                <option value="A" selected>A</option>
	                                <option value="B">B</option>
	                                <option value="C">C</option>
	                                <option value="D">D</option>
	                                <option value="F">F</option>
	                            </select>
	                        </div>
	                        <div class="col-sm-3">
	                            <label for="comfortableScore" class="form-label">널널</label>
	                            <select name="comfortableScore" id="comfortableScore" class="form-control">
	                                <option value="A" selected>A</option>
	                                <option value="B">B</option>
	                                <option value="C">C</option>
	                                <option value="D">D</option>
	                                <option value="F">F</option>
	                            </select>
	                        </div>
	                        <div class="col-sm-3">
	                            <label for="lectureScore" class="form-label">강의</label>
	                            <select name="lectureScore" id="lectureScore" class="form-control">
	                                <option value="A" selected>A</option>
	                                <option value="B">B</option>
	                                <option value="C">C</option>
	                                <option value="D">D</option>
	                                <option value="F">F</option>
	                            </select>
	                        </div>
	                    </div>
	
	                    <div class="modal-footer">
	                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	                        <button type="submit" class="btn btn-primary">등록하기</button>
	                    </div>
	                </form>
	            </div>
	        </div>
	    </div>
	</div>
	
	<%@ include file="/WEB-INF/jsp/layout/footer.jsp" %>

</body>
</html>