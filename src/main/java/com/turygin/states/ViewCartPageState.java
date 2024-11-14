package com.turygin.states;

import com.turygin.api.model.CourseWithSectionsDTO;
import com.turygin.api.model.SectionDTO;

import java.util.List;
import java.util.SortedMap;

/**
 * Stores the state of the user cart page.
 */
public class ViewCartPageState {

    /** List of courses in users cart that also contain selected section information. */
    private final SelectableList<CourseWithSectionsDTO> courses = new SelectableList<>();

    /**
     * Check whether the selected course in user's cart has any sections.
     * @return true if selected course has sections, false otherwise
     */
    public boolean getHasSections() {
        return courses.getHasItems() &&
                courses.getHasSelected() &&
                courses.getSelected().getSections() != null &&
                !courses.getSelected().getSections().isEmpty();
    }

    /**
     * Gets a sorted map of sections of the selected course with ID as the key and section DTO as the value.
     * @return the sections
     */
    public SortedMap<Long,SectionDTO> getSections() {
        if (!getHasSections()) {
            return null;
        }
        return courses.getSelected().getSections();
    }

    /**
     * Gets courses.
     * @return the courses
     */
    public SelectableList<CourseWithSectionsDTO> getCourses() {
        return courses;
    }

    /**
     * Sets courses.
     * @param courses the courses
     */
    public void setCourses(List<CourseWithSectionsDTO> courses) {
        this.courses.setItems(courses);
    }
}
