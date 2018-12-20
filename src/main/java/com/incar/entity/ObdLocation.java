package com.incar.entity;

import java.sql.Timestamp;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2018/12/20 11:23
 */
public class ObdLocation {
    private String obdCode;
    private Integer tripId;
    private String vid;
    private String vin;
    private Integer locationSpeed;
    private Integer travelDistance;
    private String longitude;
    private String latitude;
    private Double direction;
    private Timestamp locationTime;
    private Integer locationType;
    private Timestamp recordTime;

    public String getObdCode() {
        return obdCode;
    }

    public void setObdCode(String obdCode) {
        this.obdCode = obdCode;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getLocationSpeed() {
        return locationSpeed;
    }

    public void setLocationSpeed(Integer locationSpeed) {
        this.locationSpeed = locationSpeed;
    }

    public Integer getTravelDistance() {
        return travelDistance;
    }

    public void setTravelDistance(Integer travelDistance) {
        this.travelDistance = travelDistance;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Double getDirection() {
        return direction;
    }

    public void setDirection(Double direction) {
        this.direction = direction;
    }

    public Integer getLocationType() {
        return locationType;
    }

    public void setLocationType(Integer locationType) {
        this.locationType = locationType;
    }

    public Timestamp getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(Timestamp locationTime) {
        this.locationTime = locationTime;
    }

    public Timestamp getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Timestamp recordTime) {
        this.recordTime = recordTime;
    }
}
