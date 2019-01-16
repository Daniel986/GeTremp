package com.getremp.daniel_lael.getremp.TrempSelect;

public class RegularTremp {

    private String driver;
    private String destination;
    private String origin;
    private String day;
    private String hour;
    private String[] participants;
    private String driverImage;
    private int totalSeats;
    private int occupiedSeats;

    public RegularTremp() {
    }

    public RegularTremp(String driver, String destination, String origin, String day, String hour, String[] participants, String driverImage, int totalSeats) {
        this.driver = driver;
        this.destination = destination;
        this.origin = origin;
        this.day = day;
        this.hour = hour;
        this.participants = participants;
        this.driverImage = driverImage;
        this.totalSeats = totalSeats;
        this.occupiedSeats = participants.length;
    }

    public RegularTremp(String driver) {
        this.driver = driver;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(int occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }


    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String[] getParticipants() {
        return participants;
    }

    public void setParticipants(String[] participants) {
        this.participants = participants;
    }

    public String getDriverImage() {
        return driverImage;
    }

    public void setDriverImage(String driverImage) {
        this.driverImage = driverImage;
    }

    public void refreshSeats(){
        this.occupiedSeats = participants.length;
    }

}
