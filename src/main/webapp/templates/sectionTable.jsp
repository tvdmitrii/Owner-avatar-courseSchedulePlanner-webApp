<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">Course Sections</h5>
<c:choose>
    <%-- Display this if there are sections to display. --%>
    <c:when test="${viewCartPage.hasSections}">
        <div id="section-table-card-body" class="card-body">
            <%-- Form to edit section selection or remove the course from cart. --%>
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
                        <%-- Go through each available section. Sections is a map. --%>
                        <c:forEach var="entry" items="${viewCartPage.sections}" varStatus="loop">
                            <tr>
                                <%-- Display section list number. --%>
                                <th scope="row">Section ${loop.index+1}</th>
                                <%-- Checkbox to select or unselect section. --%>
                                <td>
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox"
                                            name="sectionIds" value="${entry.key}"
                                            ${entry.value.isSelected ? "checked" : ""}>
                                    </div>
                                </td>
                                <%-- Display the days section meets. --%>
                                <td>${entry.value.daysOfWeek}</td>
                                <%-- Display the time section meets. --%>
                                <td>${entry.value.meetingTime}</td>
                                <%-- Display instructor name. --%>
                                <td>${entry.value.instructor.name}</td>
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
    <%-- Display this if a course is selected but it does not have any sections. --%>
    <c:when test="${courses.hasSelected}">
        <div class="card-body d-flex flex-column ">
            <div class="d-flex flex-grow-1 justify-content-center">
                <h4 class="card-title mt-1 d-flex align-self-center">There Are No Sections.</h4>
            </div>
            <div class="d-flex">
                <%-- Form to remove the course from cart. --%>
                <form action="${pageContext.request.contextPath}/cart/edit" method="POST">
                    <button type="submit" name="action" value="delete" class="btn btn-danger">Remove</button>
                </form>
            </div>
        </div>
    </c:when>
    <%-- Display this if no course in cart is selected. --%>
    <c:otherwise>
        <div class="card-body d-flex justify-content-center">
            <h4 class="card-title mt-1 d-flex align-self-center">No Course Selected.</h4>
        </div>
    </c:otherwise>
</c:choose>