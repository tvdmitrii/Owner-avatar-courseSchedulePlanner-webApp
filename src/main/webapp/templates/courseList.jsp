<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">${courseListTitle}</h5>
<c:choose>
    <c:when test="${hasLoadedCourses}">
        <div id="course-list-card-body" class="card-body">
            <div id="course-list" class="overflow-auto">
                <ul class="list-group align-content-stretch">
                    <c:forEach var="course" items="${loadedCourses}" varStatus="loop">
                        <a href="${pageContext.request.contextPath}/${detailsServlet}?courseListId=${loop.index}"
                           class="list-group-item ${hasSelectedCourse
                                                && course.id == selectedCourse.id ? "active" : ""}">
                                ${course.code}: ${course.title}
                        </a>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="card-body d-flex justify-content-center">
            <h4 class="card-title mt-1 d-flex align-self-center">${courseListEmptyText}</h4>
        </div>
    </c:otherwise>
</c:choose>