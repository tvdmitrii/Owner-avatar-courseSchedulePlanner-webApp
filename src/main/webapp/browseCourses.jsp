<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/browseCourses.css"/>
        <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
        <c:import url="/templates/toastTriggerScript.jsp"/>
        <title>Browse | Course Schedule Planner</title>
    </head>
    <body class="d-flex">
        <c:import url="/templates/header.jsp"/>
        <%-- Setup Variables for JSPs. --%>
        <c:set var="courses" scope="request" value="${browseCoursesPage.courses}"/>
        <c:set var="departments" scope="request" value="${browseCoursesPage.departments}"/>
        <c:set var="detailsServlet" scope="request" value="browser/select"/>
        <c:set var="courseListTitle" scope="request" value="Results"/>
        <c:set var="courseListEmptyText" scope="request" value="No Results."/>
        <main class="d-flex">
            <%-- Course Search Panel. --%>
            <div id="course-search-section" class="card d-flex my-3 ms-3 p-3">
                <c:import url="/templates/courseSearch.jsp"/>
            </div>
            <%-- Search Results Panel. --%>
            <div id="course-list-section" class="card d-flex my-3 p-3">
                <c:import url="/templates/courseList.jsp"/>
            </div>
            <%-- Course Details Panel. --%>
            <div id="course-description-section" class="card d-flex my-3 me-3 p-3">
                <c:import url="/templates/courseInfo.jsp"/>
            </div>
        </main>
        <c:import url="/templates/toasts.jsp"/>
        <c:import url="/templates/footer.jsp"/>
    </body>
</html>
