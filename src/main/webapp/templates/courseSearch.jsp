<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">Search Tools</h5>
<div class="d-flex flex-column card-body">
  <%-- Search Form --%>
  <form class="d-flex flex-column flex-grow-1" action="${pageContext.request.contextPath}/browser/search" method="GET">
    <%-- Department Dropdown --%>
    <div class="mb-3">
      <label for="department-input" class="form-label">Department</label>
      <select class="form-select" id="department-input" name="departmentListId">
        <%-- Default Option --%>
        <option ${!departments.hasSelected ? "selected" : ""} value="-1">Select Department</option>
        <%-- Populated List of Departments --%>
        <c:forEach var="department" items="${departments.items}" varStatus="loop">
          <option
            ${departments.hasSelected && department.id == departments.selected.id ? "selected" : ""}
            value="${loop.index}"
          >
            ${department.name}
          </option>
        </c:forEach>
      </select>
    </div>
    <%-- Course Title Substring Input --%>
    <div class="mb-3">
      <label for="course-title-input" class="form-label">Course Title</label>
      <input type="text" class="form-control" id="course-title-input" name="title"
             value="${browseCoursesPage.hasTitleSearchTerm ? browseCoursesPage.titleSearchTerm : ""}">
    </div>
    <%-- Search Form Submit Button --%>
    <button type="submit" class="btn btn-primary">Search</button>
  </form>
</div>