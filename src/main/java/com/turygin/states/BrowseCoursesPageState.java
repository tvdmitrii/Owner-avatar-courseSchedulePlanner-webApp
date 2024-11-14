package com.turygin.states;

import com.turygin.api.model.CourseDTO;
import com.turygin.api.model.DepartmentDTO;

import java.util.List;

/**
 * Stores the state of the course browser page.
 */
public class BrowseCoursesPageState {

    /** List of available courses. */
    private final SelectableList<CourseDTO> courses = new SelectableList<>();

    /** List of available departments. */
    private final SelectableList<DepartmentDTO> departments = new SelectableList<>();

    /** Current title search term for course search. */
    private String titleSearchTerm;

    /**
     * Checks whether title search term is set.
     * @return true if search term is set, false otherwise
     */
    public boolean getHasTitleSearchTerm() {
        return titleSearchTerm != null && !titleSearchTerm.isEmpty();
    }

    /**
     * Gets title search term.
     * @return the title search term
     */
    public String getTitleSearchTerm() {
        return titleSearchTerm;
    }

    /**
     * Sets title search term.
     * @param titleSearchTerm the title search term
     */
    public void setTitleSearchTerm(String titleSearchTerm) {
        this.titleSearchTerm = titleSearchTerm;
    }

    /**
     * Gets courses.
     * @return the courses
     */
    public SelectableList<CourseDTO> getCourses() {
        return courses;
    }

    /**
     * Sets courses.
     * @param courses the courses
     */
    public void setCourses(List<CourseDTO> courses) {
        this.courses.setItems(courses);
    }

    /**
     * Gets departments.
     * @return the departments
     */
    public SelectableList<DepartmentDTO> getDepartments() {
        return departments;
    }

    /**
     * Sets departments.
     * @param departments the departments
     */
    public void setDepartments(List<DepartmentDTO> departments) {
        this.departments.setItems(departments);
    }
}
