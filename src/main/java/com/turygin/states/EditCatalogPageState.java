package com.turygin.states;

import com.turygin.api.model.InstructorDTO;
import com.turygin.api.model.SectionDTO;

import java.util.List;

/**
 * Stores the state of the edit catalog page.
 */
public class EditCatalogPageState extends BrowseCoursesPageState {

    /** List of available sections for selected course. */
    protected final SelectableList<SectionDTO> sections = new SelectableList<>();

    /** List of available instructors. */
    protected final SelectableList<InstructorDTO> instructors = new SelectableList<>();

    /** True if in course editing mode, false if in section editing mode. */
    protected boolean isCourseMode = true;

    /**
     * Gets instructors.
     * @return instructors selectable list object
     */
    public SelectableList<InstructorDTO> getInstructors() {
        return instructors;
    }

    /**
     * Gets sections.
     * @return sections selectable list object
     */
    public SelectableList<SectionDTO> getSections() {
        return sections;
    }

    /**
     * Sets instructors.
     * @param instructors the instructors
     */
    public void setInstructors(List<InstructorDTO> instructors) {
        this.instructors.setItems(instructors);
    }

    /**
     * Sets sections.
     * @param sections the sections
     */
    public void setSections(List<SectionDTO> sections) {
        this.sections.setItems(sections);
    }

    /**
     * Checks whether in course or section editing mode.
     * @return true if in course editing mode, false if in section editing mode
     */
    public boolean getIsCourseMode() {
        return isCourseMode;
    }

    /**
     * Sets course or section editing mode.
     * @param isCourseMode true if in course editing mode, false if in section editing mode
     */
    public void setIsCourseMode(boolean isCourseMode) {
        this.isCourseMode = isCourseMode;
    }
}
