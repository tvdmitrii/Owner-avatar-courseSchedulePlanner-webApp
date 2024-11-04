<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" href="css/fontawesome.css"/>
        <link rel="stylesheet" href="css/main.css"/>
        <link rel="stylesheet" href="css/browseCourses.css"/>
        <script src="js/bootstrap.bundle.min.js"></script>
        <title>Course Schedule Planner</title>
    </head>
    <body class="d-flex">
        <c:import url="/templates/header.jsp"/>
        <c:set var="hasSelectedCourse" scope="request" value="${browseCoursesPage.hasSelectedCourse}"/>
        <c:set var="selectedCourse" scope="request" value="${browseCoursesPage.selectedCourse}"/>
        <c:set var="hasLoadedCourses" scope="request" value="${browseCoursesPage.hasLoadedCourses}"/>
        <c:set var="loadedCourses" scope="request" value="${browseCoursesPage.loadedCourses}"/>
        <c:set var="detailsServlet" scope="request" value="browseCoursesSelectCourse"/>
        <main class="d-flex">
            <div id="course-search-section" class="card d-flex my-3 ms-3 p-3">
                <c:import url="/templates/courseSearch.jsp"/>
            </div>
            <div id="course-list-section" class="card d-flex my-3 p-3">
                <c:import url="/templates/courseList.jsp"/>
            </div>
            <div id="course-description-section" class="card d-flex my-3 me-3 p-3">
                <c:import url="/templates/courseInfo.jsp"/>
            </div>
        </main>
        <c:import url="/templates/footer.jsp"/>
    </body>
</html>
