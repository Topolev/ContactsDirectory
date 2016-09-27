<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<table class="table">
    <thead>
    <tr>
        <th>
            <div class="wrap-checkbox">
                <input type="checkbox" id="delete-all">
                <label></label>
                <div>
        </th>

        <th>First name, last name</th>
        <th>Birthday</th>
        <th>Address</th>
        <th>Work place</th>
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
                <a href="<c:url value="/searchform?page=${page-1}&countRow=${countRow}" /><c:if test="${listFields ne ''}">&${listFields}</c:if>">
                    &laquo;
                </a>
            </li>
        </c:if>
        <c:if test="${paginator.skipLeft}">
            <li>
                <a href="<c:url value="/searchform?page=0&countRow=${countRow}" /><c:if test="${listFields ne ''}">&${listFields}</c:if> ">
                    1
                </a>
            </li>
            <li class="not-link"><span>...</span></li>
        </c:if>

        <c:forEach items="${paginator.listPages}" var="item" >
            <li <c:if test="${page eq item}">class="active"</c:if> >
                <a href="<c:url value="/searchform?page=${item}&countRow=${countRow}" /><c:if test="${listFields ne ''}">&${listFields}</c:if>">
                    <c:out value="${item+1}" />
                </a>
            </li>
        </c:forEach>


        <c:if test="${paginator.skipRight}">
            <li class="not-link"><span>...</span></li>
            <li>
                <a href="<c:url value="/searchform?page=${paginator.countPage - 1}&countRow=${countRow}" /><c:if test="${listFields ne ''}">&${listFields}</c:if>">
                        ${paginator.countPage}
                </a>
            </li>
        </c:if>
        <c:if test="${paginator.buttonNext}">
            <li>
                <a href="<c:url value="/searchform?page=${page+1}&countRow=${countRow}" /><c:if test="${listFields ne ''}">&${listFields}</c:if>">
                    &raquo;
                </a>
            </li>
        </c:if>
    </ul>
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