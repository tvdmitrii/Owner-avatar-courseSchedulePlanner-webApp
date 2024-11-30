package com.turygin.states;

import com.turygin.api.model.ScheduleDTO;

import java.util.List;

/**
 * Stores the state of the schedule page.
 */
public class SchedulePageState {

    /** List of available schedules. */
    protected final SelectableList<ScheduleDTO> schedules = new SelectableList<>();

    /**
     * Gets schedules.
     * @return schedules selectable list object
     */
    public SelectableList<ScheduleDTO> getSchedules() {
        return schedules;
    }

    /**
     * Sets schedules.
     * @param schedules list of schedule DTOs
     */
    public void setSchedules(List<ScheduleDTO> schedules) {
        this.schedules.setItems(schedules);
    }
}
