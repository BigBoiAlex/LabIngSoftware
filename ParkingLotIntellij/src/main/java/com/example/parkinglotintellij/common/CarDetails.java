package com.example.parkinglotintellij.common;

public class CarDetails implements java.io.Serializable {

    private Integer id;
    private String licensePlate;
    private String parkingSpot;
    private String username;

    public CarDetails(Integer id, String licensePlate, String parkingSpot, String username) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.parkingSpot = parkingSpot;
        this.username = username;
    }



    public Integer getId() {
        return id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getParkingSpot() {
        return parkingSpot;
    }

    public String getUsername() {
        return username;
    }




}