package controller;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import daoInterfaces.UserDAO;
import helpers.ScreenSwitch;
import helpers.Search;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import helpers.Warning;
import model.Appointment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;

/**
 * Login controller class for login view.
 * Contains labels for login, username, password, zone id, and detected zone.
 * Contains text fields for username and password.
 * Contains a string to be displayed on incorrect login credentials.
 */

public class LoginController {

    @FXML
    private Label loginLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameText;

    @FXML
    private TextField passwordText;

    @FXML
    private Button submitBtn;

    @FXML
    private Label zoneID;

    @FXML
    private Label detectedZone;

    private String invalidLoginMessage = "Incorrect username/password";

    /**
     * Initialize method detects users locale and country and translates all labels,buttons, and error messages accordingly.
     */

    public void initialize() {
/*        Locale testLocale = new Locale("fr");
        Locale.setDefault(testLocale);*/
        ZoneId zoneId = ZoneId.systemDefault();
        Locale userLocale = Locale.getDefault();
        String userCountry = zoneId.toString();
        translateByLocale(userLocale);
        detectedZone.setText(userCountry);
    }

    /**
     * Helper method to translate form fields by locale to be passed in intialize method.
     */

    private void translateByLocale(Locale locale) {
        if (locale.getLanguage().equals("fr")) {
            loginLabel.setText("Connexion");
            passwordLabel.setText("Le mot de passe");
            usernameLabel.setText("Nom d'utilisateur");
            submitBtn.setText("Soumettre");
            zoneID.setText("ID de zone");
            invalidLoginMessage = "Nom d'utilisateur / Mot de passe incorrect";
        }
    }

    /**
     * Action listener for login button.
     * Logs in user if given credentials match those in user table in database.
     * Records log in attempt info in txt file in root folder, whether successful or unsuccessful.
     * On successful login, displays if logged in user has any appointments withing 15 minutes of login associated with them.
     * Displays warning alert if login unsuccessful.
     * Switches to main screen on successful login.
     */

    @FXML
    void login(ActionEvent event) throws IOException {

        String user = usernameText.getText();
        String password = passwordText.getText();
        if (UserDAO.loginVerification(user,password)) {
            recordLoginActivity(user, password, true);
            ScreenSwitch.switchScreen("/view/MainScreen.fxml", (Stage) submitBtn.getScene().getWindow(), "Main Screen");
            if (!Search.hasAppointmentsOnLogin(LocalTime.now()).isEmpty()) {
                StringBuilder appointmentsMessage = new StringBuilder();
                for (Appointment a : Search.hasAppointmentsOnLogin(LocalTime.now())) {
                    appointmentsMessage.append("Appointments within 15 minutes - ID:").append(a.getAppointmentID()).append("    Date/time: ").append(a.getStart().toString()).append("\n");
                }
                Warning.displayAlert(appointmentsMessage.toString());
            } else { Warning.displayAlert("No upcoming appointments within 15 minutes."); }
        } else {
            recordLoginActivity(user,password,false);
            Warning.displayAlert(invalidLoginMessage);
        }
    }

    /**
     * Helper method to write login activity to txt file in root folder.
     * @param username Username given on login.
     * @param password Password given on login.
     * @param successful Whether login was successful(true) or not(false).
     */

    private void recordLoginActivity(String username, String password, boolean successful) throws IOException {

        FileWriter fileWriter = new FileWriter("login_activity.txt", true);

        PrintWriter printWriter = new PrintWriter(fileWriter);
        String loginTime = new Timestamp(System.currentTimeMillis()).toString();

        printWriter.println("Attempted login at " + loginTime + " using User Credentials (username) " + username + " (password) " + password + " || Attempt successful: " + successful);

        printWriter.close();

    }


}
