package com.example.magda.systeminformacyjny.models;

/**
 * Created by piotrek on 04.06.17.
 */

public class NearPlace {

    private Long id;
    private Place place;

    public NearPlace(Long id, Place place) {
        this.id = id;
        this.place = place;
    }

    public NearPlace(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof NearPlace))
            return false;
        NearPlace other = (NearPlace) obj;
        return id == null ? other.id == null : id.equals(other.id);
    }

    @Override
    public int hashCode() {
        int result = 117;
        result = 37 * result + id.hashCode();
        return result;
    }
}
