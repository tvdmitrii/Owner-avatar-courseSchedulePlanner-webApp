package com.turygin.states;

import com.turygin.api.model.CourseBasicDTO;

import java.util.ArrayList;
import java.util.List;

public class BrowseCoursesPageState {

    List<CourseBasicDTO> loadedCourses = new ArrayList<>();

    CourseBasicDTO selectedCourse;

    String titleSearchTerm;

    public boolean getHasLoadedCourses() {
        return loadedCourses != null && !loadedCourses.isEmpty();
    }

    public boolean getHasSelectedCourse() {
        return selectedCourse != null;
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
}
