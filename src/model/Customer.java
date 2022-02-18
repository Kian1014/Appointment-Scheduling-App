package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Model class for customer based on table object customer from database.
 * Contains string fields for customer name, address, postal code, phone number, created by, last updated by, customer division, and customer country.
 * Contains timestamp fields for time/date created and last updated.
 * Contains integer fields for customer ID and Division ID.
 * Has getters and setters for all fields.
 */

public class Customer {

    int cusID;
    String cusName;
    String cusAddress;
    String postalCode;
    String phoneNum;
    Timestamp createDate;
    String createdBy;
    Timestamp lastUpdate;
    String lastUpdatedBy;
    int divisionID;
    String cusDivision;
    String cusCountry;

    public Customer(int cusID, String cusName, String cusAddress, String postalCode, String phoneNum, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionID, String cusDivision, String cusCountry) {
        this.cusID = cusID;
        this.cusName = cusName;
        this.cusAddress = cusAddress;
        this.postalCode = postalCode;
        this.phoneNum = phoneNum;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
        this.cusDivision = cusDivision;
        this.cusCountry = cusCountry;
    }

    public Customer(Customer customer) {
        this.cusID = customer.getCusID();
        this.cusName = customer.getCusName();
        this.cusAddress = customer.getCusAddress();
        this.postalCode = customer.getPostalCode();
        this.phoneNum = customer.getPhoneNum();
        this.createDate = customer.getCreateDate();
        this.createdBy = customer.getCreatedBy();
        this.lastUpdate = customer.getLastUpdate();
        this.lastUpdatedBy = customer.getLastUpdatedBy();
        this.divisionID = customer.getDivisionID();
        this.cusDivision = customer.getCusDivision();
        this.cusCountry = customer.getCusCountry();
    }

    public Customer(String cusName, String cusAddress, String postalCode, String phoneNum, int divisionID) {
        this.cusName = cusName;
        this.cusAddress = cusAddress;
        this.postalCode = postalCode;
        this.phoneNum = phoneNum;
        this.divisionID = divisionID;
    }

    public int getCusID() {
        return cusID;
    }

    public void setCusID(int cusID) {
        this.cusID = cusID;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    public String getCusDivision() {
        return cusDivision;
    }

    public void setCusDivision(String cusDivision) {
        this.cusDivision = cusDivision;
    }

    public String getCusCountry() {
        return cusCountry;
    }

    public void setCusCountry(String cusCountry) {
        this.cusCountry = cusCountry;
    }
}
