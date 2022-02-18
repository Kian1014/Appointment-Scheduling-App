package helpers;

import daoInterfaces.*;
import interfaces.CheckNum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper class that has methods to retrieve info from model classes based on some criteria.
 */

public class Search {

    /**
     * Gets a country from a country list based on given ID.
     * discussion of lambda - use of lambda expression simplified code and abstracted boolean check further.
     * @param allCountries List of countries to be searched,
     * @param divisionID ID to be used to pull country from list.
     * @return Returns a Country Object.
     */

    public static Country getCountryByID(List<Country> allCountries, int divisionID) {

        CheckNum checkID = (id, otherId) -> id == otherId;
        Country c = null;

        int countryID = getCountryIdByDivisionID(divisionID);

        for (Country country : allCountries) {
            if (checkID.checkNum(countryID,country.getCountryID())) {
                c = new Country(country.getCountryID(), country.getCountryName());
            }
        }

        return c;

    }

    /**
     * Pulls a Division from a given list of divisions and a division id.
     * @param allDivisions A list of divisions.
     * @param divisionID The ID of division to be gotten.
     * @return Returns a Division.
     */

    public static Division getDivisionByID(List<Division> allDivisions, int divisionID) {

        Division d = null;

        for (Division division : allDivisions) {
            if (division.getDivisionID() == divisionID) {
                d = new Division(division.getDivisionID(), division.getDivisionName(), division.getCountryID());
            }
        }

        return d;

    }

    /**
     * Gets a country ID from a list of all divisions based on given division id.
     * @param divisionID Division ID to be used as search criteria on a list.
     * @return Returns an integer of the divisions Country ID.
     */

    private static int getCountryIdByDivisionID(int divisionID) {

        List<Division> allDivisions = DivisionDAO.getAllDivisions();
        int countryID = -1;


        for (Division d : allDivisions) {
            if (divisionID == d.getDivisionID()) {
                countryID = d.getCountryID();
            }
        }

        return countryID;

    }

    /**
     * Gets a Customer object reference from a list of all customers based on customer ID.
     * @param customerID Customer ID to be used as search criteria for list.
     * @return Returns a Customer object that matches given id.
     */

    public static Customer getCustomerByID(int customerID) {

        List<Customer> allCustomers = CustomerDAO.getAllCustomers();
        Customer cus = null;

        for (Customer c : allCustomers) {
            if (customerID == c.getCusID()) {
                cus = new Customer(c);
            }
        }

        return cus;

    }

    /**
     * Gets a contact object reference from a list of all contacts based on given contact id.
     * @param contactID Contact ID to be used as search criteria for list.
     * @return Returns a contact from list who matches contact id.
     */

    public static Contact getContactByID(int contactID) {

        List<Contact> allContacts = ContactDAO.getAllContacts();
        Contact contact = null;

        for (Contact c : allContacts) {
            if (contactID == c.getContactId()) {
                contact = new Contact(c.getContactId(),c.getContactName(),c.getContactEmail());
            }
        }

        return contact;

    }

    /**
     * Gets a user from a list of all users based on given user id.
     * @param userID User ID to be used as search criteria for list.
     * @return Returns a UserCredential object from list of users who match user ID.
     */

    public static UserCredentials getUserByID(int userID) {

        List<UserCredentials> allUsers = UserDAO.getAllUsers();
        UserCredentials uc = null;

        for (UserCredentials listUc : allUsers) {
            if (userID == listUc.getUserID()) {
                uc = new UserCredentials(listUc.getUserID(), listUc.getUsername());
            }
        }

        return uc;

    }

    /**
     * Parses list of all countries and gets a list of all country names.
     * @return Returns an observable list of all country names in string form.
     */

    public static ObservableList<String> getAllCountryNames() {

        ObservableList<String> countryNames = FXCollections.observableArrayList();
        List<Country> allCountries = CountryDAO.getAllCountries();

        for (Country c : allCountries) {
            countryNames.add(c.getCountryName());
        }

        return countryNames;
    }

    /**
     * Parses list of all divisions and gets a list of all divisions in a country given a country name.
     * @param countryName Country whose divisions will populate return list.
     * @return Returns ObservableList of all divisions in given country in string form.
     */

    public static ObservableList<String> getAllDivisionsInCountry(String countryName) {

        ObservableList<String> divisionsInCountry = FXCollections.observableArrayList();
        List<Country> allCountries = CountryDAO.getAllCountries();
        int countryID = -1;

        for (Country c : allCountries) {
            if (c.getCountryName().equals(countryName)) {
                countryID =  c.getCountryID();
            }
        }

        List<Division> allDivisions = DivisionDAO.getAllDivisions();

        for (Division d : allDivisions) {
            if (d.getCountryID() == countryID) {
                divisionsInCountry.add(d.getDivisionName());
            }
        }

        return divisionsInCountry;

    }

    /**
     * Gets a division object from list of all divisions based on name.
     * @param divisionName Name to be used as search criteria.
     * @return Returns a Division whose name field matches param name.
     */

    public static Division getDivisionByName(String divisionName) {

        Division searched = null;

        for (Division d : DivisionDAO.getAllDivisions()) {
            if (d.getDivisionName().equals(divisionName)) {
                searched = d;
            }
        }

        return searched;

    }

    /**
     * Gets a list of all division names from a list of all divisions.
     * @return Returns a list of strings of all division names.
     */

    public static List<String> getAllDivisionNames() {

        ArrayList<String> allDivisionNames = new ArrayList<>();

        for (Division d : DivisionDAO.getAllDivisions()) {
            allDivisionNames.add(d.getDivisionName());
        }

        return allDivisionNames;
    }

    /**
     * Checks whether an appointment exists for a given customer.
     * @param cusID ID of customer to be checked.
     * @return Returns true if a customer has any appointment.
     */

    public static boolean customerAppointmentExists(int cusID) {

        boolean flag = false;

        for (Appointment appointment : AppointmentDAO.getAllAppointments()) {
            if (appointment.getCustomerID() == cusID) {
                flag = true;
            }
        }

        return flag;

    }

    /**
     * Gets all customer ids and names and collates them.
     * @return Returns an observable list of customer ids and names in string form of "id-name".
     */

    public static ObservableList<String> getAllCusIdName (){

        ObservableList<String> allCusIdName = FXCollections.observableArrayList();


        for (Customer c : CustomerDAO.getAllCustomers()) {
            String idName = c.getCusID() + "-" + c.getCusName();
            allCusIdName.add(idName);
        }

        return allCusIdName;

    }

    /**
     * Gets all contact ids and names and collates them.
     * @return Returns an observable list of contact ids and names in string form of "id-name".
     */

    public static ObservableList<String> getAllContactIdName () {

        ObservableList<String> allContactIdName = FXCollections.observableArrayList();

        for (Contact c : ContactDAO.getAllContacts()) {
            String idName = c.getContactId() + "-" + c.getContactName();
            allContactIdName.add(idName);
        }

        return  allContactIdName;

    }

    /**
     * Gets all user ids and names and collates them.
     * @return Returns an observable list of user ids and names in string form of "id-name".
     */

    public static ObservableList<String> getAllUserIdName () {

        ObservableList<String> allUserIdName = FXCollections.observableArrayList();
        for (UserCredentials u : UserDAO.getAllUsers()) {
            String idName = u.getUserID() + "-" + u.getUsername();
            allUserIdName.add(idName);
        }

        return allUserIdName;

    }

    /**
     * Gets a list of all appointments that fall within the current month and year.
     * @return Returns an observable list of appointments.
     */

    public static ObservableList<Appointment> getAppointmentsByCurrentMonth() throws ParseException {

        Date currentDate = new Date();
        SimpleDateFormat getMonth = new SimpleDateFormat("yyyy-MM");

        String currentMonth = getMonth.format(currentDate);

        ObservableList<Appointment> appointmentsWithinMonth = FXCollections.observableArrayList();

        for (Appointment a : AppointmentDAO.getAllAppointments()) {
            Date date = getMonth.parse(a.getStart().toString());
            String appMonth = getMonth.format(date);
            if (appMonth.equals(currentMonth)) {
                appointmentsWithinMonth.add(a);
            }
        }

        return appointmentsWithinMonth;

    }

    /**
     * Gets a list of all appointments that fall within the current week,month, year.
     * @return Returns an observable list of appointments.
     */

    public static ObservableList<Appointment> getAppointmentsByCurrentWeek() throws ParseException {


        LocalDate now = LocalDate.now();
        LocalDate weekFromNow = LocalDate.now().plusDays(7);

      ObservableList<Appointment> appointmentsWithinWeek = FXCollections.observableArrayList();

        for (Appointment a : AppointmentDAO.getAllAppointments()) {
            LocalDate appDate = LocalDate.parse(a.getStart().toString().split(" ")[0]);

            if ( appDate.isEqual(now) || ( appDate.isAfter(now) && appDate.isBefore(weekFromNow)) || appDate.isEqual(weekFromNow) ) {
                appointmentsWithinWeek.add(a);
            }
        }

        return appointmentsWithinWeek;

    }

    /**
     * Produces a list of all appointments within 15 min of users login time.
     * @param loginTime Time of users log in.
     * @return Returns an list of appointments.
     */

    public static List<Appointment> hasAppointmentsOnLogin(LocalTime loginTime) {

        List<Appointment> appointments = new ArrayList<>();

        LocalTime upcomingAppTimeFrame = loginTime.plusMinutes(15);

        for (Appointment a : AppointmentDAO.getAllAppointments()) {
            LocalTime appStartTime = LocalTime.parse(a.getStart().toString().split(" ")[1]);
            LocalDate appStartDate = LocalDate.parse(a.getStart().toString().split(" ")[0]);

            if (loginTime.equals(appStartTime) || ( appStartTime.isAfter(loginTime) && appStartTime.isBefore(upcomingAppTimeFrame) ) || appStartTime.equals(upcomingAppTimeFrame)) {
                if (appStartDate.isEqual(LocalDate.now())) {
                    appointments.add(a);
                }
            }
        }

        return appointments;

    }

    /**
     * Checks whether a given customers appointment start date and end date conflict with an existing appointment for said customer.
     * @param cusID ID of customer to be checked.
     * @param appDate Date of customers appointment.
     * @param appTime Time of customers appointment.
     * @return Returns true if customer has a conflicting appointment time.
     */

    public static boolean conflictingCustomerAppointments (int cusID, LocalDate appDate, LocalTime appTime) {

        boolean conflictsWithExistingAppointment = false;

        Timestamp start = null;
        Timestamp end = null;

        LocalDate comparedStartDate = null;
        LocalTime comparedStartTime = null;

        LocalDate comparedEndDate = null;
        LocalTime comparedEndTime = null;

        for (Appointment a : AppointmentDAO.getAllAppointments()) {
            if (a.getCustomerID() == cusID) {

                start = a.getStart();
                end = a.getEnd();

                comparedStartDate = LocalDate.parse(start.toString().split(" ")[0]);
                comparedStartTime = LocalTime.parse(start.toString().split(" ")[1]);

                comparedEndDate = LocalDate.parse(end.toString().split(" ")[0]);
                comparedEndTime = LocalTime.parse(end.toString().split(" ")[1]);

                if ( ( appDate.isEqual(comparedStartDate) && appTime.equals(comparedStartTime) ) || ( appDate.isEqual(comparedEndDate) && appTime.equals(comparedEndTime) )
                                                    || appDate.isEqual(comparedStartDate) && appTime.isAfter(comparedStartTime) && appTime.isBefore(comparedEndTime) ) {
                    return true;
                }
            }
        }

        return false;
    }
    /**
     * Checks whether a given customers appointment start date and end date conflict with an existing appointment for said customer.
     * @param cusID ID of customer to be checked.
     * @param appID Appointment ID
     * @param appDate Date of customers appointment.
     * @param appTime Time of customers appointment.
     * @return Returns true if customer has a conflicting appointment time.
     */
    public static boolean conflictingCustomerAppointments (int appID, int cusID, LocalDate appDate, LocalTime appTime) {

        boolean conflictsWithExistingAppointment = false;

        Timestamp start = null;
        Timestamp end = null;

        LocalDate comparedStartDate = null;
        LocalTime comparedStartTime = null;

        LocalDate comparedEndDate = null;
        LocalTime comparedEndTime = null;

        for (Appointment a : AppointmentDAO.getAllAppointments()) {
            if (a.getCustomerID() == cusID && a.getAppointmentID() != appID) {

                start = a.getStart();
                end = a.getEnd();

                comparedStartDate = LocalDate.parse(start.toString().split(" ")[0]);
                comparedStartTime = LocalTime.parse(start.toString().split(" ")[1]);

                comparedEndDate = LocalDate.parse(end.toString().split(" ")[0]);
                comparedEndTime = LocalTime.parse(end.toString().split(" ")[1]);

                if( ( appDate.isEqual(comparedStartDate) && appTime.equals(comparedStartTime) ) || ( appDate.isEqual(comparedEndDate) && appTime.equals(comparedEndTime) )
                        || appDate.isEqual(comparedStartDate) && appTime.isAfter(comparedStartTime) && appTime.isBefore(comparedEndTime) ) {
                    return true;
                }
            }
        }

        return false;
    }
    /**
     * Checks whether a given customers appointment start date and end date conflict with an existing appointment for said customer, specifically if appointment time is between given start and end times.
     * @param cusID ID of customer to be checked.
     * @param appDate Date of customers appointment.
     * @param appTime Time of customers appointment.
     * @return Returns true if customer has a conflicting appointment time.
     */
    public static boolean conflictingCustomerAppointments (int cusID, LocalDate appDate, LocalTime appTime, LocalTime endTime) {

        Timestamp start = null;
        Timestamp end = null;

        LocalDate comparedStartDate = null;
        LocalTime comparedStartTime = null;

        LocalDate comparedEndDate = null;
        LocalTime comparedEndTime = null;

        for (Appointment a : AppointmentDAO.getAllAppointments()) {
            if (a.getCustomerID() == cusID) {

                start = a.getStart();
                end = a.getEnd();

                comparedStartDate = LocalDate.parse(start.toString().split(" ")[0]);
                comparedStartTime = LocalTime.parse(start.toString().split(" ")[1]);

                comparedEndDate = LocalDate.parse(end.toString().split(" ")[0]);
                comparedEndTime = LocalTime.parse(end.toString().split(" ")[1]);

                 if (appDate.isEqual(comparedStartDate) && appTime.isBefore(comparedStartTime) && endTime.isAfter(comparedEndTime)) {
                    return true;
                }
            }
        }

        return false;
    }
    /**
     * Checks whether a given customers appointment start date and end date conflict with an existing appointment for said customer, specifically if appointment time is between given start and end times.
     * @param appId Appointment ID
     * @param cusID ID of customer to be checked.
     * @param appDate Date of customers appointment.
     * @param appTime Time of customers appointment.
     * @return Returns true if customer has a conflicting appointment time.
     */
    public static boolean conflictingCustomerAppointments (int appId, int cusID, LocalDate appDate, LocalTime appTime, LocalTime endTime) {

        Timestamp start = null;
        Timestamp end = null;

        LocalDate comparedStartDate = null;
        LocalTime comparedStartTime = null;

        LocalDate comparedEndDate = null;
        LocalTime comparedEndTime = null;

        for (Appointment a : AppointmentDAO.getAllAppointments()) {
            if (a.getCustomerID() == cusID && a.getAppointmentID() != appId) {

                start = a.getStart();
                end = a.getEnd();

                comparedStartDate = LocalDate.parse(start.toString().split(" ")[0]);
                comparedStartTime = LocalTime.parse(start.toString().split(" ")[1]);

                comparedEndDate = LocalDate.parse(end.toString().split(" ")[0]);
                comparedEndTime = LocalTime.parse(end.toString().split(" ")[1]);

                if (appDate.isEqual(comparedStartDate) && appTime.isBefore(comparedStartTime) && endTime.isAfter(comparedEndTime)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Gets a list of all unique appointment types from list of all appointments.
     * @return Returns a list of appointment types in string form.
     */

    public static List<String> getAllAppTypes() {

        ArrayList<String> allAppTypes = new ArrayList<>();

        for (Appointment a : AppointmentDAO.getAllAppointments()) {

            if (allAppTypes.isEmpty()) {
                allAppTypes.add(a.getType());
            }

            if (!allAppTypes.contains(a.getType())) {
                allAppTypes.add(a.getType());
            }

        }

        return allAppTypes;

    }



}
