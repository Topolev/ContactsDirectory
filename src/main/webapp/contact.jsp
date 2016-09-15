<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


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
	<form method="post"  action="/contactnew" enctype="multipart/form-data">
		<input type="hidden" name="id" value="<c:out value="${contact.id}"/>">
		<section>
			<h3>Contact Details</h3>

			<div class="row">
				<div class="col-md-3 col-md-push-9 col-sm-4 col-sm-push-8" id="wrap-photo">
					<div id="wrap-profile">

						<c:if test="${contact.photo == null}">
							<img src = "${root_for_img}/no-profile-photo.png" alt="Profile photo" id="profile-photo"/>
						</c:if>
						<c:if test="${contact.photo != null}">
							<img src = "${root_directory}showimage?file=${contact.photo}" alt="Profile photo" id="profile-photo"/>
							<input type="hidden" name="photo" value="${contact.photo}">
						</c:if>


						<div id="choose-photo">
							<input type="file" name="uploadphoto" />
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
								<input type="date" class="form-control" id="birthday" placeholder="Choose birthday" name="birthday" value="<c:out value="${contact.birthday}"/>">
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

								<input type="radio" name="maritalStatus" class="radio" id="married" value="Married" <c:if test="${contact.maritalStatus eq 'Married'}">checked</c:if>/>
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
								<input type="text" class="form-control" id="website" name="website" placeholder="Enter website" value="${contact.website}">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="email">Email</label>
								<input type="text" class="form-control" id="email" name="email" placeholder="Enter your email" value="${contact.email}">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="workplace">Current work place</label>
								<input type="text" class="form-control" id="workplace" name="workplace" placeholder="Enter your current work place" value="${contact.workplace}">
							</div>
						</div>
					</div>
					</div>

				</div>

			</div><!-- end the 1st main row--->





			<div class="row">
				<div class="big-block">
					<h2>Address</h2>
					<input type="hidden" name="address.id" value="<c:out value="${contact.address.id}"/>">
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label for="index">Index</label>
								<input type="text" class="form-control" id="index" placeholder="Index" name="address.ind" value="${contact.address.ind}">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="country">Country</label>
								<input type="text" class="form-control" id="country" placeholder="Country" name="address.country" value="${contact.address.country}">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="city">City</label>
								<input type="text" class="form-control" id="city" placeholder="City" name="address.city" value="${contact.address.city}">
							</div>
						</div>
					</div>


					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label for="street">Street</label>
								<input type="text" class="form-control" id="street" placeholder="Street" name="address.street" value="${contact.address.street}">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="build">Build</label>
								<input type="text" class="form-control" id="build" placeholder="Build" name="address.build" value="${contact.address.build}">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="flat">Flat</label>
								<input type="text" class="form-control" id="flat" placeholder="Flat" name="address.flat" value="${contact.address.flat}">
							</div>
						</div>
					</div>
					</div>
			</div>


			<!--List of phones-->
			<div class="row">
				<div class="big-block" id="phone">
					<input type="hidden" id="phone-indexes" name="phone.indexes" value="">
					<input type="hidden" id="phone-delete" name="phone.delete" value="">
					<h2>Phones
						<div class="control-panel">
							<a href="" class="btn btn-default create-new-row" >Create</a>
							<a href="" class="btn btn-default edit-row">Edit</a>
							<a href="" class="btn btn-default delete-rows">Delete</a>
						</div>
					</h2>
					<table class="table">
						<thead>
							<tr>
								<th>
									<div class="wrap-checkbox">
										<input type="checkbox" class="checkbox delete-all">
										<label></label>
									</div>
								</th>
								<th>Phone</th>
								<th>Type</th>
								<th>Description</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach items="${contact.phoneList}" var="item" varStatus="status">
							<tr>
								<td>
									<div class="wrap-checkbox">
										<input type="checkbox" class="checkbox" onchange="tablePhone.changeCheckRow(event)">
										<label></label>
									</div>
								</td>
								<td>+${item.countryCode}-${item.operatorCode}-${item.phoneNumber}</td>
								<td>${item.typePhone}</td>
								<td>${item.description}</td>
								<input type="hidden" name="phone${status.count}.id" value="${item.id}">
								<input type="hidden" name="phone${status.count}.inc" value="${status.count}">
								<input type="hidden" name="phone${status.count}.countryCode" value="${item.countryCode}">
								<input type="hidden" name="phone${status.count}.operatorCode" value="${item.operatorCode}">
								<input type="hidden" name="phone${status.count}.phoneNumber" value="${item.phoneNumber}">
								<input type="hidden" name="phone${status.count}.typePhone" value="${item.typePhone}">
								<input type="hidden" name="phone${status.count}.description" value="${item.description}">
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div><!--end row-->
			<!--End list of phones-->

			<!--List of attachments-->
			<div class="row">
				<div class="big-block" id="attachment">
					<input type="hidden" id="attachment-indexes" name="phone.indexes" value="">
					<input type="hidden" id="attachment-delete" name="phone.delete" value="">
					<h2>Attachments
						<div class="control-panel">
							<a href="" class="btn btn-default create-new-row" >Create</a>
							<a href="" class="btn btn-default edit-row">Edit</a>
							<a href="" class="btn btn-default delete-rows">Delete</a>
						</div>
					</h2>
					<table class="table">
						<thead>
							<tr>
								<th>
									<div class="wrap-checkbox">
										<input type="checkbox" class="checkbox delete-all">
										<label></label>
									</div>
								</th>
								<th>File name</th>
								<th>Upload date</th>
								<th>Comment</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									<div class="wrap-checkbox">
										<input type="checkbox" class="checkbox checkbox-phone" onchange="change_check_phone(event)">
										<label></label>
									</div>
								</td>
								<td>FileName.doc</td>
								<td>12-12-2009</td>
								<td>Comment</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<!--End list of attachments-->

			<input type="submit" class="btn btn-default" value="Save contact"/>
		</section>
	</form>
</div>


<!--Popup for creating new phone-->
<div class="popup-overlay" id="phone-modal">
	<div class = "modal-window">
		<h5>Create/edit phone</h5>
		<div class="container-fluid">
			<div class="form-group">
				<label for="country-code">Country code</label>
				<input type="text" class="form-control" id="country-code" name="countryCode" placeholder="Country code">
			</div>
			<div class="form-group">
				<label for="operator-code">Operator code</label>
				<input type="text" class="form-control" id="operator-code" name="operatorCode" placeholder="Operator code">
			</div>
			<div class="form-group">
				<label for="phone-number">Number</label>
				<input type="text" class="form-control" id="phone-number" name="phoneNumber" placeholder="Phone number">
			</div>
			<div class="form-group">
				<label for="home">Type phone</label>

				<div class="wrap-phone">
				<input type="radio" name="typePhone" value="Home" class="radio phone-radio" id="home"/>
				<label for="home" class="phone"><i class="fa fa-phone" aria-hidden="true"></i><span>Home</span></label>

				<input type="radio" name="typePhone" value="Mobile" class="radio phone-radio" id="mobile"/>
				<label for="mobile" class="phone"><i class="fa fa-mobile" aria-hidden="true"></i>Mobile</label>
				</div>

			</div>
			<div class="form-group">
				<label for="description">Description</label>
				<input type="text" class="form-control" id="description" placeholder="Enter email">
			</div>
		</div>
		<div class="modal-buttons">
			<a href="#" class="btn btn-default create-edit">Save</a>
			<a href="#" class="btn btn-default close-modal">Cancel</a>
		</div>
	</div>
</div>
<!--END Popup for creating new phone-->

<!--Popup for creating new attachment-->
<div class="popup-overlay" id="attachment-modal">
	<div class = "modal-window">
		<h5>Create/edit attachtment</h5>
		<div class="container-fluid">
			<div class="form-group">
				<label for="name-file">Name file</label>
				<input type="text" class="form-control" id="name-file" name="nameFile" placeholder="Name file">
			</div>
			<div class="form-group">
				<label for="comment-file">Comment</label>
				<input type="text" class="form-control" id="comment-file" name="commentFile" placeholder="Comment">
			</div>
		</div>
		<div class="modal-buttons">
			<a href="#" class="btn btn-default create-edit">Save</a>
			<a href="#" class="btn btn-default close-modal">Cancel</a>
		</div>
	</div>
</div>
<!--END Popup for creating new phone-->

</div>
<script>
	var initAutoincrement = ${fn:length(contact.phoneList)};
	var initAvailableIndexes = [];
	for (var i = 1; i<= initAutoincrement; i++){
		initAvailableIndexes.push(i);
	}

</script>


<script src="${root_for_js}/contact.js"></script>

</body>
</html>

