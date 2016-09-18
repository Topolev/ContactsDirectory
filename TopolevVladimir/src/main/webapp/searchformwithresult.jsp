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
    <link href="${root_for_css}/style-contact-list.css" rel="stylesheet">

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
        <h3>Search form</h3>
        <div class="block-input">
            <form method="post"  action="/searchform" class="form-horizontal" >

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="firstname">First name</label>
                    <div class="col-sm-10">
                        <input type="text" name="first_name" class="form-control" id="firstname" placeholder="First name" value="${first_name}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="lastname">Last name</label>
                    <div class="col-sm-10">
                        <input type="text" name="last_name" class="form-control" id="lastname" placeholder="Last name" value="${last_name}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="middlename">Middle name</label>

                    <div class="col-sm-10">
                        <input type="text" name="middle_name" class="form-control" id="middlename" placeholder="Middle name" value="${middle_name}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="male">Sex:</label>
                    <div class="col-sm-10">
                        <input type="radio" name="sex" class="radio" id="male" value="Male" <c:if test="${sex eq 'Male'}">checked</c:if>   >
                        <label for="male" class="sex"><span class="icon">Ù</span>Male</label>

                        <input type="radio" name="sex" class="radio" id="female" value="Female" <c:if test="${sex eq 'Female'}">checked</c:if> >
                        <label for="female" class="sex"><span class="icon">Ú</span>Female</label>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="single">Marital status:</label>
                    <div class="col-sm-10">
                        <input type="radio" name="marital_status" class="radio" id="single" value="Single" <c:if test="${marital_status eq 'Single'}">checked</c:if> >
                        <label for="single" class="sex"><span class="icon">Ù</span>Single</label>

                        <input type="radio" name="marital_status" class="radio" id="married" value="Married" <c:if test="${marital_status eq 'Married'}">checked</c:if> >
                        <label for="married" class="sex"><span class="icon">ÙÙ</span>Married</label>
                    </div>

                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="nationality">Nationality</label>
                    <div class="col-sm-10">
                        <input type="text" name="nationality" class="form-control" id="nationality" placeholder="Nationality" value="${nationality}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="country">Country</label>
                    <div class="col-sm-10">
                        <input type="text" name="country" class="form-control" id="country" placeholder="Country" value="${country}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="city">City</label>
                    <div class="col-sm-10">
                        <input type="text" name="city" class="form-control" id="city" placeholder="City" value="${city}">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="street">Street</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="street" placeholder="Street" value="${street}">
                    </div>
                </div>

                <input type="submit" class="btn btn-default" value="Search">

            </form>
        </div>


        <div class="control-panel">
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

        <%@ include file="contact_table.jsp"%>
    </section>

</div>
<script src="${root_for_js}/main.js"></script>
<script src="${root_for_js}/table_search.js"></script>
</body>
</html>