package com.turygin.states;

import com.turygin.api.model.CourseWithSectionsDTO;
import com.turygin.api.model.SectionDTO;

import java.util.ArrayList;
import java.util.List;

public class ViewCartPageState {
    private List<CourseWithSectionsDTO> loadedCourses = new ArrayList<>();

    private CourseWithSectionsDTO selectedCourse;

    public boolean getHasLoadedCourses() {
        return loadedCourses != null && !loadedCourses.isEmpty();
    }

    public boolean getHasSelectedCourse() {
        return selectedCourse != null;
    }

    public boolean getHasSections() {
        return getHasSelectedCourse() &&
                selectedCourse.getSections() != null && !selectedCourse.getSections().isEmpty();
    }

    public List<SectionDTO> getSections() {
        return selectedCourse.getSections();
    }

    public List<CourseWithSectionsDTO> getLoadedCourses() {
        return loadedCourses;
    }

    public void setLoadedCourses(List<CourseWithSectionsDTO> loadedCourses) {
        this.loadedCourses = loadedCourses;
    }

    public CourseWithSectionsDTO getSelectedCourse() {
        return selectedCourse;
    }

    public void setSelectedCourse(CourseWithSectionsDTO selectedCourse) {
        this.selectedCourse = selectedCourse;
    }
}
