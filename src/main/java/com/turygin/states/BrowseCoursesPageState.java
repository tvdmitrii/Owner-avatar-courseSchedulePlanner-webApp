package com.turygin.states;

import com.turygin.api.model.CourseBasicDTO;
import com.turygin.api.model.CourseWithSectionsDTO;
import com.turygin.api.model.DepartmentBasicDTO;

import java.util.ArrayList;
import java.util.List;

public class BrowseCoursesPageState {

    private LoadedList<CourseBasicDTO> courses = new LoadedList<>();
    private LoadedList<DepartmentBasicDTO> departments = new LoadedList<>();

    private String titleSearchTerm;

    public boolean getHasTitleSearchTerm() {
        return titleSearchTerm != null && !titleSearchTerm.isEmpty();
    }

    public String getTitleSearchTerm() {
        return titleSearchTerm;
    }

    public void setTitleSearchTerm(String titleSearchTerm) {
        this.titleSearchTerm = titleSearchTerm;
    }

    public LoadedList<CourseBasicDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseBasicDTO> courses) {
        this.courses.setItems(courses);
    }

    public LoadedList<DepartmentBasicDTO> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentBasicDTO> departments) {
        this.departments.setItems(departments);
    }
}
