package ro.quickorder.backend.model.dto;

import java.time.LocalTime;

public class PropertyDto {

    private LocalTime startProgramTime;

    public LocalTime getStartProgramTime() {
        return startProgramTime;
    }

    public PropertyDto() {
    }

    public PropertyDto(LocalTime startProgramTime, LocalTime endProgramTime) {
        this.startProgramTime = startProgramTime;
        this.endProgramTime = endProgramTime;
    }

    public void setStartProgramTime(LocalTime startProgramTime) {
        this.startProgramTime = startProgramTime;
    }

    public LocalTime getEndProgramTime() {
        return endProgramTime;
    }

    public void setEndProgramTime(LocalTime endProgramTime) {
        this.endProgramTime = endProgramTime;
    }

    private LocalTime endProgramTime;
}
