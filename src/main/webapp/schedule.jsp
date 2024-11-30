<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/schedule.css"/>
        <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
        <c:import url="/templates/toastTriggerScript.jsp"/>
        <title>Schedule | Course Schedule Planner</title>
    </head>
    <body class="d-flex">
        <c:import url="/templates/header.jsp"/>
        <%-- Setup Variables for JSPs. --%>
        <c:set var="schedules" scope="request" value="${schedulePage.schedules}"/>
        <main class="d-flex">
            <%-- Schedule Table Panel. --%>
            <div id="schedule-table-section" class="card d-flex my-3 p-3">
                <c:import url="/templates/scheduleTable.jsp"/>
            </div>
        </main>
        <c:import url="/templates/toasts.jsp"/>
        <c:import url="/templates/footer.jsp"/>
    </body>
</html>
