<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">Results</h5>
<c:choose>
    <c:when test="${hasSelectedCourse && hasSections}">
        <div id="section-list-card-body" class="card-body">
            <div id="section-list" class="overflow-auto">
                <ul class="list-group align-content-stretch">
                    <c:forEach var="section" items="${selectedCourse.sections}" varStatus="loop">
                        <li class="list-group-item">
                            ${section.daysOfTheWeek} | ${section.meetingTimes} | ${section.instructor}
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:when>
    <c:when test="${hasSelectedCourse}">
        <div class="card-body d-flex justify-content-center">
            <h4 class="card-title mt-1 d-flex align-self-center">There Are No Sections.</h4>
        </div>
    </c:when>
    <c:otherwise>
        <div class="card-body d-flex justify-content-center">
            <h4 class="card-title mt-1 d-flex align-self-center">No Course Selected.</h4>
        </div>
    </c:otherwise>
</c:choose>