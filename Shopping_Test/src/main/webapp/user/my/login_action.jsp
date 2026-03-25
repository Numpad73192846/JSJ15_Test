<!-- 로그인 처리 -->
<%@ include file="/layout/jstl.jsp" %>
<%@ include file="/layout/common.jsp" %>
<%@page import="java.util.UUID"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="shop.dto.User"%>
<%@page import="shop.dao.UserRepository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	
	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	
	String rememberId = request.getParameter("remember-id");
	String rememberMe = request.getParameter("remember-me");
	
	UserRepository userDAO = new  UserRepository();
	User loginUser = userDAO.login(id, pw);
	
	// TODO: 로그인 실패
	// - 로그인 페이지로 이동(에러코드 전달)
	
	if ( loginUser == null ) {
		response.sendRedirect("login.jsp?error=0");
		return;
	}
	
	// TODO: 로그인 성공
	// - 세션에 아이디(loginId) 등록
	// - 주문목록(orderList) 초기화
	//  (로그인 후 주문목록은 새로 조회해야 하므로 null로 설정)
	// - 아이디 저장, 자동 로그인 쿠키 처리
	// - 로그인 성공 페이지로 이동
	session.setAttribute("loginId", loginUser.getId());
	session.setAttribute("orderList", null);
	
	// TODO: 아이디 저장
	// - 아이디 저장 체크 시 : 쿠키 생성
	// - 아이디 저장 체크 해제 시 : 쿠키 삭제
	if ( rememberId != null ) {
		Cookie rememberIdCookie = new Cookie("rememberId", URLEncoder.encode(loginUser.getId(), "UTF-8"));
		rememberIdCookie.setPath("/");
		rememberIdCookie.setMaxAge(7 * 24 * 60 * 60);
		response.addCookie(rememberIdCookie);
	}
	else {
		Cookie rememberIdCookie = new Cookie("rememberId", "");
		rememberIdCookie.setPath("/");
		rememberIdCookie.setMaxAge(0);
		response.addCookie(rememberIdCookie);
	}
	
	// TODO: 자동 로그인
	// - 자동 로그인 체크 시 : 쿠키 생성(토큰 발급)
	// - 자동 로그인 체크 해제 시 : 쿠키 삭제
	
	// TODO: 쿠키 전달
	// - 모든 경로에서 접근 가능하도록 설정
	// - 자동 로그인, 토큰 쿠키는 7일간 유지
	// - 아이디 저장, 로그인 아이디 쿠키는 세션 종료 시 삭제(기본값)
	if ( rememberMe != null ) {
		
		String token = userDAO.refreshToken(loginUser.getId());
		
		Cookie rememberMeCookie = new Cookie("rememberMe", URLEncoder.encode("Check", "UTF-8"));
		Cookie tokenCookie= new Cookie("token", URLEncoder.encode(token, "UTF-8"));
		
		rememberMeCookie.setPath("/");
		tokenCookie.setPath("/");
		
        int week = 7 * 24 * 60 * 60;
        rememberMeCookie.setMaxAge(week);
        tokenCookie.setMaxAge(week);
		
		response.addCookie(rememberMeCookie);
		response.addCookie(tokenCookie);
	}
	else {
		
		userDAO.deleteToken(loginUser.getId());
		
		Cookie rememberMeCookie = new Cookie("rememberMe", "");
		Cookie tokenCookie = new Cookie("token", "");
		
		rememberMeCookie.setPath("/");
		tokenCookie.setPath("/");
		
		rememberMeCookie.setMaxAge(0);
		tokenCookie.setMaxAge(0);
		
		response.addCookie(rememberMeCookie);
		response.addCookie(tokenCookie);
	}
	
	
	
	// TODO: 로그인 성공 페이지로 이동
	// - 메시지 코드 전달(0: 로그인 성공)
	response.sendRedirect("complete.jsp?msg=0");		

%>





