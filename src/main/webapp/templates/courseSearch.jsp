<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">Search Tools</h5>
<div class="card-body">
  <form action="${pageContext.request.contextPath}/browser/search" method="GET">
    <div class="mb-3">
      <label for="department-input" class="form-label">Department</label>
      <select class="form-select" id="department-input" name="departmentListId">
        <option ${!departments.hasSelected ? "selected" : ""} value="-1">Select Department</option>
        <c:forEach var="department" items="${departments.items}" varStatus="loop">
          <option
            ${departments.hasSelected
                    && department.id == departments.selected.id ? "selected" : ""}
                  value="${loop.index}">${department.name}</option>
        </c:forEach>
      </select>
    </div>
    <div class="mb-3">
      <label for="course-title-input" class="form-label">Course Title</label>
      <input type="text" class="form-control" id="course-title-input" name="title"
             value="${browseCoursesPage.hasTitleSearchTerm ? browseCoursesPage.titleSearchTerm : ""}">
    </div>
    <button type="submit" class="btn btn-primary">Search</button>
  </form>
</div>