<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">Course Description</h5>
<c:choose>
  <c:when test="${hasSelectedCourse}">
    <div class="card-body">
      <h4 class="card-title mt-1">${selectedCourse.title}</h4>
      <h5 class="card-title mb-5">${selectedCourse.code}</h5>
      <h6 class="card-title">Description:</h6>
      <p class="card-text">${selectedCourse.description}</p>
      <h6 class="card-title mt-2">Credits:</h6>
      <p class="card-text">${selectedCourse.credits}</p>
      <c:if test="${userState != null}">
        <form action="${pageContext.request.contextPath}/browseCoursesAddToCart" method="POST">
          <button type="submit" class="btn btn-primary">Add to Cart</button>
        </form>
      </c:if>
    </div>
  </c:when>
  <c:otherwise>
    <div class="card-body d-flex justify-content-center">
      <h4 class="card-title mt-1 d-flex align-self-center">No Course Selected.</h4>
    </div>
  </c:otherwise>
</c:choose>