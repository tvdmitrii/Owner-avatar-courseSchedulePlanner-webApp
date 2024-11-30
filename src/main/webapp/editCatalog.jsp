<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/editCatalog.css"/>
        <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
        <c:import url="/templates/toastTriggerScript.jsp"/>
        <title>Course Schedule Planner</title>
    </head>
    <body class="d-flex">
        <c:import url="/templates/header.jsp"/>
        <%-- Setup Variables for JSPs. --%>
        <c:set var="courses" scope="request" value="${editCatalogPage.courses}"/>
        <c:set var="departments" scope="request" value="${editCatalogPage.departments}"/>
        <c:set var="sections" scope="request" value="${editCatalogPage.sections}"/>
        <c:set var="instructors" scope="request" value="${editCatalogPage.instructors}"/>
        <c:set var="detailsServlet" scope="request" value="catalog/select"/>
        <c:set var="courseListTitle" scope="request" value="Courses"/>
        <c:set var="courseListEmptyText" scope="request" value="No Courses."/>
        <c:set var="sectionListTitle" scope="request" value="Sections"/>
        <c:set var="sectionListEmptyText" scope="request" value="No Sections."/>
        <main class="d-flex">
            <%-- Course Search Panel. --%>
            <div id="course-search-section" class="card d-flex my-3 ms-3 p-3">
                <c:import url="/templates/catalog/courseSearch.jsp"/>
            </div>
            <%-- Course List Panel. --%>
            <div id="course-list-section" class="card d-flex my-3 p-3">
                <c:import url="/templates/courseList.jsp"/>
            </div>
            <%-- Section List Panel. --%>
            <div id="section-list-section" class="card d-flex my-3 p-3">
                <c:import url="/templates/sectionList.jsp"/>
            </div>
            <%-- Editor Panel. --%>
            <div id="editor-section" class="card d-flex my-3 me-3 p-3">
                <%-- Choose whether to show course editor or section editor. --%>
                <c:choose>
                    <c:when test="${editCatalogPage.isCourseMode}">
                        <c:import url="/templates/catalog/courseEdit.jsp"/>
                    </c:when>
                    <c:otherwise>
                        <c:import url="/templates/catalog/sectionEdit.jsp"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
        <c:import url="/templates/toasts.jsp"/>
        <c:import url="/templates/footer.jsp"/>
    </body>
</html>
