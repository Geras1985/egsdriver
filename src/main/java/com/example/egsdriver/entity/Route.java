package com.example.egsdriver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;

@Entity
@Table(name = "routes")
public class Route extends BaseEntity<Long> {

    @Column(name="starting_point")
    private String startingPoint;
    @Column(name="end_point")
    private String endPoint;
    @Column(name="start_time")
    private LocalDate startTime;
    @Column(name="commend")
    private String commend;
    @Column(name="free_sits")
    private int freeSits;
    @Column(name="trip_description")
    private String tripDescription;
    @Column(name="car_description")
    private String carDescription;
    @Column(name="schedule")
    private LocalDate schedule;
    @Column(name="active")
    private boolean active;
    @Column(name="time")
    private String time;


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

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
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

    public LocalDate getSchedule() {
        return schedule;
    }

    public void setSchedule(LocalDate schedule) {
        this.schedule = schedule;
    }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Route{" +
                "startingPoint='" + startingPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", startTime=" + startTime +
                ", commend='" + commend + '\'' +
                ", freeSits=" + freeSits +
                ", tripDescription='" + tripDescription + '\'' +
                ", carDescription='" + carDescription + '\'' +
                ", schedule=" + schedule +
                ", active=" + active +
                '}';
    }
}
