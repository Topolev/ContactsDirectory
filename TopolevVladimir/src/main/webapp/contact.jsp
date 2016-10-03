<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cf" uri="/WEB-INF/functions.tld"%>


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


</head>
<body>


<jsp:include page="header.jsp"/>

<div id="wrap-content" class="user-container">
	<form method="post"  action="/contactsave" enctype="multipart/form-data">
		<input type="hidden" name="page" value="${page}">
		<input type="hidden" name="countPage" value="${countPage}">
		<input type="hidden" name="id" value="<c:out value="${contact.id}"/>">
		<section>
			<h3>${resourceBundle.getString("contactdetails")}</h3>

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
							<button class="btn btn-default">${resourceBundle.getString("choosenewimage")}</button>
						</div>
					</div>
				</div>

				<div class="col-md-9 col-md-pull-3 col-sm-8 col-sm-pull-4">
					<div class="block-input">
					<div class="row">
						<div class="col-md-4">
							<div class="form-group ${cf:hasError(error.errors, 'firstname') ? 'has-error': ''}">
								<label for="firstname" class="control-label">${resourceBundle.getString("firstname")}</label>
								<input type="text"
									   class="form-control"
									   id="firstname"
									   placeholder="Enter your first name"
									   name="firstname"
									   value="<c:out value="${contact.firstname}"/>"
									   onchange = "validate.validateField(event,['isNotEmpty', 'isMaxLength'],'submit')"
									   onblur = "validate.validateField(event,['isNotEmpty', 'isMaxLength'],'submit')">
								<div class="warn-message">${cf:getMessage(error.errors,'firstname')}</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group ${cf:hasError(error.errors, 'lastname') ? 'has-error': ''}">
								<label for="lastname" class="control-label">${resourceBundle.getString("lastname")}</label>
								<input type="text"
									   class="form-control"
									   id="lastname"
									   placeholder="Enter last name"
									   name="lastname"
									   value="<c:out value="${contact.lastname}"/>"
									   onchange = "validate.validateField(event,['isNotEmpty', 'isMaxLength'],'submit')"
									   onblur = "validate.validateField(event,['isNotEmpty', 'isMaxLength'],'submit')" >
								<div class="warn-message">${cf:getMessage(error.errors,'lastname')}</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="middlename">${resourceBundle.getString("middlename")}</label>
								<input type="text" class="form-control" id="middlename" placeholder="Enter middle name" name="middlename" value="<c:out value="${contact.middlename}"/>" >
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col-md-4">
							<div class="form-group  ${cf:hasError(error.errors, 'birthday') ? 'has-error': ''}">
								<label for="birthday" class="control-label">${resourceBundle.getString("birthday")}</label>
								<input type="date" class="form-control"
									   id="birthday" placeholder="Choose birthday"
									   name="birthday"
									   value="<c:out value="${contact.birthday}"/>"
									   onchange = "validate.validateField(event,['isDate'],'submit')"
									   onblur = "validate.validateField(event,['isDate'],'submit')">
								<div class="warn-message">${cf:getMessage(error.errors,'birthday')}</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="nationality">${resourceBundle.getString("nationality")}</label>
								<input type="text" class="form-control" id="nationality" placeholder="Enter nationality" name="nationality" value="<c:out value="${contact.nationality}"/>">
							</div>
						</div>
					</div>
					</div>

					<div class="block-input">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label for="male" class="block">${resourceBundle.getString("sex")}:</label>

								<input type="radio" name="sex" class="radio" id="male" value="Male" <c:if test="${(contact.sex ==null) or (contact.sex eq 'Male')}">checked</c:if> />
								<label for="male" class="sex"><span class="icon">Ù</span>${resourceBundle.getString("male")}</label>

								<input type="radio" name="sex" class="radio" id="female" value="Female" <c:if test="${contact.sex eq 'Female'}">checked</c:if> />
								<label for="female" class="sex"><span class="icon">Ú</span>${resourceBundle.getString("female")}</label>
								<div class="clear"></div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="single" class="block">${resourceBundle.getString("maritalstatus")}:</label>

								<input type="radio" name="maritalStatus" class="radio" id="single" value="Single" <c:if test="${(contact.maritalStatus == null) or (contact.maritalStatus eq 'Single')}">checked</c:if> />
								<label for="single" class="sex"><span class="icon">Ù</span>${resourceBundle.getString("single")}</label>

								<input type="radio" name="maritalStatus" class="radio" id="married" value="Married" <c:if test="${contact.maritalStatus eq 'Married'}">checked</c:if>/>
								<label for="married" class="sex"><span class="icon">ÙÙ</span>${resourceBundle.getString("married")}</label>
								<div class="clear"></div>
							</div>
						</div>
					</div>
					</div>

					<div class="block-input">
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label for="website">${resourceBundle.getString("website")}</label>
								<input type="text" class="form-control" id="website" name="website" placeholder="Enter website" value="${contact.website}">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group ${cf:hasError(error.errors, 'email') ? 'has-error': ''}">
								<label for="email" class="control-label">${resourceBundle.getString("email")}</label>
								<input type="text"
									   class="form-control"
									   id="email" name="email"
									   placeholder="Enter your email"
									   value="${contact.email}"
									   onchange = "validate.validateField(event,['isNotEmpty','isEmail'],'submit')"
									   onblur = "validate.validateField(event,['isNotEmpty','isEmail'],'submit')" >
								<div class="warn-message">${cf:getMessage(error.errors,'email')}</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="workplace">${resourceBundle.getString("workplace")}</label>
								<input type="text" class="form-control" id="workplace" name="workplace" placeholder="Enter your current work place" value="${contact.workplace}">
							</div>
						</div>
					</div>
					</div>

				</div>

			</div><!-- end the 1st main row--->





			<div class="row">
				<div class="big-block">
					<h2>${resourceBundle.getString("address")}</h2>
					<input type="hidden" name="address.id" value="<c:out value="${contact.address.id}"/>">
					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label for="index" class="control-label">${resourceBundle.getString("ind")}</label>
								<input type="text"
									   class="form-control"
									   id="index" placeholder="Index"
									   name="address.ind" value="${contact.address.ind}"
									   onchange = "validate.validateField(event,['isNumber'],'submit')"
									   onblur = "validate.validateField(event,['isNumber'],'submit')">
								<div class="warn-message"></div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="country">${resourceBundle.getString("country")}</label>
								<input type="text" class="form-control" id="country" placeholder="Country" name="address.country" value="${contact.address.country}">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<label for="city">${resourceBundle.getString("city")}</label>
								<input type="text" class="form-control" id="city" placeholder="City" name="address.city" value="${contact.address.city}">
							</div>
						</div>
					</div>


					<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<label for="street">${resourceBundle.getString("street")}</label>
								<input type="text" class="form-control" id="street" placeholder="Street" name="address.street" value="${contact.address.street}">
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group ${cf:hasError(error.errors, 'build') ? 'has-error': ''}">
								<label for="build" class="control-label">${resourceBundle.getString("build")}</label>
								<input type="text"
									   class="form-control"
									   id="build" placeholder="Build"
									   name="address.build" value="${contact.address.build}"
									   onchange = "validate.validateField(event,['isNumber'],'submit')"
									   onblur = "validate.validateField(event,['isNumber'],'submit')">
								<div class="warn-message">${cf:getMessage(error.errors,'build')}</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group ${cf:hasError(error.errors, 'flat') ? 'has-error': ''}">
								<label for="flat" class="control-label">${resourceBundle.getString("flat")}</label>
								<input type="text"
									   class="form-control"
									   id="flat"
									   placeholder="Flat"
									   name="address.flat" value="${contact.address.flat}"
									   onchange = "validate.validateField(event,['isNumber'],'submit')"
									   onblur = "validate.validateField(event,['isNumber'],'submit')">
								<div class="warn-message">${cf:getMessage(error.errors,'flat')}</div>
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
					<h2>${resourceBundle.getString("phones")}
						<div class="control-panel">
							<a href="" class="btn btn-default create-new-row" >${resourceBundle.getString("create")}</a>
							<a href="" class="btn btn-default edit-row">${resourceBundle.getString("edit")}</a>
							<a href="" class="btn btn-default delete-rows">${resourceBundle.getString("delete")}</a>
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
								<th>${resourceBundle.getString("phone")}</th>
								<th>${resourceBundle.getString("type")}</th>
								<th>${resourceBundle.getString("description")}</th>
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

				<div id="container-file" style="display: none">
					<!--<input type="file" name="file0" onchange = "tableAttachment.changeFile(event)">-->
					<c:forEach items="${contact.attachmentList}" var="item" varStatus="status">
						<input type="file" name="file${status.count}" onchange = "tableAttachment.changeFile(event)">
					</c:forEach>
				</div>

				<div class="big-block" id="attachment">
					<input type="hidden" id="attachment-indexes" name="attachment.indexes" value="">
					<input type="hidden" id="attachment-delete" name="attachment.delete" value="">
					<h2>${resourceBundle.getString("attachments")}
						<div class="control-panel">
							<a href="" class="btn btn-default create-new-row" >${resourceBundle.getString("create")}</a>
							<a href="" class="btn btn-default edit-row">${resourceBundle.getString("edit")}</a>
							<a href="" class="btn btn-default delete-rows">${resourceBundle.getString("delete")}</a>
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
								<th>${resourceBundle.getString("filename")}</th>
								<th>${resourceBundle.getString("uploaddate")}</th>
								<th>${resourceBundle.getString("comment")}</th>
							</tr>
						</thead>
						<tbody>


                        <c:forEach items="${contact.attachmentList}" var="item" varStatus="status">
                            <tr>
                                <td>
                                    <div class="wrap-checkbox">
                                        <input type="checkbox" class="checkbox checkbox-phone" onchange="tableAttachment.changeCheckRow(event)">
                                        <label></label>
                                    </div>
                                </td>
                                <td><a href="${root_directory}uploadfile?file=${item.nameFileInSystem}">${item.nameFile}</a></td>
                                <td>${item.dateFile}</td>
                                <td>${item.commentFile}</td>
                                <input type="hidden" name="attachment${status.count}.id" value="${item.id}">
                                <input type="hidden" name="attachment${status.count}.inc" value="${status.count}">
                                <input type="hidden" name="attachment${status.count}.nameFile" value="${item.nameFile}">
                                <input type="hidden" name="attachment${status.count}.commentFile" value="${item.commentFile}">
                                <input type="hidden" name="attachment${status.count}.nameFileInSystem" value="${item.nameFileInSystem}">
                                <input type="hidden" name="attachment${status.count}.dateFile" value="${item.dateFile}">
							</tr>
                        </c:forEach>

						</tbody>
					</table>
				</div>
			</div>
			<!--End list of attachments-->

			<input type="submit" id="submit" class="btn btn-default" value="${resourceBundle.getString("savecontact")}"/>
		</section>
	</form>
</div>


<!--Popup for creating new phone-->
<div class="popup-overlay" id="phone-modal">
	<div class = "modal-window">
		<h5>${resourceBundle.getString("createeditphone")}</h5>
		<div class="container-fluid">
			<div class="form-group">
				<label for="country-code" class="control-label">${resourceBundle.getString("countrycode")}</label>
				<input type="text"
					   class="form-control"
					   id="country-code"
					   name="countryCode"
					   placeholder="Country code"
					   onchange = "validate.validateField(event,['isNotEmpty','isNumber'],'save-phone')"
					   onblur = "validate.validateField(event,['isNotEmpty','isNumber'],'save-phone')">
				<div class="warn-message"></div>
			</div>
			<div class="form-group">
				<label for="operator-code" class="control-label">${resourceBundle.getString("operatorcode")}</label>
				<input type="text"
					   class="form-control"
					   id="operator-code"
					   name="operatorCode"
					   placeholder="Operator code"
					   onchange = "validate.validateField(event,['isNotEmpty','isNumber'],'save-phone')"
					   onblur = "validate.validateField(event,['isNotEmpty','isNumber'],'save-phone')">
				<div class="warn-message"></div>
			</div>
			<div class="form-group">
				<label for="phone-number" class="control-label">${resourceBundle.getString("number")}</label>
				<input type="text"
					   class="form-control"
					   id="phone-number"
					   name="phoneNumber"
					   placeholder="Phone number"
					   onchange = "validate.validateField(event,['isNotEmpty','isNumber'],'save-phone')"
					   onblur = "validate.validateField(event,['isNotEmpty','isNumber'],'save-phone')">
				<div class="warn-message"></div>
			</div>
			<div class="form-group">
				<label for="home">${resourceBundle.getString("typephone")}</label>

				<div class="wrap-phone">
				<input type="radio" name="typePhone" value="Home" class="radio phone-radio" id="home"/>
				<label for="home" class="phone"><i class="fa fa-phone" aria-hidden="true"></i><span>${resourceBundle.getString("home")}</span></label>

				<input type="radio" name="typePhone" value="Mobile" class="radio phone-radio" id="mobile"/>
				<label for="mobile" class="phone"><i class="fa fa-mobile" aria-hidden="true"></i>${resourceBundle.getString("mobile")}</label>
				</div>

			</div>
			<div class="form-group">
				<label for="description">${resourceBundle.getString("description")}</label>
				<input type="text" class="form-control" id="description" placeholder="Enter email">
			</div>
		</div>
		<div class="modal-buttons">
			<a href="#" class="btn btn-default create-edit" id="save-phone">${resourceBundle.getString("save")}</a>
			<a href="#" class="btn btn-default close-modal">${resourceBundle.getString("cancel")}</a>
		</div>
	</div>
</div>
<!--END Popup for creating new phone-->


<!--Popup for creating new attachment-->
<div class="popup-overlay" id="attachment-modal">
	<div class = "modal-window">
		<h5>${resourceBundle.getString("createeditattachment")}</h5>
		<div class="container-fluid">
			<div class="form-group">
				<label for="name-file" class="control-label">${resourceBundle.getString("filename")}</label>
				<input type="text"
					   class="form-control"
					   id="name-file"
					   name="nameFile"
					   placeholder="Name file"
					   onchange = "validate.validateField(event,['isNotEmpty'],'save-attachment')"
					   onblur = "validate.validateField(event,['isNotEmpty'],'save-attachment')">
				<div class="warn-message"></div>
			</div>
			<div class="form-group">
				<label for="comment-file">${resourceBundle.getString("comment")}</label>
				<input type="text" class="form-control" id="comment-file" name="commentFile" placeholder="Comment">
			</div>
			<div class="form-group">
				<label class="block">${resourceBundle.getString("choosenfile")}: </label>
				<span id="choosen-file" class="block" onchange="validate.validateField(event,['isChoosenFile'],'save-attachment')"></span>
				<div class="warn-message"></div>
				<button class="btn btn-default" id="upload-file" onclick="tableAttachment.chooseFile()">${resourceBundle.getString("choosenewfile")}</button>

			</div>
		</div>
		<div class="modal-buttons">
			<a href="#" class="btn btn-default create-edit" id="save-attachment">${resourceBundle.getString("save")}</a>
			<a href="#" class="btn btn-default close-modal">${resourceBundle.getString("cancel")}</a>
		</div>
	</div>
</div>
<!--END Popup for creating new phone-->

</div>
<script>
	var initAutoincrementPhoto = ${fn:length(contact.phoneList)};
	var initAvailableIndexesPhoto = [];
	for (var i = 1; i<= initAutoincrementPhoto; i++){
		initAvailableIndexesPhoto.push(i);
	}


	var initAutoincrementAttachment = ${fn:length(contact.attachmentList)}
	var initAvailableIndexesAttachment = [];
	for (var i = 1; i<= initAutoincrementAttachment; i++){
		initAvailableIndexesAttachment.push(i);
	}

</script>

<script src="${root_for_js}/validate.js"></script>
<script src="${root_for_js}/contact.js"></script>
<script src="${root_for_js}/main.js"></script>

<script>





	var validate = new Validate();

	var mapValidateMainForm = [
		{id: "firstname", validators : ['isNotEmpty', 'isMaxLength']},
		{id: "lastname", validators: ['isNotEmpty', 'isMaxLength']},
		{id: "birthday", validators: ['isDate']},
		{id: "email", validators: ['isNotEmpty', 'isEmail']},
		{id: "index", validators: ['isNumber']},
		{id: "build", validators: ['isNumber']},
		{id: "flat", validators: ['isNumber']},
	]


	document.getElementById("submit").onclick = function(){
		return validate.validateFieldList(mapValidateMainForm);
	}






	/*document.getElementById("save-phone").onclick = function(){
		return validate.validateFieldList(mapValidatePhoneForm);
	}*/





</script>

</body>
</html>

