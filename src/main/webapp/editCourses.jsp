<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontawesome.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/editCourses.css"/>
        <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
        <c:import url="/templates/toastTriggerScript.jsp"/>
        <title>Course Schedule Planner</title>
    </head>
    <body class="d-flex">
        <c:import url="/templates/header.jsp"/>
        <c:set var="courses" scope="request" value="${editCoursesPage.courses}"/>
        <c:set var="departments" scope="request" value="${editCoursesPage.departments}"/>
        <c:set var="sections" scope="request" value="${editCoursesPage.sections}"/>
        <c:set var="instructors" scope="request" value="${editCoursesPage.instructors}"/>
        <c:set var="detailsServlet" scope="request" value="admin/select"/>
        <c:set var="courseListTitle" scope="request" value="Courses"/>
        <c:set var="courseListEmptyText" scope="request" value="No Courses."/>
        <c:set var="sectionListTitle" scope="request" value="Sections"/>
        <c:set var="sectionListEmptyText" scope="request" value="No Sections."/>
        <main class="d-flex">
            <div id="course-search-section" class="card d-flex my-3 ms-3 p-3">
                <c:import url="/templates/courseSearch.jsp"/>
            </div>
            <div id="course-list-section" class="card d-flex my-3 p-3">
                <c:import url="/templates/courseList.jsp"/>
            </div>
            <div id="section-list-section" class="card d-flex my-3 p-3">
                <c:import url="/templates/sectionList.jsp"/>
            </div>
            <div id="course-description-section" class="card d-flex my-3 me-3 p-3">
                <c:choose>
                    <c:when test="${editCoursesPage.isCourseMode}">
                        <c:import url="/templates/admin/courseEdit.jsp"/>
                    </c:when>
                    <c:otherwise>
                        <c:import url="/templates/admin/sectionEdit.jsp"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
        <c:import url="/templates/toasts.jsp"/>
        <c:import url="/templates/footer.jsp"/>
    </body>
</html>
