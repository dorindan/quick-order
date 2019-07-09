package ro.quickorder.backend.model.dto;

import java.time.LocalTime;

public class ProgramDto {

    private String numeRestaurant;

    private LocalTime startProgramTime;

    public String getNumeRestaurant() {
        return numeRestaurant;
    }

    public void setNumeRestaurant(String numeRestaurant) {
        this.numeRestaurant = numeRestaurant;
    }

    public LocalTime getStartProgramTime() {
        return startProgramTime;
    }

    public ProgramDto() {
    }

    public ProgramDto(LocalTime startProgramTime, LocalTime endProgramTime) {
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
