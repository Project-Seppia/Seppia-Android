package com.seppia.android.project_seppia.dto;

/**
 * Created by zhanggd on 29.08.16.
 */
public class LocationDto {
    //"address": "3151 Edison Way, Redwood City",
    //"phone": "123456789",
    //"name": "SportsHouse",
    //"geometry": {
    //"lat": 37.476493,
    //    "lng": -122.2031432
    //},
    //"photo": {
    //"height": 1530,
    //    "width": 2048,
    //    "photoReference": "CoQBcwAAANjjBvLAv_QK37UvN1jwtFpC6qBSpHKBd1wgj83EqIsIxMBJw--TRoINFqr0fCiVS17DqO9cZvu632_Q5Mia6E5IwFE8hkRn8GGUXWfOOvm65WS_uc45jl2oWWvEMKKUaLSxsPBHlAekNQ9CqW1s99prtjkQLLK5ce3rqOc7gReuEhDvXqi5qbgrrOX1bjqRGclPGhQ9eoCVcGBpG_C4XaJNvyktn2aRuw"
    //},
    //"rating": 4.300000190734863,
    //"openingTimeInSevenDays": []
    private String address;
    private String phone;
    private String name;
    private GeometryDto geometry;
    private PhotoDto photo;
    private double rating;
    private String openingTimeInSevenDays[];

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeometryDto getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryDto geometry) {
        this.geometry = geometry;
    }

    public PhotoDto getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoDto photo) {
        this.photo = photo;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String[] getOpeningTimeInSevenDays() {
        return openingTimeInSevenDays;
    }

    public void setOpeningTimeInSevenDays(String[] openingTimeInSevenDays) {
        this.openingTimeInSevenDays = openingTimeInSevenDays;
    }
}
