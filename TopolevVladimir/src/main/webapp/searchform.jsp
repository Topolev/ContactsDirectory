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




</head>
<body>


<jsp:include page="header.jsp"/>

<div id="wrap-content" class="user-container">

        <section>
            <h3>${resourceBundle.getString("searchform")}</h3>
            <div class="block-input">
                <form method="post"  action="/searchform" class="form-horizontal" >

                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="firstname">${resourceBundle.getString("firstname")}</label>
                        <div class="col-sm-10">
                            <input type="text" name="first_name" class="form-control" id="firstname" placeholder="First name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="lastname">${resourceBundle.getString("lastname")}</label>
                        <div class="col-sm-10">
                            <input type="text" name="last_name" class="form-control" id="lastname" placeholder="Last name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="middlename">${resourceBundle.getString("middlename")}</label>

                        <div class="col-sm-10">
                            <input type="text" name="middle_name" class="form-control" id="middlename" placeholder="Middle name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="male">${resourceBundle.getString("sex")}:</label>
                        <div class="col-sm-10">
                            <input type="radio" name="sex" class="radio" id="male" value="Male">
                            <label for="male" class="sex"><span class="icon">Ù</span>${resourceBundle.getString("male")}</label>

                            <input type="radio" name="sex" class="radio" id="female" value="Female">
                            <label for="female" class="sex"><span class="icon">Ú</span>${resourceBundle.getString("female")}</label>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="single">${resourceBundle.getString("maritalstatus")}:</label>
                        <div class="col-sm-10">
                            <input type="radio" name="marital_status" class="radio" id="single" value="Single">
                            <label for="single" class="sex"><span class="icon">Ù</span>${resourceBundle.getString("single")}</label>

                            <input type="radio" name="marital_status" class="radio" id="married" value="Married">
                            <label for="married" class="sex"><span class="icon">ÙÙ</span>${resourceBundle.getString("married")}</label>
                        </div>

                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="nationality">${resourceBundle.getString("nationality")}</label>
                        <div class="col-sm-10">
                            <input type="text" name="nationality" class="form-control" id="nationality" placeholder="Nationality">
                        </div>
                    </div>

                    <div class="form-group" id="birthday">
                        <label class="col-sm-2 control-label" for="birthdaymore">${resourceBundle.getString("birthday")}</label>
                        <div class="col-sm-5">
                             <span>раньше</span> <input type="date" name="birthdaymore" class="form-control" id="birthdaymore" >
                                 <div class="warn-message">Date of this field have to be less or equal to date in the next field.</div>
                        </div>
                        <div class="col-sm-5">
                            <span>позже</span> <input type="date" name="birthdayless" class="form-control" id="birthdayless" >
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="country">${resourceBundle.getString("country")}</label>
                        <div class="col-sm-10">
                            <input type="text" name="country" class="form-control" id="country" placeholder="Country">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="city">${resourceBundle.getString("city")}</label>
                        <div class="col-sm-10">
                            <input type="text" name="city" class="form-control" id="city" placeholder="City">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label" for="street">${resourceBundle.getString("street")}</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="street" placeholder="Street">
                        </div>
                    </div>

                    <input type="submit" class="btn btn-default" value="${resourceBundle.getString("search")}" id="search">

                </form>
            </div>
        </section>

</div>
<script src="${root_for_js}/main.js"></script>
<script src="${root_for_js}/search.js"></script>

</body>
</html>

