<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">Course Description</h5>
<c:choose>
  <c:when test="${browseCoursesPage.hasSelectedCourse}">
    <div class="card-body">
      <h4 class="card-title mt-1">${browseCoursesPage.selectedCourse.title}</h4>
      <h5 class="card-title mb-5">${browseCoursesPage.selectedCourse.code}</h5>
      <h6 class="card-title">Description:</h6>
      <p class="card-text">${browseCoursesPage.selectedCourse.description}</p>
      <h6 class="card-title mt-2">Credits:</h6>
      <p class="card-text">${browseCoursesPage.selectedCourse.credits}</p>
    </div>
  </c:when>
  <c:otherwise>
    <div class="card-body d-flex justify-content-center">
      <h4 class="card-title mt-1 d-flex align-self-center">No Course Selected.</h4>
    </div>
  </c:otherwise>
</c:choose>