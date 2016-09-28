<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<c:url value="/resources/img" var="root_for_img"/>
<c:url value="/" var="root" />

<header class="user-container">
	<nav class="navbar navbar-default" role="navigation">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#main-navbar" id="header-button">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" id="logo" href="${root}">
				<img src="${root_for_img}/logo.png">
			</a>
		</div><!--end .navbar-header-->
		
		<div class="collapse navbar-collapse" id="main-navbar">
			<ul class="nav navbar-nav">
				<li>
					<a href="${root}contactlist">${resourceBundle.getString("contactlist")}</a>
				</li>
				<li>
					<a href="${root}showsearchform">${resourceBundle.getString("search")}</a>
				</li>
			</ul>

			<c:if test="${param.lan == null}">
				<c:set var="language" value="${cookie.lan.value}"/>
			</c:if>
			<c:if test="${param.lan != null}">
				<c:set var="language" value="${param.lan}"/>
			</c:if>

			<div class="navbar-right">
				<div id="wrap-lan">
					<div id="lan" class="lan-click">
						<div id="choose-lang">

							<c:if test="${(language == null) or (language == 'ru')}">
								<img src="${root_for_img}/flag-rus.png"/><span>RU</span>
							</c:if>
							<c:if test="${language == 'en'}">
								<img src="${root_for_img}/flag-eng.png"/><span>EN</span>
							</c:if>
						</div>
						<div id="list-lang">
							<a href="${root}changelan?lan=en">
								<img src="${root_for_img}/flag-eng.png"/><span>EN</span>
							</a>
							<a href="${root}changelan?lan=ru">
								<img src="${root_for_img}/flag-rus.png"/><span>RU</span>
							</a>
						</div>
						<i class="fa fa-sort-desc" aria-hidden="true"></i>
					</div>
					
				</div>
			</div>
		</div><!--end #main-navbar-->	
	</nav>
</header>