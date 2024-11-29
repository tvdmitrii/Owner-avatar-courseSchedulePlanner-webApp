<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">
    Schedules ${schedules.hasItems ? String.format("%d/%d", schedules.selectedId + 1, schedules.size) : ""}
</h5>
<c:choose>
    <c:when test="${schedules.hasItems}">
        <c:set var="days" scope="request" value="${['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday']}"/>
        <c:set var="schedule" scope="request" value="${schedules.selected}"/>
        <div id="schedule-table-card-body" class="card-body d-flex align-items-center">
            <div class="d-flex">
                <button type="submit" class="btn btn-primary">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-left" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8"/>
                    </svg>
                </button>
            </div>
            <div class="container d-flex flex-column align-self-stretch position-relative">
                <div class="row border">
                    <div class="col border">
                        <h5 class="text-center">Time / Day</h5>
                    </div>
                    <c:forEach var="day" items="${days}">
                        <div class="col border">
                            <h5 class="text-center">${day}</h5>
                        </div>
                    </c:forEach>
                </div>
                <c:forEach begin="7" end="19" varStatus="loop">
                    <div class="row border flex-grow-1">
                        <div class="col border time-row">
                                ${loop.index > 12 ? loop.index - 12 : loop.index}:00 ${loop.index >= 12 ? "PM" : "AM"}
                        </div>
                        <c:forEach begin="0" end="${days.size() - 1}">
                            <div class="col border">
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
                <c:forEach var="section" items="${schedule.sections}">
                    <c:set var="startTimeFraction" scope="request"
                           value="${section.startTime.time.hour + section.startTime.time.minute/60.0}"/>
                    <c:set var="endTimeFraction" scope="request"
                           value="${section.endTime.time.hour + section.endTime.time.minute/60.0}"/>
                    <c:set var="duration" scope="request"
                           value="${endTimeFraction - startTimeFraction}"/>
                    <c:forEach begin="0" end="${days.size() - 1}" varStatus="loop">
                        <c:if test="${section.daysOfWeek.daysOfWeek[loop.index]}">
                            <div class="schedule-block z-2"
                                 style="
                                         left: ${100.0/6*(loop.index+1)}%;
                                         top: ${100.0/14*(startTimeFraction-7+1)}%;
                                         height: ${100.0/14*duration}%">
                                    ${section.course.code}
                                    ${section.startTime}
                                    ${section.endTime}
                            </div>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </div>
            <div class="d-flex">
                <button type="submit" class="btn btn-primary">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-arrow-right" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8"/>
                    </svg>
                </button>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="card-body d-flex justify-content-center">
            <h4 class="card-title mt-1 d-flex align-self-center">No Schedules.</h4>
        </div>
    </c:otherwise>
</c:choose>