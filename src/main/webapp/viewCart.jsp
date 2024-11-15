<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontawesome.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/viewCart.css"/>
        <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
        <title>Course Schedule Planner</title>
    </head>
    <body class="d-flex">
        <c:import url="/templates/header.jsp"/>
        <c:set var="courses" scope="request" value="${viewCartPage.courses}"/>
        <c:set var="detailsServlet" scope="request" value="cart/select"/>
        <c:set var="courseListTitle" scope="request" value="Cart"/>
        <c:set var="courseListEmptyText" scope="request" value="No Courses."/>
        <main class="d-flex">
            <div id="course-list-section" class="card d-flex my-3 p-3">
                <c:import url="/templates/courseList.jsp"/>
            </div>
            <div id="section-table-section" class="card d-flex my-3 me-3 p-3">
                <c:import url="/templates/sectionTable.jsp"/>
            </div>
        </main>
        <c:import url="/templates/footer.jsp"/>
    </body>
</html>
