package com.incar.entity;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2018/12/20 11:47
 */
public class AGps {
    private String gprscode;
    private Double lng;
    private Double lat;

    public String getGprscode() {
        return gprscode;
    }

    public void setGprscode(String gprscode) {
        this.gprscode = gprscode;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
