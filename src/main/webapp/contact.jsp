<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<html>
<head>
</head>
<body>
	<c:if test="${contact != null}">
		<form>
			<label for="firstname">Firstname:</label>
			<input id="firstname" name="firstname" value="<c:out value="${contact.firstname}" />">
			
			<label for="lasttname">Firstname:</label>
			<input name="lasttname" value="<c:out value="${contact.lastname}" />">
			
			<label for="middlename">Middlename:</label>
			<input name="middlename" value="<c:out value="${contact.middlename}" />">
			
			<label for="sex">Sex</label>
			<input type="radio" name="sex" value="Mail" <c:if test="${contact.sex == 'Male'}">checked</c:if>>
			<input type="radio" name="sex" value="Femail" <c:if test="${contact.sex eq 'Female'}">checked</c:if>>
			
			<label for="natinality">Nationality</label>
			<input name="nationality" value="<c:out value="${contact.nationality}"/>" />
			
			<label for="maritalstatus">Marital status</label>
			<input type="radio" name="maritalstatus" value="Mail" <c:if test="${contact.maritalStatus == 'Single'}">checked</c:if>>
			<input type="radio" name="maritalstatus" value="Femail" <c:if test="${contact.maritalStatus eq 'Married'}">checked</c:if>>
			
			<label for="website">Website:</label>
			<input name="website" value="<c:out value="${contact.website}"/>" />
			
			<label for="email">Email:</label>
			<input name="email" value="<c:out value="${contact.email}"/>" />
			
			<label for="workplace">Workplace:</label>
			<input name="workplace" value="<c:out value="${contact.workplace}"/>" />
		</form>
	</c:if>
</body>
</html>