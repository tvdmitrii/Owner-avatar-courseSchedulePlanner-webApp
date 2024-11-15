<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">Course Editor</h5>
<div class="card-body">
  <form action="${pageContext.request.contextPath}/admin/course/edit" method="POST">
    <div class="mb-3">
      <label for="new-course-title-input" class="form-label">Course Title</label>
      <input type="text" class="form-control" id="new-course-title-input" name="title"
             value="${courses.hasSelected ? courses.selected.title : ""}" required>
    </div>
    <div class="mb-3">
      <label for="new-course-department-input" class="form-label">Department</label>
      <select class="form-select" id="new-course-department-input" name="departmentListId" required>
        <option ${!departments.hasSelected ? "selected" : ""} value="-1">Select Department</option>
        <c:forEach var="department" items="${departments.items}" varStatus="loop">
          <option ${courses.hasSelected
                  && department.id == courses.selected.department.id ? "selected" : ""}
                  value="${loop.index}">${department.name}</option>
        </c:forEach>
      </select>
    </div>
    <div class="mb-3">
      <label for="new-course-number-input" class="form-label">Course Number</label>
      <input type="text" class="form-control" id="new-course-number-input" name="number"
             value="${courses.hasSelected ? courses.selected.number : 0}" required>
    </div>
    <div class="mb-3">
      <label for="new-course-description-input" class="form-label">Description:</label>
      <textarea class="form-control" id="new-course-description-input" rows="3" name="description" required>${
        courses.hasSelected ? courses.selected.title : ""
        }</textarea>
    </div>
    <div class="mb-3">
      <label for="new-course-credits-input" class="form-label">Credits</label>
      <input type="text" class="form-control" id="new-course-credits-input" name="credits"
             value="${courses.hasSelected ? courses.selected.credits : 0}" required>
    </div>
    <div class="d-flex justify-content-center">
      <button type="submit" name="action" value="add" class="btn btn-primary me-1 d-flex">Add New Course</button>
      <c:if test="${courses.hasSelected}">
        <button type="submit" name="action" value="update" class="btn btn-primary mx-1 d-flex">Update Selected</button>
        <button type="submit" name="action" value="delete" class="btn btn-danger ms-1 d-flex">Delete Selected</button>
      </c:if>
    </div>
  </form>
</div>