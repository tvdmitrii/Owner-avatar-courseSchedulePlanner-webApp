<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">Course Description</h5>
<c:choose>
  <c:when test="${courses.hasSelected}">
    <div class="card-body">
      <%-- Course Title. --%>
      <h4 class="card-title mt-1">${courses.selected.title}</h4>
      <%-- Course Code. --%>
      <h5 class="card-title mb-5">${courses.selected.code}</h5>
      <%-- Course Description. --%>
      <h6 class="card-title">Description:</h6>
      <p class="card-text">${courses.selected.description}</p>
      <%-- Course Number of Credits. --%>
      <h6 class="card-title mt-2">Credits:</h6>
      <p class="card-text">${courses.selected.credits}</p>
      <%-- Show add to cart button only if logged in. --%>
      <c:if test="${userState != null}">
        <form action="${pageContext.request.contextPath}/browser/addToCart" method="POST">
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