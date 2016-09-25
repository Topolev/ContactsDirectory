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
    <link href="${root_for_css}/style-sendmessage.css" rel="stylesheet">


</head>
<body>


<jsp:include page="header.jsp"/>

<div id="wrap-content" class="user-container">

    <section>
        <h3>Send messages via email</h3>
        <div class="block-input">
            <form method="post"  action="/sendmessage" class="form-horizontal" >

                <div class="form-group">
                    <label class="col-sm-2 control-label">Send to: </label>
                    <div class="col-sm-10">
                        <c:forEach items="${contacts}" var="item">
                            <span class="email">${item.email}</span>
                        </c:forEach>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="subject">Subject: </label>
                    <div class="col-sm-10">
                        <input type="text" name="subject" class="form-control" id="subject" placeholder="Subject email">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="subject">Template: </label>
                    <div class="col-sm-10">
                        <select name="template" id="template">
                            <option value="withouttemplate">Without template</option>
                            <option value="template1">Template 01</option>
                            <option value="template2">Template 02</option>
                        </select>

                        <input type="hidden" name="template1" value="Hello, $u.firstname$.&#13;&#10;We glad to see you in our site!&#13;&#10;Regards!"./>
                        <input type="hidden" name="template2" value="Hello, $u.lastname$"./>


                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label" for="message">Message: </label>
                    <div class="col-sm-10">
                        <div class="alert alert-warning">
                            If you want You can use private information for each contact. Availabale expression:
                            &lt;firstName&gt;, &lt;lastName&gt;.
                        </div>
                        <textarea class="form-control" id="message" name="message">
                        </textarea>
                    </div>
                </div>
                <input type="hidden" value=${sendto} name="sendto">
                <input type="submit" value="Send messages" class="btn btn-default">
                <a href="${root_directory}contactlist" class="btn btn-default">Cancel</a>

            </form>
        </div>
    </section>
</div>
<script src="${root_for_js}/main.js"></script>
</body>
</html>

<script>
    document.getElementById("template").onchange = function(){
        var value = this.value;
        var input = getHiddenInputField(value);
        document.getElementById("message").innerHTML = input.value;
    }

    getHiddenInputField = function(nameField){
        var hidden = document.getElementsByTagName("input");
        for (var i = 0; i <hidden.length; i++){
            if ((hidden[i].getAttribute("type"))== "hidden" &&( hidden[i].getAttribute("name") == nameField)){
                return hidden[i];
            }
        }
    }


</script>