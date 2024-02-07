package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Nimesha
 */
public class ModelDashBoard {

    Connection connection;

    public ModelDashBoard(Connection connection) {
        this.connection = connection;
    }

    public JSONArray getAgeRangeChartData(Connection connection) throws Exception {
        JSONArray jsa = new JSONArray();
        LinkedHashMap mp = new LinkedHashMap();
        String query = "SELECT\n"
                + "    SUM(((TIMESTAMPDIFF(YEAR, `datebirth`, CURDATE())) BETWEEN 18 AND 24)) AS '18-24',\n"
                + "    SUM(((TIMESTAMPDIFF(YEAR, `datebirth`, CURDATE())) BETWEEN 25 AND 34)) AS '25-34',\n"
                + "    SUM(((TIMESTAMPDIFF(YEAR, `datebirth`, CURDATE())) BETWEEN 35 AND 44)) AS '35-44',\n"
                + "    SUM(((TIMESTAMPDIFF(YEAR, `datebirth`, CURDATE())) BETWEEN 45 AND 54)) AS '45-54',\n"
                + "    SUM(((TIMESTAMPDIFF(YEAR, `datebirth`, CURDATE())) >=55)) AS '55+'\n"
                + "FROM `employee_details`;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            mp.put("18-24", rs.getInt("18-24"));
            mp.put("25-34", rs.getInt("25-34"));
            mp.put("35-44", rs.getInt("35-44"));
            mp.put("45-54", rs.getInt("45-54"));
            mp.put("55+", rs.getInt("55+"));
        }
        rs.close();
        ps.close();

        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            JSONObject chartData = new JSONObject();
            Map.Entry pairs = (Map.Entry) it.next();
            chartData.put("name", pairs.getKey());
            chartData.put("value", pairs.getValue());
            jsa.add(chartData);
        }

        return jsa;
    }

    public JSONArray getPieChartData(Connection connection) throws Exception {
        JSONArray jsa = new JSONArray();
        LinkedHashMap mp = new LinkedHashMap();
        String query = "SELECT `typofemp`,COUNT(*) AS empCount FROM `employee_details` GROUP BY `typofemp`;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            mp.put(rs.getString("typofemp"), rs.getInt("empCount"));
        }
        rs.close();
        ps.close();

        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            JSONObject chartData = new JSONObject();
            Map.Entry pairs = (Map.Entry) it.next();
            chartData.put("name", pairs.getKey());
            chartData.put("value", pairs.getValue());
            jsa.add(chartData);
        }

        return jsa;
    }

    public JSONArray getMaleFemaleData(Connection connection) throws Exception {
        JSONArray jsa = new JSONArray();
        LinkedHashMap mp = new LinkedHashMap();
        String query = "SELECT `gender`,COUNT(*) AS empCount FROM `employee_details` GROUP BY `gender`;";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (rs.getInt("gender") == 1) {
                mp.put("Male", rs.getInt("empCount"));
            } else {
                mp.put("Female", rs.getInt("empCount"));
            }
        }
        rs.close();
        ps.close();

        jsa.add(mp);
        return jsa;
    }

}
