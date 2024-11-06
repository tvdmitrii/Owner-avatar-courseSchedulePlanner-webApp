<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">Results</h5>
<c:choose>
    <c:when test="${hasSelectedCourse && hasSections}">
        <form action="${pageContext.request.contextPath}/viewCartSaveCourse" method="POST">
            <div id="section-list-card-body" class="card-body">
                <div id="section-table" class="overflow-auto">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th scope="col">Section</th>
                            <th scope="col">Selected</th>
                            <th scope="col">Meeting Days</th>
                            <th scope="col">Meeting Times</th>
                            <th scope="col">Instructor</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="entry" items="${selectedCourse.sections}" varStatus="loop">
                            <tr>
                                <th scope="row">Section ${loop.index+1}</th>
                                <td>
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="sectionIds" value="${entry.key}"
                                            ${entry.value.isSelected ? "checked" : ""}>
                                    </div>
                                </td>
                                <td>${entry.value.daysOfTheWeek}</td>
                                <td>${entry.value.meetingTimes}</td>
                                <td>${entry.value.instructor}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <button type="submit" class="btn btn-primary">Save</button>
            </div>
        </form>
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