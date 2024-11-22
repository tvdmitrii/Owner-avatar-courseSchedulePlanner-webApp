<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">Section Editor</h5>
<div class="card-body">
  <form action="${pageContext.request.contextPath}/catalog/section" method="POST">
    <%-- Group of Meeting Week Day Checkboxes --%>
    <label for="weekDays" class="form-label">Meeting Days</label>
    <div id="weekDays" class="mb-3">
      <%-- Monday Checkbox --%>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="monday" name="weekDays"
          ${sections.hasSelected && sections.selected.daysOfWeek.daysOfWeek[0] ? "checked" : ""} value="0">
        <label class="form-check-label" for="monday">Monday</label>
      </div>
      <%-- Tuesday Checkbox --%>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="tuesday" name="weekDays"
          ${sections.hasSelected && sections.selected.daysOfWeek.daysOfWeek[1] ? "checked" : ""} value="1">
        <label class="form-check-label" for="tuesday">Tuesday</label>
      </div>
      <%-- Wednesday Checkbox --%>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="wednesday" name="weekDays"
          ${sections.hasSelected && sections.selected.daysOfWeek.daysOfWeek[2] ? "checked" : ""} value="2">
        <label class="form-check-label" for="wednesday">Wednesday</label>
      </div>
      <%-- Thursday Checkbox --%>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="thursday" name="weekDays"
          ${sections.hasSelected && sections.selected.daysOfWeek.daysOfWeek[3] ? "checked" : ""} value="3">
        <label class="form-check-label" for="thursday">Thursday</label>
      </div>
      <%-- Friday Checkbox --%>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="friday" name="weekDays"
          ${sections.hasSelected && sections.selected.daysOfWeek.daysOfWeek[4] ? "checked" : ""} value="4">
        <label class="form-check-label" for="friday">Friday</label>
      </div>
    </div>
    <%-- Section Instructor Dropdown --%>
    <div class="mb-3">
      <label for="instructor-input" class="form-label">Instructor</label>
      <select class="form-select" id="instructor-input" name="instructorListId" required>
        <%-- Default Option --%>
        <option
          ${!sections.hasSelected ? "selected" : ""}
          value="-1"
        >
          Select Instructor
        </option>
        <%-- Populated Instructor List --%>
        <c:forEach var="instructor" items="${instructors.items}" varStatus="loop">
          <option
            ${sections.hasSelected && instructor.id == sections.selected.instructor.id ? "selected" : ""}
            value="${loop.index}"
          >
            ${instructor.name}
          </option>
        </c:forEach>
      </select>
    </div>
    <%-- Group of Section Meeting Start Time Inputs --%>
    <label for="start-time" class="form-label">Start Time</label>
    <div class="mb-3 input-group" id="start-time">
      <%-- Start Time Hour Dropdown --%>
      <select class="form-select" id="start-time-hours-input" name="startTimeHours" required>
        <%-- Default Option --%>
        <option ${!sections.hasSelected ? "selected" : ""} value="-1">Select Hours</option>
        <%-- Hours in 1 Hour Increments --%>
        <c:forEach begin="1" end="12" varStatus="loop">
          <option
            ${sections.hasSelected && loop.index == sections.selected.startTime.hours ? "selected" : ""}
            value="${loop.index}"
          >
            ${loop.index}
          </option>
        </c:forEach>
      </select>
      <%-- Start Time Minute Dropdown --%>
      <select class="form-select" id="start-time-minutes-input" name="startTimeMinutes" required>
        <%-- Default Option --%>
        <option ${!sections.hasSelected ? "selected" : ""} value="-1">Select Minutes</option>
        <%-- Minutes in 5 Minute Increments --%>
        <c:forEach begin="0" end="11" varStatus="loop">
          <option
            ${sections.hasSelected && loop.index*5 == sections.selected.startTime.minutes ? "selected" : ""}
            value="${loop.index*5}"
          >
            ${loop.index*5}
          </option>
        </c:forEach>
      </select>
      <%-- AM/PM Dropdown --%>
      <select class="form-select" id="start-time-meridian-input" name="startTimeMeridian" required>
        <%-- Default Option --%>
        <option ${!sections.hasSelected ? "selected" : ""} value="-1">Select AM/PM</option>
        <%-- AM Option --%>
        <option
          ${sections.hasSelected && !sections.selected.startTime.pastNoon ? "selected" : ""}
          value="false"
        >
          AM
        </option>
        <%-- PM Option --%>
        <option
          ${sections.hasSelected && sections.selected.startTime.pastNoon ? "selected" : ""}
          value="false"
        >
          PM
        </option>
      </select>
    </div>
    <%-- Group of Section Meeting End Time Inputs --%>
    <label for="end-time" class="form-label">End Time</label>
    <div class="mb-3 input-group" id="end-time">
      <%-- End Time Hour Dropdown --%>
      <select class="form-select" id="end-time-hours-input" name="endTimeHours" required>
        <%-- Default Option --%>
        <option ${!sections.hasSelected ? "selected" : ""} value="-1">Select Hours</option>
        <%-- Hours in 1 Hour Increments --%>
        <c:forEach begin="1" end="12" varStatus="loop">
          <option
            ${sections.hasSelected && loop.index == sections.selected.endTime.hours ? "selected" : ""}
            value="${loop.index}"
          >
            ${loop.index}
          </option>
        </c:forEach>
      </select>
      <%-- End Time Minute Dropdown --%>
      <select class="form-select" id="end-time-minutes-input" name="endTimeMinutes" required>
        <%-- Default Option --%>
        <option ${!sections.hasSelected ? "selected" : ""} value="-1">Select Minutes</option>
        <%-- Minutes in 5 Minute Increments --%>
        <c:forEach begin="0" end="11" varStatus="loop">
          <option
            ${sections.hasSelected && loop.index*5 == sections.selected.endTime.minutes ? "selected" : ""}
            value="${loop.index*5}"
          >
            ${loop.index*5}
          </option>
        </c:forEach>
      </select>
      <%-- AM/PM Dropdown --%>
      <select class="form-select" id="end-time-meridian-input" name="endTimeMeridian" required>
        <%-- Default Option --%>
        <option ${!sections.hasSelected ? "selected" : ""} value="-1">Select AM/PM</option>
        <%-- AM Option --%>
        <option
          ${sections.hasSelected && !sections.selected.endTime.pastNoon ? "selected" : ""}
          value="false"
        >
          AM
        </option>
        <%-- PM Option --%>
        <option
          ${sections.hasSelected && sections.selected.endTime.pastNoon ? "selected" : ""}
          value="false"
        >
          PM
        </option>
      </select>
    </div>
    <%-- Form Submit Buttons --%>
    <div class="d-flex justify-content-center">
      <button type="submit" name="action" value="add" class="btn btn-primary me-1 d-flex">Add</button>
      <%-- Only show update/delete buttons if a section is selected --%>
      <c:if test="${sections.hasSelected}">
        <button type="submit" name="action" value="update" class="btn btn-primary mx-1 d-flex">Update</button>
        <button type="submit" name="action" value="delete" class="btn btn-danger ms-1 d-flex">Delete</button>
      </c:if>
    </div>
  </form>
</div>