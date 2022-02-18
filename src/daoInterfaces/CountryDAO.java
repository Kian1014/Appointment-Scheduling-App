package daoInterfaces;

import com.mysql.cj.xdevapi.SqlStatement;
import database.DatabaseConnection;
import model.Country;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Country DAO class used to query countries database.
 */

public class CountryDAO {

    /**
     * Retrieves all countries from countries table and transforms them into Countries List.
     * @return Returns list of Countries.
     */

    public static List<Country> getAllCountries () {

        ArrayList<Country> allCountries = new ArrayList<>();

        try {

            String sql = "Select * from countries";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");

                Country country = new Country(countryID,countryName);

                allCountries.add(country);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCountries;

    }


}
