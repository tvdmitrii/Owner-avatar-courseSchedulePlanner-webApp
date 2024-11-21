<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<h5 class="card-header">Section Editor</h5>
<div class="card-body">
  <form action="${pageContext.request.contextPath}/admin/section/edit" method="POST">
    <label for="weekDays" class="form-label">Meeting Days</label>
    <div id="weekDays" class="mb-3">
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="monday" name="weekDays"
          ${sections.hasSelected && sections.selected.daysOfWeek.daysOfWeek[0] ? "checked" : ""} value="0">
        <label class="form-check-label" for="monday">Monday</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="tuesday" name="weekDays"
          ${sections.hasSelected && sections.selected.daysOfWeek.daysOfWeek[1] ? "checked" : ""} value="1">
        <label class="form-check-label" for="tuesday">Tuesday</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="wednesday" name="weekDays"
          ${sections.hasSelected && sections.selected.daysOfWeek.daysOfWeek[2] ? "checked" : ""} value="2">
        <label class="form-check-label" for="wednesday">Wednesday</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="thursday" name="weekDays"
          ${sections.hasSelected && sections.selected.daysOfWeek.daysOfWeek[3] ? "checked" : ""} value="3">
        <label class="form-check-label" for="thursday">Thursday</label>
      </div>
      <div class="form-check form-check-inline">
        <input class="form-check-input" type="checkbox" id="friday" name="weekDays"
          ${sections.hasSelected && sections.selected.daysOfWeek.daysOfWeek[0] ? "checked" : ""} value="4">
        <label class="form-check-label" for="friday">Friday</label>
      </div>
    </div>
    <div class="mb-3">
      <label for="instructor-input" class="form-label">Instructor</label>
      <select class="form-select" id="instructor-input" name="instructorListId" required>
        <option ${!sections.hasSelected ? "selected" : ""} value="-1">Select Instructor</option>
        <c:forEach var="instructor" items="${instructors.items}" varStatus="loop">
          <option ${sections.hasSelected
                  && instructor.id == sections.selected.instructor.id ? "selected" : ""}
                  value="${loop.index}">${instructor.name}</option>
        </c:forEach>
      </select>
    </div>
    <label for="start-time" class="form-label">Start Time</label>
    <div class="mb-3 input-group" id="start-time">
      <select class="form-select" id="start-time-hours-input" name="startTimeHours" required>
        <option ${!sections.hasSelected ? "selected" : ""} value="-1">Select Hours</option>
        <c:forEach begin="1" end="12" varStatus="loop">
          <option ${sections.hasSelected
                  && loop.index == sections.selected.startTime.hours ? "selected" : ""}
                  value="${loop.index}">${loop.index}</option>
        </c:forEach>
      </select>
      <select class="form-select" id="start-time-minutes-input" name="startTimeMinutes" required>
        <option ${!sections.hasSelected ? "selected" : ""} value="-1">Select Minutes</option>
        <c:forEach begin="0" end="11" varStatus="loop">
          <option ${sections.hasSelected
                  && loop.index*5 == sections.selected.startTime.minutes ? "selected" : ""}
                  value="${loop.index*5}">${loop.index*5}</option>
        </c:forEach>
      </select>
      <select class="form-select" id="start-time-meridian-input" name="startTimeMeridian" required>
        <option ${!sections.hasSelected ? "selected" : ""} value="-1">Select AM/PM</option>
        <option ${sections.hasSelected && !sections.selected.startTime.pastNoon ? "selected" : ""}
                value="false">AM</option>
        <option ${sections.hasSelected && sections.selected.startTime.pastNoon ? "selected" : ""}
                value="false">PM</option>
      </select>
    </div>
    <label for="end-time" class="form-label">End Time</label>
    <div class="mb-3 input-group" id="end-time">
      <select class="form-select" id="end-time-hours-input" name="endTimeHours" required>
        <option ${!sections.hasSelected ? "selected" : ""} value="-1">Select Hours</option>
        <c:forEach begin="1" end="12" varStatus="loop">
          <option ${sections.hasSelected
                  && loop.index == sections.selected.endTime.hours ? "selected" : ""}
                  value="${loop.index}">${loop.index}</option>
        </c:forEach>
      </select>
      <select class="form-select" id="end-time-minutes-input" name="endTimeMinutes" required>
        <option ${!sections.hasSelected ? "selected" : ""} value="-1">Select Minutes</option>
        <c:forEach begin="0" end="11" varStatus="loop">
          <option ${sections.hasSelected
                  && loop.index*5 == sections.selected.endTime.minutes ? "selected" : ""}
                  value="${loop.index*5}">${loop.index*5}</option>
        </c:forEach>
      </select>
      <select class="form-select" id="end-time-meridian-input" name="endTimeMeridian" required>
        <option ${!sections.hasSelected ? "selected" : ""} value="-1">Select AM/PM</option>
        <option ${sections.hasSelected && !sections.selected.endTime.pastNoon ? "selected" : ""}
                value="false">AM</option>
        <option ${sections.hasSelected && sections.selected.endTime.pastNoon ? "selected" : ""}
                value="false">PM</option>
      </select>
    </div>

    <div class="d-flex justify-content-center">
      <button type="submit" name="action" value="add" class="btn btn-primary me-1 d-flex">Add New Section</button>
      <c:if test="${sections.hasSelected}">
        <button type="submit" name="action" value="update" class="btn btn-primary mx-1 d-flex">Update Selected</button>
        <button type="submit" name="action" value="delete" class="btn btn-danger ms-1 d-flex">Delete Selected</button>
      </c:if>
    </div>
  </form>
</div>