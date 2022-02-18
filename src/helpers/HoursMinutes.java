package helpers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Helper class that stores start and end business hours and provides helper methods regarding time for controller classes to access.
 *         //help from https://stackoverflow.com/questions/7670355/convert-date-time-for-given-timezone-java
 *         //help from https://stackoverflow.com/questions/9886751/how-to-split-date-and-time-from-a-datetime-string
 */

public class HoursMinutes {

    private static final String startHour = "08:00";
    private static final String endHour = "22:00";

    /**
     * Produces a list of all hours in military time to populate combo boxes
     * @return Returns an ObservableList of hours from 00:00 to 23:00 in string form.
     */

    public static ObservableList<String> getAllHours() {

        ObservableList<String> allHours = FXCollections.observableArrayList();
        int i = 0;

        while (i < 24) {
            StringBuilder hour = new StringBuilder();
            if (i < 10) {
                hour.append("0").append(i);
            } else {
                hour.append(i);
            }

            allHours.add(hour.toString());
            i++;

        }

        return allHours;

    }

    /**
     * Produces a list of minutes to populate combo boxes.
     * @return Returns an ObservableList of strings of minutes from 00 to 55 in 5 minute increments.
     */

    public static ObservableList<String> getAllMinutes() {

        ObservableList<String> allMinutes = FXCollections.observableArrayList();
        int i = 0;

        while (i < 60) {
            String minute;
            if (i == 0 || i == 5) {
                minute = "0" + Integer.toString(i);
            } else {
                minute = Integer.toString(i);
            }

            allMinutes.add(minute);
            i = i + 5;
        }

        return allMinutes;

    }

    /**
     * Transforms EST business hour start time to local time.
     * @return Returns string form of local business hour start.
     */

    public static String getLocalBusinessHourStart() throws ParseException {

        DateFormat df = new SimpleDateFormat("HH:mm");
        df.setTimeZone(TimeZone.getTimeZone("EST"));
        DateFormat dfLocal = new SimpleDateFormat("HH:mm");
        Date date = df.parse(startHour);
        return dfLocal.format(date);


    }

    /**
     * Transforms EST business hour end time to local time.
     * @return Returns String form of local business hour end.
     */

    public static String getLocalBusinessHourEnd() throws ParseException {

            DateFormat df = new SimpleDateFormat("HH:mm");
            df.setTimeZone(TimeZone.getTimeZone("EST"));
            DateFormat dfLocal = new SimpleDateFormat("HH:mm");
            Date date = df.parse(endHour);
            return dfLocal.format(date);

    }

    /**
     * Compares given param time to local business hours.
     * @param time Time to be compared with local business hours.
     * @return Returns true if param time is within local business hours.
     */

    public static boolean timeWithinBusinessHours (String time) throws ParseException {

        LocalTime localStartTime = LocalTime.parse(getLocalBusinessHourStart());
        LocalTime localEndTime = LocalTime.parse(getLocalBusinessHourEnd());
        LocalTime meetingTime = LocalTime.parse(time);

        return (meetingTime.equals(localStartTime) || meetingTime.equals(localEndTime) || ( meetingTime.isAfter(localStartTime) && meetingTime.isBefore(localEndTime) ) );


//        DateFormat df = new SimpleDateFormat("HH:mm");
//        Date meetingTime = df.parse(time);
//        Date startTime = df.parse(getLocalBusinessHourStart());
//        Date endTime = df.parse(getLocalBusinessHourEnd());
//
//
//
//        return (meetingTime.after(startTime) && meetingTime.before(endTime));
    }

    /**
     * Separates hours from a timestamp.
     * @param timestamp Timestamp from which hours will be removed.
     * @return Returns String of hours from timestamp in format ##.
     */

    public static String getHoursFromTimestamp (Timestamp timestamp) {

        String hours = timestamp.toString().split(" ")[1];
        hours = hours.split(":")[0];
        return hours;

    }

    /**
     * Separates minutes from a timestamp.
     * @param timestamp Timestamp from which minutes will be removed.
     * @return Returns String of minutes from timestamp in format ##.
     */

    public static String getMinutesFromTimestamp (Timestamp timestamp) {

        String minutes = timestamp.toString().split(" ")[1];
        minutes = minutes.split(":")[1];
        return minutes;

    }

    /**
     * Separates date from a timestamp.
     * @param timestamp Timestamp from which date will be removed.
     * @return Returns String of LocalDate from timestamp in format yyyy-mm-dd.
     */

    public static LocalDate getLocalDateFromTimestamp (Timestamp timestamp) {

        String date = timestamp.toString().split(" ")[0];

        return LocalDate.parse(date);

    }


}
