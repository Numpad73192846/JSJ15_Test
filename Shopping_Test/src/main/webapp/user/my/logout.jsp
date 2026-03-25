<%@page import="shop.dto.User"%>
<%@page import="shop.dao.UserRepository"%>
<%@ include file="/layout/jstl.jsp" %>
<%@ include file="/layout/common.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	

	UserRepository userDAO = new  UserRepository();

	loginId = (loginId != null) ? loginId : "";

	// TODO: 아이디 저장, 자동 로그인 쿠키 삭제
	if ( loginId.isEmpty() ) {
		userDAO.deleteToken(loginId);
	}
	
    Cookie rememberIdCookie = new Cookie("rememberId", "");
    Cookie rememberMeCookie = new Cookie("rememberMe", "");
    Cookie tokenCookie      = new Cookie("token", "");

    rememberIdCookie.setPath("/");
    rememberMeCookie.setPath("/");
    tokenCookie.setPath("/");

    rememberIdCookie.setMaxAge(0);
    rememberMeCookie.setMaxAge(0);
    tokenCookie.setMaxAge(0);
	
	// TODO: 세션 무효화
	session.invalidate();
	
	// TODO: 쿠키 전달
	response.addCookie(rememberIdCookie);
	response.addCookie(rememberMeCookie);
	response.addCookie(tokenCookie);
	
	response.sendRedirect(root + "/");
	
%>