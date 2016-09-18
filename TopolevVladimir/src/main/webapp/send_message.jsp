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

    <section>
        <h3>Send messages via email</h3>
        <div class="block-input">
            <form method="post"  action="/searchform" class="form-horizontal" >

                <div class="form-group">
                    <label class="col-sm-2 control-label">Send to: </label>
                    <div class="col-sm-10">
                        <c:forEach items="${contacts}" var="item">
                            <span>${item.email}</span>,
                        </c:forEach>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="subject">Subject: </label>
                    <div class="col-sm-10">
                        <input type="text" name="last_name" class="form-control" id="subject" placeholder="Subject email">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="subject">Template: </label>
                    <div class="col-sm-10">
                        <input type="text" name="last_name" class="form-control" id="subject" placeholder="Subject email">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="message">Message: </label>
                    <div class="col-sm-10">
                        <textarea class="form-control" id="message" >
                        </textarea>
                    </div>
                </div>
                <a href="${root_directory}contactlist" class="btn btn-default">Cancel</a>


            </form>
        </div>
    </section>
</div>
<script src="${root_for_js}/main.js"></script>
</body>
</html>