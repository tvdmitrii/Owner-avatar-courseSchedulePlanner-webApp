package com.turygin.states;

import com.turygin.api.model.CourseBasicDTO;
import com.turygin.api.model.DepartmentBasicDTO;

import java.util.ArrayList;
import java.util.List;

public class BrowseCoursesPageState {

    private List<CourseBasicDTO> loadedCourses = new ArrayList<>();

    private List<DepartmentBasicDTO> loadedDepartments = new ArrayList<>();

    private DepartmentBasicDTO selectedDepartment;

    private CourseBasicDTO selectedCourse;

    private String titleSearchTerm;

    public boolean getHasLoadedCourses() {
        return loadedCourses != null && !loadedCourses.isEmpty();
    }

    public boolean getHasLoadedDepartments() {
        return loadedDepartments != null && !loadedDepartments.isEmpty();
    }

    public boolean getHasSelectedCourse() {
        return selectedCourse != null;
    }

    public boolean getHasSelectedDepartment() {
        return selectedDepartment != null;
    }

    public boolean getHasTitleSearchTerm() {
        return titleSearchTerm != null && !titleSearchTerm.isEmpty();
    }

    public List<CourseBasicDTO> getLoadedCourses() {
        return loadedCourses;
    }

    public void setLoadedCourses(List<CourseBasicDTO> loadedCourses) {
        this.loadedCourses = loadedCourses;
    }

    public CourseBasicDTO getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(CourseBasicDTO selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public String getTitleSearchTerm() {
        return titleSearchTerm;
    }

    public void setTitleSearchTerm(String titleSearchTerm) {
        this.titleSearchTerm = titleSearchTerm;
    }

    public List<DepartmentBasicDTO> getLoadedDepartments() {
        return loadedDepartments;
    }

    public void setLoadedDepartments(List<DepartmentBasicDTO> loadedDepartments) {
        this.loadedDepartments = loadedDepartments;
    }

    public DepartmentBasicDTO getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(DepartmentBasicDTO selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public long getSelectedDepartmentId() {
        return selectedDepartment == null ? -1 : selectedDepartment.getId();
    }
}
