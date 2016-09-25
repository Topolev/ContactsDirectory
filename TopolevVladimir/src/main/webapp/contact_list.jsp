<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
 

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap Template</title>

<c:url value="/resources/css" var="root_for_css"/>
<c:url value="resources/js" var = "root_for_js"/>
<c:url value="/" var="root_directory"/>

<link href="${root_for_css}/style-common.css" rel="stylesheet" />
<link href="${root_for_css}/style-contact-list.css" rel="stylesheet" />
<link href="${root_for_css}/font-awesome/css/font-awesome.min.css" rel="stylesheet" />





</head>
<body>

<%@ include file="header.jsp"%>

<div id="wrap-content" class="user-container">
	<section>
		<h3>Contact list</h3>
		<div class="control-panel">
			<a href="${root_directory}contactnew" class="btn btn-default">Create new contact</a>
			<a href="" class="btn btn-default" id="show-delete-popup">Delete selected contacts</a>
			<a href="" class="btn btn-default" id="send-messages">Send email to selected contacts</a>
			<div class="select-show">
				<span>Show:<span>
				<select name="countRow" id="count-row">
					<c:forTokens var="show_page" items="10,20" delims=",">
						<option value="${show_page}" <c:if test="${show_page eq countRow}">selected</c:if> >${show_page}</option>
					</c:forTokens>
				</select>
			</div>
		</div>

		<%@ include file="contact_table.jsp"%>

	</section>
</div>



<script src="${root_for_js}/main.js"></script>
<script src="${root_for_js}/table.js"></script>

</body>
</html>

	
