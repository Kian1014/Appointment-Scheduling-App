package daoInterfaces;

import database.DatabaseConnection;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Division DAO class used to query first_level_divisions table.
 */

public class DivisionDAO {

    /**
     * Retrieves all Divisions and transforms them into a Divisions List.
     * @return Returns a list of Divisions
     */

    public static List<Division> getAllDivisions() {

        ArrayList<Division> allDivisions = new ArrayList<>();

        try {

            String sql = "select * from first_level_divisions";
            PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int countryID = rs.getInt("COUNTRY_ID");
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");

                Division division = new Division(divisionID,divisionName,countryID);

                allDivisions.add(division);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allDivisions;

    }



}
