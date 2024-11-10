<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">Course Sections</h5>
<c:choose>
    <c:when test="${viewCartPage.hasSelectedCourse && viewCartPage.hasSections}">
        <div id="section-list-card-body" class="card-body">
            <form action="${pageContext.request.contextPath}/cart/edit" method="POST">
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
                        <c:forEach var="entry" items="${viewCartPage.selectedCourse.sections}" varStatus="loop">
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
                <button type="submit" name="action" value="save" class="btn btn-primary">Save</button>
                <button type="submit" name="action" value="delete" class="btn btn-danger">Remove</button>
            </form>
        </div>
    </c:when>
    <c:when test="${viewCartPage.hasSelectedCourse}">
        <div class="card-body d-flex flex-column ">
            <div class="d-flex flex-grow-1 justify-content-center">
                <h4 class="card-title mt-1 d-flex align-self-center">There Are No Sections.</h4>
            </div>
            <div class="d-flex">
                <form action="${pageContext.request.contextPath}/cart/edit" method="POST">
                    <button type="submit" name="action" value="delete" class="btn btn-danger">Remove</button>
                </form>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="card-body d-flex justify-content-center">
            <h4 class="card-title mt-1 d-flex align-self-center">No Course Selected.</h4>
        </div>
    </c:otherwise>
</c:choose>