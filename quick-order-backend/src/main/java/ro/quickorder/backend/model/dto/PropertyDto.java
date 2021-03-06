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

    public PropertyDto(String restaurantName, LocalTime startProgramTime, LocalTime endProgramTime, String streetName, Double latitude, Double longitude, String email) {
        this.restaurantName = restaurantName;
        this.startProgramTime = startProgramTime;
        this.endProgramTime = endProgramTime;
        this.streetName = streetName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
    }

    public PropertyDto() {
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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
                "restaurantName='" + restaurantName + '\'' +
                ", startProgramTime=" + startProgramTime +
                ", endProgramTime=" + endProgramTime +
                ", streetName='" + streetName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", email='" + email + '\'' +
                '}';
    }

    public static final class Builder {
        private String restaurantName;

        private LocalTime startProgramTime;

        private LocalTime endProgramTime;

        private String streetName;

        private Double latitude;

        private Double longitude;

        private String email;

        public PropertyDto.Builder withRestaurantName(String restaurantName) {
            this.restaurantName = restaurantName;
            return this;
        }

        public PropertyDto.Builder withStartProgramTime(LocalTime startProgramTime) {
            this.startProgramTime = startProgramTime;
            return this;
        }

        public PropertyDto.Builder withEndProgramTime(LocalTime endProgramTime) {
            this.endProgramTime = endProgramTime;
            return this;
        }

        public PropertyDto.Builder withStreetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        public PropertyDto.Builder withLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public PropertyDto.Builder withLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public PropertyDto.Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public PropertyDto build() {
            return new PropertyDto(restaurantName, startProgramTime, endProgramTime, streetName, latitude, longitude, email);
        }
    }
}
