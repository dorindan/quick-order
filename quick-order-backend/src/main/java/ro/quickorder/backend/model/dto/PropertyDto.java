package ro.quickorder.backend.model.dto;

import java.time.LocalTime;

public class PropertyDto {

    private String restaurantName;

    private LocalTime startProgramTime;

    private LocalTime endProgramTime;

    private String streetName;

    private Double latitude;

    private Double longitude;

    private String email;

    public PropertyDto(String numeRestaurant, LocalTime startProgramTime, LocalTime endProgramTime, String streetName, Double latitude, Double longitude, String email) {
        this.restaurantName = numeRestaurant;
        this.startProgramTime = startProgramTime;
        this.endProgramTime = endProgramTime;
        this.streetName = streetName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
    }

    public PropertyDto() {
    }

    public String getNumeRestaurant() {
        return restaurantName;
    }

    public void setNumeRestaurant(String numeRestaurant) {
        this.restaurantName = numeRestaurant;
    }

    public LocalTime getStartProgramTime() {
        return startProgramTime;
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

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PropertyDto{" +
                "numeRestaurant='" + restaurantName + '\'' +
                ", startProgramTime=" + startProgramTime +
                ", endProgramTime=" + endProgramTime +
                ", streetName='" + streetName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", email='" + email + '\'' +
                '}';
    }
}
