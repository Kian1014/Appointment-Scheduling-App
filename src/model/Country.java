package model;

/**
 * Model class of country based on table object country from database.
 * Contains integer field for country ID.
 * Contains string field for country Name.
 * Has getters/setters for all fields.
 */

public class Country {

    private int countryID;
    private String countryName;


    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


}
