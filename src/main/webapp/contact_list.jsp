<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<head>
	<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet" />
</head>
<body>
<div class="container">
<form action="" method="post">
	<a href="<c:url value="/contact/delete" />">Delete</a>
	<table class="table">
		<thead>
			<tr>
				<th></th>
				<th>Фамилия, имя</th>
				<th>День рождения</th>
				<th>Адрес</th>
				<th>Место работы</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="contact" items="${contactList}">
				<tr>
					<td><input type="checkbox" name="delete" value="${contact.id}"></td>
					<td><a href="<c:url value="/contact/${contact.id}"/>">
							${contact.firstname} ${contact.lastname}</a></td>
					<td>${contact.birthday}</td>
					<td>${contact.address.country}, ${contact.address.city}, ${contact.address.street}, ${contact.address.build}-${contact.address.flat}</td>
					<td>${contact.workplace}</td>
			</c:forEach>
		</tbody>
	</table>
</form>
<form action="" method="post">
	<div>
		
	</div>
</form>
</div>
</body>
</html>