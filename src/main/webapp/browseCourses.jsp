<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" href="css/fontawesome.css"/>
        <link rel="stylesheet" href="css/main.css"/>
        <script src="js/bootstrap.bundle.min.js"></script>
        <title>Course Schedule Planner</title>
    </head>
    <body class="d-flex">
        <c:import url="/templates/header.jsp"/>
        <main class="d-flex">
            <div id="course-search-section" class="card d-flex my-3 ms-3 p-3">
                <h5 class="card-header">Search Tools</h5>
                <div class="card-body">
                    <p class="card-text">Not yet functional!</p>
                    <form>
                        <div class="mb-3">
                            <label for="department-input" class="form-label">Department</label>
                            <select class="form-select" id="department-input" name="department">
                                <option selected>Select Department</option>
                                <option value="1">Computer Science</option>
                                <option value="2">Engineering</option>
                                <option value="3">English</option>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="course-title-input" class="form-label">Course Title</label>
                            <input type="text" class="form-control" id="course-title-input">
                        </div>
                        <button type="submit" class="btn btn-primary">Search</button>
                    </form>
                </div>
            </div>
            <div id="course-list-section" class="card d-flex my-3 p-3">
                <h5 class="card-header">Results</h5>
                <div id="course-list-card-body" class="card-body">
                    <div id="course-list" class="overflow-auto">
                        <ul class="list-group align-content-stretch">
                            <c:forEach var="course" items="${browseCoursesPage.loadedCourses}" varStatus="loop">
                                <a href="${pageContext.request.contextPath}/browseCoursesSelectCourse?courseListId=${loop.index}"
                                   class="list-group-item ${browseCoursesPage.hasSelectedCourse && course.id == browseCoursesPage.selectedCourse.id ? "active" : ""}">
                                        ${course.code}: ${course.title}
                                </a>
                            </c:forEach>
                        </ul>
                    </div>
                    <!--
                    <div id="course-list-pagination">
                        <nav>
                            <ul class="pagination">
                                <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                                <li class="page-item"><a class="page-link" href="#">1</a></li>
                                <li class="page-item"><a class="page-link" href="#">2</a></li>
                                <li class="page-item"><a class="page-link" href="#">3</a></li>
                                <li class="page-item"><a class="page-link" href="#">Next</a></li>
                            </ul>
                        </nav>
                    </div>-->
                </div>
            </div>
            <div id="course-description-section" class="card d-flex my-3 me-3 p-3">
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
                        <div class="card-body">
                            <h4 class="card-title mt-1">No Course Selected.</h4>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
        <c:import url="/templates/footer.jsp"/>
    </body>
</html>
