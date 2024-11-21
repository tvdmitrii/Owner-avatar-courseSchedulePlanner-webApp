<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">${sectionListTitle}</h5>
<c:choose>
    <c:when test="${sections.hasItems}">
        <div id="section-list-card-body" class="card-body">
            <div id="section-list" class="overflow-auto">
                <ul class="list-group align-content-stretch">
                    <c:forEach var="section" items="${sections.items}" varStatus="loop">
                        <a href="${pageContext.request.contextPath}/${detailsServlet}?sectionListId=${loop.index}"
                           class="list-group-item ${sections.hasSelected
                                                && section.id == sections.selected.id ? "active" : ""}">
                                Section ${loop.index+1}
                        </a>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="card-body d-flex justify-content-center">
            <h4 class="card-title mt-1 d-flex align-self-center">${sectionListEmptyText}</h4>
        </div>
    </c:otherwise>
</c:choose>