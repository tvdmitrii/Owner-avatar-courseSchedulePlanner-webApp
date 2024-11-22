<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<header class="d-flex flex-wrap justify-content-center border-bottom">
    <div id="header-title" class="d-flex mt-1">
        <span class="fs-3">Course Schedule Planner. Under Construction.</span>
    </div>
    <nav id="header-nav" class="d-flex navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
                    aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <%-- Page Navigation Section--%>
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link ${navState.name == 'browser' ? "active" : ""}"
                           href="${pageContext.request.contextPath}/browser/load">Browse</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${navState.name == 'cart' ? "active" : ""}"
                           href="${pageContext.request.contextPath}/cart/view">My Courses</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">My Schedules</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${navState.name == 'catalog' ? "active" : ""}"
                           href="${pageContext.request.contextPath}/catalog">Edit Catalog</a>
                    </li>
                </ul>
                <span class="flex-grow-1"></span>
                <%-- Account Section--%>
                <c:choose>
                    <c:when test="${userState == null}">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/logIn">Log In</a>
                            </li>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <div class="d-flex">Welcome, ${userState.firstName}!</div>
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/logOut">Log Out</a>
                            </li>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </nav>
</header>