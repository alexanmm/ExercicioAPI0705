
package br.com.digitalhouse.exercicioapi0705.model;

import com.google.gson.annotations.Expose;

public class Location {

    @Expose
    private String city;
    @Expose
    private Coordinates coordinates;
    @Expose
    private String postcode;
    @Expose
    private String state;
    @Expose
    private String street;
    @Expose
    private Timezone timezone;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Timezone getTimezone() {
        return timezone;
    }

    public void setTimezone(Timezone timezone) {
        this.timezone = timezone;
    }

}
