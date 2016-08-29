package com.seppia.android.project_seppia.dto;

import java.util.List;

/**
 * Created by zhanggd on 29.08.16.
 */
public class FetchLocationsDto {

    private List<LocationDto>locations;

    public List<LocationDto> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationDto> locations) {
        this.locations = locations;
    }
}
