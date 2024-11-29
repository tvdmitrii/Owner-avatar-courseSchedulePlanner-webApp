package com.turygin.states;

import com.turygin.api.model.ScheduleDTO;

import java.util.List;


public class SchedulePageState {

    protected final SelectableList<ScheduleDTO> schedules = new SelectableList<>();

    public SelectableList<ScheduleDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleDTO> schedules) {
        this.schedules.setItems(schedules);
    }
}
