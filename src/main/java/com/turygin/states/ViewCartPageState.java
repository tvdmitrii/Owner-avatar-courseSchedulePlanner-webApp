package com.turygin.states;

import com.turygin.api.model.CourseWithSectionsDTO;
import com.turygin.api.model.SectionDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class ViewCartPageState {
    private List<CourseWithSectionsDTO> loadedCourses = new ArrayList<>();

    private int selectedCourseId = -1;

    public boolean getHasLoadedCourses() {
        return loadedCourses != null && !loadedCourses.isEmpty();
    }

    public boolean getHasSelectedCourse() {
        return selectedCourseId != -1;
    }

    public boolean getHasSections() {
        return getHasSelectedCourse() &&
                getSelectedCourse().getSections() != null && !getSelectedCourse().getSections().isEmpty();
    }

    public SortedMap<Long,SectionDTO> getSections() {
        if (!getHasLoadedCourses() || !getHasSelectedCourse()) {
            return null;
        }
        return getSelectedCourse().getSections();
    }

    public List<CourseWithSectionsDTO> getLoadedCourses() {
        return loadedCourses;
    }

    public void setLoadedCourses(List<CourseWithSectionsDTO> loadedCourses) {
        this.loadedCourses = loadedCourses;
    }

    public CourseWithSectionsDTO getSelectedCourse() {
        if (!getHasLoadedCourses() || !getHasSelectedCourse()) {
            return null;
        }
        return this.loadedCourses.get(selectedCourseId);
    }

    public void updateSelectedCourse(CourseWithSectionsDTO newCourse) {
        if (!getHasLoadedCourses() || !getHasSelectedCourse()) {
            return;
        }
        this.loadedCourses.set(selectedCourseId, newCourse);
    }

    public void setSelectedCourseId(int selectedCourseId) {
        this.selectedCourseId = selectedCourseId;
    }

    public int getSelectedCourseId() {
        return selectedCourseId;
    }

    public void removeSelectedCourse() {
        if (!getHasLoadedCourses() || !getHasSelectedCourse()) {
            return;
        }
        this.loadedCourses.remove(selectedCourseId);
        selectedCourseId = -1;
    }
}
