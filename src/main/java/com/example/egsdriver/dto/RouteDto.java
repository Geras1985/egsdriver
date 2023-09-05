package com.example.egsdriver.dto;


import com.example.egsdriver.entity.BaseEntity;

public class RouteDto extends BaseEntity<Long> {

    private String startingPoint;
    private String endPoint;
    private String startTime;
    private String commend;
    private int freeSits;
    private String tripDescription;
    private String carDescription;
    private String schedule;
    private boolean active;
    private String time;

    public RouteDto() {
    }

    public RouteDto(Long id, String name, String phone, String driversTeamsContact, String startingPoint,
                    String endPoint, String startTime, String commend, int freeSits, String tripDescription,
                    String carDescription, String schedule, boolean active, String time) {
        super(id, name, phone, driversTeamsContact);
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
        this.startTime = startTime;
        this.commend = commend;
        this.freeSits = freeSits;
        this.tripDescription = tripDescription;
        this.carDescription = carDescription;
        this.schedule = schedule;
        this.active = active;
        this.time = time;
    }



    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getCommend() {
        return commend;
    }

    public void setCommend(String commend) {
        this.commend = commend;
    }

    public int getFreeSits() {
        return freeSits;
    }

    public void setFreeSits(int freeSits) {
        this.freeSits = freeSits;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public void setTripDescription(String tripDescription) {
        this.tripDescription = tripDescription;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "RouteDto{" +
                "startingPoint='" + startingPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", startTime='" + startTime + '\'' +
                ", commend='" + commend + '\'' +
                ", freeSits=" + freeSits +
                ", tripDescription='" + tripDescription + '\'' +
                ", carDescription='" + carDescription + '\'' +
                ", schedule='" + schedule + '\'' +
                ", active=" + active +
                ", time='" + time + '\'' +
                '}';
    }
}
