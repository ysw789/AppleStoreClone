<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/apple.css?after3">
    <title>Apple Korea</title>
</head>
<script>
    const confirmLeave = () => {
        if (confirm('정말 탈퇴하시겠습니까?')) window.location.href = '/user/leave';
    }
    const chatWinOpen = (chatId) => {
        window.open("../chatclient/ChatWindow.jsp?chatId=" + chatId, "", "width=320,height=400");
    }
</script>
<body>
<%@ include file="../modules/header.jsp"%>
<section class="main-user">
    <div class="mypage-container">
        <h1 class="mypage-title">마이페이지</h1>
        <div class="mypage-menu">
            <ul>
                <li><a href="#">주문내역</a></li>
                <%-- request.getAttribute("orderList") --%>
                <li><a href="${pageContext.request.contextPath}/user/info-update">개인정보 변경</a></li>
                <li><a href="${pageContext.request.contextPath}/user/pw-change">비밀번호 변경</a></li>
            </ul>
        </div>
        <c:if test="${not empty sessionScope.isAdmin}">
            <button type="button" onclick="location.href='/admin-page'">관리자 페이지</button>
        </c:if>
        <button type="button" onclick="chatWinOpen('user')">실시간 상담</button>
        <button class="mypage-button" onclick="confirmLeave()" type="button">회원 탈퇴</button>
    </div>
</section>
<%@ include file="../modules/footer.jsp"%>
</body>
</html>