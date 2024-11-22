package com.turygin.states;

import com.turygin.api.model.InstructorDTO;
import com.turygin.api.model.SectionDTO;

import java.util.List;

public class EditCatalogPageState extends BrowseCoursesPageState {
    /** List of available sections for selected course. */
    protected final SelectableList<SectionDTO> sections = new SelectableList<>();

    /** List of available instructors. */
    protected final SelectableList<InstructorDTO> instructors = new SelectableList<>();

    protected boolean isCourseMode = true;

    public SelectableList<InstructorDTO> getInstructors() {
        return instructors;
    }

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

    public boolean getIsCourseMode() {
        return isCourseMode;
    }

    public void setIsCourseMode(boolean isCourseMode) {
        this.isCourseMode = isCourseMode;
    }
}
