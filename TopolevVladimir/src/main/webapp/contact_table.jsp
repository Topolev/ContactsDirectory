<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${count == 0}">
    <div class="alert alert-warning">There aren't contacts. You can create the first one.</div>
</c:if>
<c:if test="${(fn:length(contactList) == 0) and (count != 0)}">
    <div class="alert alert-warning">${resourceBundle.getString("isntexistpageforpaginator")}</div>
</c:if>

<c:if test="${fn:length(contactList) != 0}">
    <table class="table">
    <thead>
    <tr>
        <th>
            <div class="wrap-checkbox">
                <input type="checkbox" id="delete-all">
                <label></label>
                <div>
        </th>

        <th>${resourceBundle.getString("firstnamelastname")}
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

        <th>${resourceBundle.getString("birthday")}
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
        <th>${resourceBundle.getString("address")}
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
        <th>${resourceBundle.getString("workplace")}
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
            <a href="<c:url value="/contact?id=${contact.id}&page=${page}&countRow=${countRow}"/>">
                    ${contact.firstname} ${contact.lastname}
            </a>
        </td>
        <td>${contact.birthday}</td>
        <td>
            <c:if test="${(contact.address.country != null) and (contact.address.city != null) and (contact.address.street != null) and (contact.address.build != null) and (contact.address.flat != null)}">
                ${contact.address.country},${contact.address.city},
                ${contact.address.street},${contact.address.build}-${contact.address.flat}
            </c:if>
            <c:if test="${!((contact.address.country != null) and (contact.address.city != null) and (contact.address.street != null) and (contact.address.build != null) and (contact.address.flat != null))}">
                ${resourceBundle.getString("notavailable")}
            </c:if>
        </td>
        <td>
            <c:if test="${(contact.workplace == null) or (contact.workplace.trim()=='')}">
                ${resourceBundle.getString("notavailable")}
            </c:if>
            <c:if test="${!((contact.workplace == null) or (contact.workplace.trim()==''))}">
                ${contact.workplace}
            </c:if>
        </td>
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
            <li>
                <a href="<c:url value="/contactlist?page=0&countRow=${countRow}&sortField=${sortField}&sortType=${sortType}" />">
                    1
                </a>
            </li>
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
            <li>
                <a href="<c:url value="/contactlist?page=${paginator.countPage - 1}&countRow=${countRow}&sortField=${sortField}&sortType=${sortType}" />">
                        ${paginator.countPage}
                </a>
            </li>
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
</c:if>