<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" data-bs-theme="dark" class="h-100">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/fontawesome.css"/>
    <link rel="stylesheet" href="css/main.css"/>
    <script src="js/bootstrap.bundle.min.js"></script>
    <title>Course Schedule Planner</title>
</head>
<body class="d-flex flex-column h-100">
<header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
    <a href="" class="d-flex mx-auto link-body-emphasis text-decoration-none">
        <span class="fs-1">Course Schedule Planner WebApp</span>
    </a>
</header>
<main class="flex-shrink-0">
    <div id="course-list" class="mx-auto my-5">
        <ul class="list-group">
            <c:forEach var="course" items="${courses}">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    ${course.code}: ${course.title}
                </li>
            </c:forEach>

        </ul>
    </div>
</main>
<footer class="footer mt-auto d-flex justify-content-end py-3 px-4 mb-4 border-top">
    <span class="fs-5 fst-italic fw-lighter">by Dmitrii Turygin</span>
</footer>
</body>
</html>
