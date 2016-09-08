<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>

<c:url value="/resources/img" var="root_for_img"/>
<c:url value="/" var="root" />

<header class="user-container">
	<nav class="navbar navbar-default" role="navigation">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#main-navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" id="logo" href="#">
				<img src="${root_for_img}/logo.png">
			</a>
		</div><!--end .navbar-header-->
		
		<div class="collapse navbar-collapse" id="main-navbar">
			<ul class="nav navbar-nav">
				<li>
					<a href="${root}contactlist">Contact list</a>
				</li>
				<li>
					<a href="">Search</a>
				</li>
				<li>
					<a href="">Send emails</a>
				</li>
			</ul>
			<div class="navbar-right">
				<div id="wrap-lan">
					<div id="lan" class="lan-click">
						<div id="choose-lang">
							<img src="${root_for_img}/flag-eng.png"/><span>EN</span>
						</div>
						<div id="list-lang">
							<a href="">
								<img src="${root_for_img}/flag-eng.png"/><span>EN</span>
							</a>
							<a href="">
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