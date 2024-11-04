<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="css/bootstrap.min.css"/>
        <link rel="stylesheet" href="css/fontawesome.css"/>
        <link rel="stylesheet" href="css/main.css"/>
        <link rel="stylesheet" href="css/viewCart.css"/>
        <script src="js/bootstrap.bundle.min.js"></script>
        <title>Course Schedule Planner</title>
    </head>
    <body class="d-flex">
        <c:import url="/templates/header.jsp"/>
        <c:set var="hasSelectedCourse" scope="request" value="${viewCartPage.hasSelectedCourse}"/>
        <c:set var="selectedCourse" scope="request" value="${viewCartPage.selectedCourse}"/>
        <c:set var="hasLoadedCourses" scope="request" value="${viewCartPage.hasLoadedCourses}"/>
        <c:set var="loadedCourses" scope="request" value="${viewCartPage.loadedCourses}"/>
        <c:set var="detailsServlet" scope="request" value="viewCartSelectCourse"/>
        <c:set var="hasSections" scope="request" value="${viewCartPage.hasSections}"/>
        <main class="d-flex">
            <div id="course-list-section" class="card d-flex my-3 p-3">
                <c:import url="/templates/courseList.jsp"/>
            </div>
            <div id="section-list-section" class="card d-flex my-3 me-3 p-3">
                <c:import url="/templates/sectionList.jsp"/>
            </div>
        </main>
        <c:import url="/templates/footer.jsp"/>
    </body>
</html>
