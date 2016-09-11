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
<c:url value="/resources/img" var="root_for_img"/>
<c:url value="resources/js" var = "root_for_js"/>
<c:url value="/" var="root_directory"/>


<link href="${root_for_css}/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
<link href="${root_for_css}/font-raphaelicons/raphaelicons.css" rel="stylesheet">

<!-- Bootstrap -->
<link href="${root_for_css}/style-common.css" rel="stylesheet">
<link href="${root_for_css}/style-contact.css" rel="stylesheet">

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>-->
<script src="${root_for_js}/jquery-1.12.3.min.js"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${root_for_js}/bootstrap.js"></script>
</head>
<body>
	
	
<jsp:include page="header.jsp"/>

<div id="wrap-content" class="user-container">
	<form method="post"  enctype="multipart/form-data">
	<section>
		<h3>Contact Details</h3>
		
		<div class="row">
			<div class="col-md-3 col-md-push-9 col-sm-4 col-sm-push-8" id="wrap-photo">
				<div id="wrap-profile">
					
					<c:if test="${contact.photo == null}">
						<img src = "${root_for_img}/no-profile-photo.png" alt="Profile photo" id="profile-photo"/>
					</c:if>
					<c:if test="${contact.photo != null}">
						PHOTO
					</c:if>
					
					
					<div id="choose-photo">	
						<input type="file" />
						<button class="btn btn-default">Choose new image</button>
					</div>
				</div>
			</div>
			
			<div class="col-md-9 col-md-pull-3 col-sm-8 col-sm-pull-4">
				<div class="block-input">
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label for="firstname">First name</label>
							<input type="text" class="form-control" id="firstname" placeholder="Enter your first name" name="firstname" value="<c:out value="${contact.firstname}" />">
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label for="lastname">Last name</label>
							<input type="text" class="form-control" id="lastname" placeholder="Enter last name" name="lastname" value="<c:out value="${contact.lastname}"/>" >
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label for="middlename">Middle name</label>
							<input type="text" class="form-control" id="middlename" placeholder="Enter middle name" name="middlename" value="<c:out value="${contact.middlename}"/>" >
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label for="birthday">Birthday</label>
							<input type="text" class="form-control" id="birthday" placeholder="Choose birthday" name="birthday" value="<c:out value="${contact.birthday}"/>">
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label for="nationality">Nationality</label>
							<input type="text" class="form-control" id="nationality" placeholder="Enter nationality" name="nationality" value="<c:out value="${contact.nationality}"/>">
						</div>
					</div>
				</div>
				</div>
				
				<div class="block-input">
				<div class="row">
					<div class="col-md-6">
						<div class="form-group">
							<label for="male" class="block">Sex:</label>
							
							<input type="radio" name="sex" class="radio" id="male" value="Male" <c:if test="${contact.sex eq 'Male'}">checked</c:if> />
							<label for="male" class="sex"><span class="icon">Ù</span>Male</label>
							
							<input type="radio" name="sex" class="radio" id="female" value="Female" <c:if test="${contact.sex eq 'Female'}">checked</c:if> />
							<label for="female" class="sex"><span class="icon">Ú</span>Female</label>
							<div class="clear"></div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label for="single" class="block">Marital status:</label>
							
							<input type="radio" name="maritalStatus" class="radio" id="single" value="Single" <c:if test="${contact.maritalStatus eq 'Single'}">checked</c:if> />
							<label for="single" class="sex"><span class="icon">Ù</span>Single</label>
							
							<input type="radio" name="maritalstatus" class="radio" id="married" value="Married" <c:if test="${contact.maritalStatus eq 'Married'}">checked</c:if>/>
							<label for="married" class="sex"><span class="icon">ÙÙ</span>Married</label>
							<div class="clear"></div>
						</div>
					</div>
				</div>
				</div>
					
				<div class="block-input">	
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label for="website">Website</label>
							<input type="text" class="form-control" id="website" placeholder="Enter website" value="${contact.website}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label for="email">Email</label>
							<input type="text" class="form-control" id="email" placeholder="Enter your email" value="${contact.email}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label for="workplace">Current work place</label>
							<input type="text" class="form-control" id="workplace" placeholder="Enter your current work place" value="${contact.workplace}">
						</div>
					</div>
				</div>
				</div>
			
			</div>
			
		</div><!-- end the 1st main row--->
		
		
		
		
		
		<div class="row">
			<div class="big-block">
				<h2>Address</h2>
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label for="index">Index</label>
							<input type="text" class="form-control" id="index" placeholder="Index" value="${contact.address.index}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label for="country">Country</label>
							<input type="text" class="form-control" id="country" placeholder="Country" value="${contact.address.country}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label for="city">City</label>
							<input type="text" class="form-control" id="city" placeholder="City" value="${contact.address.city}">
						</div>
					</div>
				</div>
				
				
				<div class="row">
					<div class="col-md-4">
						<div class="form-group">
							<label for="street">Street</label>
							<input type="text" class="form-control" id="street" placeholder="Street" value="${contact.address.street}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label for="build">Build</label>
							<input type="text" class="form-control" id="build" placeholder="Build" value="${contact.address.build}">
						</div>
					</div>
					<div class="col-md-4">
						<div class="form-group">
							<label for="flat">Flat</label>
							<input type="text" class="form-control" id="flat" placeholder="Flat" value="${contact.address.flat}">
						</div>
					</div>
				</div>
				</div>
		</div>
		<input type="submit" class="btn btn-default" />
	</section>
	</form>
</div>


</body>
</html>
<script>
	
	
	
</script>
















