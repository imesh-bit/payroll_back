/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nimesha
 */
public class ModelCreateCust {

    Connection connection;
    ModelPayroll modelPayroll;

    public ModelCreateCust(Connection connection) {
        this.connection = connection;
        this.modelPayroll = new ModelPayroll(connection);
    }

    public String settxtCustFile(Connection con, String comCode, String sFileName, String salMnth, String salTyp, String description, String purposeCode, String purposeDescription) {

        BufferedWriter f = null;
        double netsal = 0.0;
        int k = 0;
        try {
            if (!modelPayroll.isFinalized(connection, salMnth, comCode)) {
                return "Error: Selected salary month is not finalized yet";
            } else {
                f = new BufferedWriter(new FileWriter(sFileName));
                String sql = "SELECT\n"
                        + "     Payments.`empid` AS EmpID,\n"
                        + "     employee_details.`empinitialname` AS ename,\n"
                        + "     employee_details.`nic` AS nic,\n"
                        + "     IFNULL(bank.id,0)AS BankId,\n"
                        + "     IFNULL(bank.bankSwiftCode,'')AS SwiftCode,\n"
                        + "     Payments.`accno1` AS AccNo,\n"
                        + "     Payments.`Amount1` AS Amount\n"
                        + "FROM\n"
                        + "     `bankdata` bank RIGHT OUTER JOIN `emppaybank` Payments ON bank.`id` = Payments.`bankid1`\n"
                        + "     LEFT OUTER JOIN `employee_details` employee_details ON Payments.`empid` = employee_details.`empid`\n"
                        + "WHERE\n"
                        + "     employee_details.salarytyp = ?\n"
                        + " AND Payments.monthcode = ?\n"
                        + " AND Payments.amount1 > 0\n"
                        + " AND Payments.comcode = ?\n"
                        + "ORDER BY\n"
                        + "     Payments.comcode ASC,\n"
                        + "     employee_details.empcode ASC";

                PreparedStatement pstmt = null;
                ResultSet rs = null;
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, salTyp);
                pstmt.setString(2, salMnth);
                pstmt.setString(3, comCode);
                rs = pstmt.executeQuery();
                // System.out.println("sql salary transfer cust---" + pstmt);
                while (rs.next()) {
                    if (netsal != 0) {
                        f.write("\r\n");
                    }
                    k = k + 1;//Incremental No

                    if (rs.getInt("BankId") == 0) {//Commercial bank account holders
                        f.write(rs.getString("AccNo"));
                        f.write("|");
                        f.write(String.valueOf(rs.getBigDecimal("Amount")));
                        f.write("|");
                        f.write(description);
                        f.write("|");
                        f.write(description);
                        f.write("|");
                        f.write(purposeCode);
                        f.write("|");
                        f.write(purposeDescription);
                    } else {//Other bank account holders
                        f.write(rs.getString("AccNo"));
                        f.write("|");
                        f.write(String.valueOf(rs.getBigDecimal("Amount")));
                        f.write("|");
                        f.write(rs.getString("SwiftCode"));
                        f.write("|");
                        f.write(description);
                        f.write("|");
                        f.write(description);
                        f.write("|");
                        f.write(rs.getString("ename"));
                        f.write("|");
                        f.write(rs.getString("nic"));
                        f.write("|");
                        f.write(purposeCode);
                        f.write("|");
                        f.write(purposeDescription);
                        f.write("|NRM|SHA");
                    }

                    netsal = netsal + rs.getDouble("Amount");
                    f.flush();

                }
                f.close();
                pstmt.close();
                rs.close();

            }

        } catch (Exception ex) {
            return "Error:" + ex.getLocalizedMessage();
        }
        return "Cust File Sucessfully Created.Total Transaction = LKR " + netsal + " | No. of transactions = " + k;
    }

}
