<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${menu}" var="item">
	<li class="nav-item"><a class="nav-link text-uppercase waves-effect waves-light menu-nav-blog <c:if test="${item.categoryCode eq category_code}">active</c:if> " data-code="${item.categoryCode}" href="${item.categoryHref}">${item.categoryText}</a></li>
</c:forEach>
