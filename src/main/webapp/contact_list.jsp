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


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>-->
<script src="${root_for_js}/jquery-1.12.3.min.js"></script>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${root_for_js}/bootstrap.js"></script>
</head>
<body>

<jsp:include page="header.jsp"/>

<div id="wrap-content" class="user-container">
	<section>
		<h3>Contact list</h3>
		<div class="control-panel">
			<a href="${root_directory}contactnew" class="btn btn-default">Create new contact</a>
			<a href="" class="btn btn-default" id="show-delete-popup">Delete selected contacts</a>
			<div class="select-show">
				<span>Show:<span>
				
				
				
				<select name="countRow" id="count-row">
					<c:forTokens var="show_page" items="10,20" delims=",">
						<option value="${show_page}" <c:if test="${show_page eq countRow}">selected</c:if> >${show_page}</option>
					</c:forTokens>
				</select>
			</div>
		</div>
	
		<table class="table">
			<thead>
				<tr>					
					<th>
						<div class="wrap-checkbox">
							<input type="checkbox" id="delete-all">
							<label></label>
						<div>
					</th>
					
					<th>First name, last name
						
						
						<a href="${root_directory}contactlist?countRow=${countRow}&sortField=0&sortType=${sortFields.get(0).sortType}">
							<c:if test="${!sortFields.get(0).choosenField}">
								<i class="fa fa-sort" aria-hidden="true"></i>
							</c:if>
							<c:if test="${sortFields.get(0).choosenField}">
								<c:choose>
									<c:when test="${sortFields.get(0).sortType eq 'ASC'}"><i class="fa fa-sort-asc" aria-hidden="true"></i></c:when>
									<c:when test="${sortFields.get(0).sortType eq 'DESC'}"><i class="fa fa-sort-desc" aria-hidden="true"></i></c:when>
								</c:choose>
							</c:if>
						</a>
						
					</th>
					
					<th>Birthday
						<a href="${root_directory}contactlist?countRow=${countRow}&sortField=1&sortType=${sortFields.get(1).sortType}">
							<c:if test="${!sortFields.get(1).choosenField}">
								<i class="fa fa-sort" aria-hidden="true"></i>
							</c:if>
							<c:if test="${sortFields.get(1).choosenField}">
								<c:choose>
									<c:when test="${sortFields.get(1).sortType eq 'ASC'}"><i class="fa fa-sort-asc" aria-hidden="true"></i></c:when>
									<c:when test="${sortFields.get(1).sortType eq 'DESC'}"><i class="fa fa-sort-desc" aria-hidden="true"></i></c:when>
								</c:choose>
							</c:if>
						</a>
					</th>
					<th>Address
						<a href="${root_directory}contactlist?countRow=${countRow}&sortField=2&sortType=${sortFields.get(2).sortType}">
							<c:if test="${!sortFields.get(2).choosenField}">
								<i class="fa fa-sort" aria-hidden="true"></i>
							</c:if>
							<c:if test="${sortFields.get(2).choosenField}">
								<c:choose>
									<c:when test="${sortFields.get(2).sortType eq 'ASC'}"><i class="fa fa-sort-asc" aria-hidden="true"></i></c:when>
									<c:when test="${sortFields.get(2).sortType eq 'DESC'}"><i class="fa fa-sort-desc" aria-hidden="true"></i></c:when>
								</c:choose>
							</c:if>
						</a>
					</th>
					<th>Work place
						<a href="${root_directory}contactlist?countRow=${countRow}&sortField=3&sortType=${sortFields.get(3).sortType}">
							<c:if test="${!sortFields.get(3).choosenField}">
								<i class="fa fa-sort" aria-hidden="true"></i>
							</c:if>
							<c:if test="${sortFields.get(3).choosenField}">
								<c:choose>
									<c:when test="${sortFields.get(3).sortType eq 'ASC'}"><i class="fa fa-sort-asc" aria-hidden="true"></i></c:when>
									<c:when test="${sortFields.get(3).sortType eq 'DESC'}"><i class="fa fa-sort-desc" aria-hidden="true"></i></c:when>
								</c:choose>
							</c:if>
						</a>
					</th>	
				</tr>
			</thead>
			<tbody>
			<c:forEach var="contact" items="${contactList}">
				<tr>
					<td>
						<div class="wrap-checkbox">
							<input type="checkbox" name="delete" class="delete-by-id" value="${contact.id}">
							<label></label>
						<div>
					</td>
					<td>
						<a href="<c:url value="/contact?id=${contact.id}"/>">
							${contact.firstname} ${contact.lastname}
						</a>
					</td>
					<td>${contact.birthday}</td>
					<td>${contact.address.country},${contact.address.city},
						${contact.address.street},
						${contact.address.build}-${contact.address.flat}</td>
					<td>${contact.workplace}</td>
			</c:forEach>
			</tbody>
		</table>
		
		
		
		<div class="wrap-pagination">
			
			<ul class="pagination">
				
				<c:if test="${paginator.buttonPrev}">
				<li>
					<a href="<c:url value="/contactlist?page=${page-1}&countRow=${countRow}&sortField=${sortField}&sortType=${sortType}" />">
						&laquo; 
					</a>
				</li>
				</c:if>
				<c:if test="${paginator.skipLeft}">
					<li class="not-link"><span>...</span></li>
				</c:if>
				
				<c:forEach items="${paginator.listPages}" var="item" >
					<li <c:if test="${page eq item}">class="active"</c:if> >
						<a href="<c:url value="/contactlist?page=${item}&countRow=${countRow}&sortField=${sortField}&sortType=${sortType}" />">
							<c:out value="${item+1}" /> 
						</a>
					</li>
				</c:forEach>
				

				<c:if test="${paginator.skipRight}">
					<li class="not-link"><span>...</span></li>
				</c:if>
				<c:if test="${paginator.buttonNext}">
					<li>
						<a href="<c:url value="/contactlist?page=${page+1}&countRow=${countRow}&sortField=${sortField}&sortType=${sortType}" />">
							&raquo; 
						</a>
					</li>
				</c:if>
			</ul>
		</div>
	</section>
</div>

<div class="popup-overlay" id="modal">
	<div class = "modal-window">
		<h5>Attention</h5>
		<div>
			Are you sure that you want to delete the selected contacts?
		</div>
		<div class="modal-buttons">
			<a href="#" class="btn btn-default" id="delete-contact">Yes</a>
			<a href="#" class="btn btn-default" id="close-modal">No</a>
		</div>
	</div>
</div>


</body>
</html>
<script>
	
	<!--Script for lang-->
	var body = document.getElementsByTagName("body")[0];
	var lang = document.getElementById("lan");
	var choose_lang = document.getElementById("choose-lang");
	
	body.onclick = function(event){
		event = event || window.event;
		if (!lang.contains(event.target)){
			lang.style.overflow = "hidden";
		}
	}
	
	
	lang.onclick = function(event){
		event = event || window.event;
		console.log(event.target)
		this.style.overflow = "visible";
	}
	

	var a_lang = document.getElementById("list-lang").getElementsByTagName("a");
	for (var i in a_lang){
		a_lang[i].onclick = function(event){
			choose_lang.innerHTML = this.innerHTML;
			lang.style.overflow = "hidden";
			event.stopPropagation()
			return false;	
		}
	}
	
	
	<!--Script for checkbox-->
	var rootPath = window.location.protocol + "//" + window.location.host
			+ window.location.pathname.replace("/contactlist", "");

	
	var listCheckbox = document.getElementsByTagName("table")[0]
	                           .getElementsByClassName("delete-by-id");
	
	for(var i in listCheckbox){
		listCheckbox[i].onchange = function(){
			var tr = this.parentNode.parentNode.parentNode;
			if (tr.getAttribute("class") == "checked"){
				tr.removeAttribute("class"); 
			} else {
				tr.setAttribute("class", "checked");
			}
		}
	}
	

	
	document.getElementById("count-row").onchange = function(event) {
		window.location.replace(rootPath + "/contactlist?countRow="
				+ this.value);
	};

	function getCheckedId() {
		arrayId = [];
		for (var i = 0; i < listCheckbox.length; i++) {
			if (listCheckbox[i].checked)
				arrayId.push(listCheckbox[i].value);
		}
		return arrayId;
	}

	document.getElementById("delete-contact").onclick = function(event) {
		var checkedId = getCheckedId();
		window.location.replace(rootPath + "/contactdelete?delete="
				+ JSON.stringify(checkedId) + "&page=${page}&countRow=${countRow}" );
		
		return false;
	};
	
	document.getElementById("delete-all").onclick = function(){
		var position = this.checked;
		for (var i = 0; i < listCheckbox.length; i++) {
			listCheckbox[i].checked = position;
		}
		for(var i = 0; i < listCheckbox.length; i++){
			if (listCheckbox[i].checked == true){
				listCheckbox[i].parentNode.parentNode.parentNode.setAttribute("class", "checked");
			} else{
				listCheckbox[i].parentNode.parentNode.parentNode.removeAttribute("class");
			}
		}
	}
	
	
	var showpopup = document.getElementById("show-delete-popup");
	
	var popup_overlay = document.getElementById("modal");
	
	showpopup.onclick = function(){
		popup_overlay.style.display = "block";
		return false;
	}
	var but_close_modal = document.getElementById("close-modal");
	but_close_modal.onclick = function(){
		popup_overlay.style.display = "none";
		return false;
	}
	
</script>