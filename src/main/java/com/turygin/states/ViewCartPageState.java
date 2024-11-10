package com.turygin.states;

import com.turygin.api.model.CourseWithSectionsDTO;
import com.turygin.api.model.SectionDTO;

import java.util.List;
import java.util.SortedMap;

public class ViewCartPageState {

    private LoadedList<CourseWithSectionsDTO> courses = new LoadedList<>();

    public boolean getHasSections() {
        return courses.getHasItems() &&
                courses.getHasSelected() &&
                courses.getSelected().getSections() != null &&
                !courses.getSelected().getSections().isEmpty();
    }

    public SortedMap<Long,SectionDTO> getSections() {
        if (!getHasSections()) {
            return null;
        }
        return courses.getSelected().getSections();
    }

    public LoadedList<CourseWithSectionsDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseWithSectionsDTO> courses) {
        this.courses.setItems(courses);
    }
}
