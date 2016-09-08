<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet" />
	<link href="<c:url value="/resources/css/contact-edit.css" />"
	rel="stylesheet" />
</head>
<body>
<span class="icon">Ù</span>
<div class="container">
	<c:if test="${contact != null}">
		<form>
			<div class="form-group">
				<label for="firstname">First name:</label>
				<input type="text" name="firstname" id="firstname" class="form-control" value="<c:out value="${contact.firstname}" />">
			</div>
			<div class="form-group">
				<label for="lastname">Last name:</label>
				<input type="text" name="lastname" id="lastname" class="form-control" value="<c:out value="${contact.lastname}" />">
			</div>
			<div class="form-group">
				<label for="middlename">Middle name:</label>
				<input type="text" name="middlename" id="middlename" class="form-control" value="<c:out value="${contact.middlename}" />">
			</div>
			
			
			<div class="radio-group">	
				<label for="male" class="main">Sex</label>
					<div class="radio-wrap">
						<input type="radio" id="male" class="radio" name="sex" value="Mail" <c:if test="${contact.sex == 'Male'}">checked</c:if>>
						<label for="male" >
							<span class="icon">Ù</span> Male
						</label>
					</div>
			
					<div class="radio-wrap">
						<input type="radio" id="female" class="radio" name="sex" value="Mail" <c:if test="${contact.sex == 'Female'}">checked</c:if>>
						<label for="female">
							<span class="icon">Ú</span> Female
						</label>
					</div>
			</div>
			
			<div class="form-group">
				<label for="natinality">Nationality</label>
				<input type="text" name="nationality" id="nationality" class="form-control" value="<c:out value="${contact.nationality}"/>" />
			</div>
			
			<div class="radio-group">
				<label for="maritalstatus">Marital status</label>
			</div>
			<label for="maritalstatus">Marital status</label>
			<input type="radio" name="maritalstatus" value="Mail" <c:if test="${contact.maritalStatus == 'Single'}">checked</c:if>>
			<input type="radio" name="maritalstatus" value="Femail" <c:if test="${contact.maritalStatus eq 'Married'}">checked</c:if>>
			
			<div class="form-group">
				<label for="website">Website:</label>
				<input type="text" name="website" id="website" class="form-control" value="<c:out value="${contact.website}"/>" />
			</div>
			
			<div class="form-group">
				<label for="email">Email:</label>
				<input type="text" name="email" id="email" class="form-control" value="<c:out value="${contact.email}"/>" />
			</div>
			
			<div class="form-group">
				<label for="workplace">Workplace:</label>
				<input type="text" name="workplace" id="workplace" class="form-control" value="<c:out value="${contact.workplace}"/>" />
			</div>
		</form>
	</c:if>
</div>
</body>
</html>