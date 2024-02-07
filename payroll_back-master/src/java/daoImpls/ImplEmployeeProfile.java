/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daoImpls;

import dao.BeanEmployee;
import dao.BeanEmployeeBank;
import dao.BeanSystemLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.JDBC;
import interfaces.InterfaceEmployeeProfile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nimesha
 */
public class ImplEmployeeProfile implements InterfaceEmployeeProfile {

    @Override
    public String saveEmployeeProfile(BeanEmployee employeeDetails) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        int empId = 0;

        try {
            //   System.out.println(BeanSystemLog.getUser() + "**************" + BeanSystemLog.getComcode());
            if (!validateNIC(connection, employeeDetails.getComcode(), employeeDetails.getNic())) {
                return "Error:" + "This NIC already registered!";
            }

            if (employeeDetails.isEpfAuto()) {
                int empCode = getNextEpfNo(connection, employeeDetails.getComcode());
                employeeDetails.setEmpcode(empCode);
                employeeDetails.setEpfno(getEpfRegNo(connection, employeeDetails.getComcode()) + "/" + String.valueOf(empCode));
            } else {
                if (employeeDetails.getEmpcode() == 0) {
                    employeeDetails.setEpfno("");
                } else {
                    if (!validateEPF(connection, employeeDetails.getComcode(), employeeDetails.getEmpcode())) {
                        return "Error:" + "This EPF already registered!";
                    }
                    employeeDetails.setEpfno(getEpfRegNo(connection, employeeDetails.getComcode()) + "/" + String.valueOf(employeeDetails.getEmpcode()));
                }
            }

            String sql_emp_details = "INSERT INTO `employee_details`\n"
                    + "(`date`,`empcode`,`titel`,`initials`,`surname`,`empinitialname`,`empfullname`,`nic`,`datebirth`,`gender`,`empaddress`,`tele`,`email`,`status`,\n"
                    + "`dateappointment`,`probationenddate`,`groupid`,`categoryid`,`typofemp`,`division`,`designation`,`salarytyp`,`basicsalary`,`budgetallowance`,`bra2`,\n"
                    + "`allow1`,`allow2`,`allow3`,`allow4`,`allow5`,`deduct1`,`deduct2`,`deduct3`,`deduct4`,`deduct5`,`epfno`,`remarks`,`comcode`,`active`,`imgfilename`,`user`)\n"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

            PreparedStatement ps_emp_details = connection.prepareStatement(sql_emp_details, Statement.RETURN_GENERATED_KEYS);
            ps_emp_details.setDate(1, new java.sql.Date(employeeDetails.getDate().getTime()));
            ps_emp_details.setInt(2, employeeDetails.getEmpcode());
            ps_emp_details.setString(3, employeeDetails.getTitel());
            ps_emp_details.setString(4, employeeDetails.getInitials());
            ps_emp_details.setString(5, employeeDetails.getSurname());
            ps_emp_details.setString(6, employeeDetails.getEmpinitialname());
            ps_emp_details.setString(7, employeeDetails.getEmpfullname());
            ps_emp_details.setString(8, employeeDetails.getNic());
            ps_emp_details.setDate(9, new java.sql.Date(employeeDetails.getDatebirth().getTime()));
            ps_emp_details.setInt(10, employeeDetails.getGender());
            ps_emp_details.setString(11, employeeDetails.getEmpaddress());
            ps_emp_details.setString(12, employeeDetails.getTele());
            ps_emp_details.setString(13, employeeDetails.getEmail());
            ps_emp_details.setString(14, employeeDetails.getStatus());
            ps_emp_details.setDate(15, new java.sql.Date(employeeDetails.getDateappointment().getTime()));
            ps_emp_details.setDate(16, new java.sql.Date(employeeDetails.getProbationenddate().getTime()));
            ps_emp_details.setInt(17, employeeDetails.getGroupid());
            ps_emp_details.setInt(18, employeeDetails.getCategoryid());
            ps_emp_details.setString(19, employeeDetails.getTypofemp());
            ps_emp_details.setString(20, employeeDetails.getDivision());
            ps_emp_details.setString(21, employeeDetails.getDesignation());
            ps_emp_details.setString(22, "Normal");//employeeDetails.getSalarytyp()
            ps_emp_details.setBigDecimal(23, employeeDetails.getBasicsalary());
            ps_emp_details.setBigDecimal(24, employeeDetails.getBudgetallowance());
            ps_emp_details.setBigDecimal(25, employeeDetails.getBra2());
            ps_emp_details.setBigDecimal(26, employeeDetails.getAllow1());
            ps_emp_details.setBigDecimal(27, employeeDetails.getAllow2());
            ps_emp_details.setBigDecimal(28, employeeDetails.getAllow3());
            ps_emp_details.setBigDecimal(29, employeeDetails.getAllow4());
            ps_emp_details.setBigDecimal(30, employeeDetails.getAllow5());
            ps_emp_details.setBigDecimal(31, employeeDetails.getDeduct1());
            ps_emp_details.setBigDecimal(32, employeeDetails.getDeduct2());
            ps_emp_details.setBigDecimal(33, employeeDetails.getDeduct3());
            ps_emp_details.setBigDecimal(34, employeeDetails.getDeduct4());
            ps_emp_details.setBigDecimal(35, employeeDetails.getDeduct5());
            ps_emp_details.setString(36, employeeDetails.getEpfno());
            ps_emp_details.setString(37, employeeDetails.getRemarks());
            ps_emp_details.setString(38, BeanSystemLog.getComcode());
            ps_emp_details.setInt(39, 1);//employeeDetails.getActive()
            ps_emp_details.setString(40, employeeDetails.getImgfilename());
            ps_emp_details.setString(41, BeanSystemLog.getUser());
            ps_emp_details.executeUpdate();

            ResultSet keys = ps_emp_details.getGeneratedKeys();
            keys.next();
            empId = keys.getInt(1);

            employeeDetails.setEmpid(empId);

            ps_emp_details.close();

            BeanEmployeeBank empBank = employeeDetails.getBankDetails();
            empBank.setEmpid(empId);

            String sql_emp_bank = "INSERT INTO `employee_bank` (empid,bankid1,accno1,amount1,bankid2,accno2,amount2,user,comcode) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps_emp_bank = connection.prepareStatement(sql_emp_bank);
            ps_emp_bank.setInt(1, empBank.getEmpid());
            ps_emp_bank.setInt(2, empBank.getBankid1());
            ps_emp_bank.setString(3, empBank.getAccno1());
            ps_emp_bank.setBigDecimal(4, empBank.getAmount1());
            ps_emp_bank.setInt(5, empBank.getBankid2());
            ps_emp_bank.setString(6, empBank.getAccno2());
            ps_emp_bank.setBigDecimal(7, empBank.getAmount2());
            ps_emp_bank.setString(8, BeanSystemLog.getUser());
            ps_emp_bank.setString(9, BeanSystemLog.getComcode());
            ps_emp_bank.execute();
            ps_emp_bank.close();

            String sql_user = "INSERT INTO `user`(`empid`,`username`,`password`,`fullname`,`comcode`,`loccode`,`regdate`,`usergroup`,"
                    + "`email`,`mobileno`,`status`,`dataentryuser`)\n"
                    + "VALUES (?,?,md5(?),?,?,?,?,?,?,?,?,?);";
            PreparedStatement ps_allow = connection.prepareStatement(sql_user);
            ps_allow.setInt(1, employeeDetails.getEmpid());
            ps_allow.setString(2, employeeDetails.getEmail());
            ps_allow.setString(3, employeeDetails.getNic().trim().concat(String.valueOf(employeeDetails.getEmpid())));
            ps_allow.setString(4, employeeDetails.getEmpinitialname());
            ps_allow.setString(5, BeanSystemLog.getComcode());
            ps_allow.setString(6, "NA");
            ps_allow.setDate(7, new java.sql.Date(new Date().getTime()));
            ps_allow.setString(8, "Payslip User");
            ps_allow.setString(9, employeeDetails.getEmail());
            ps_allow.setString(10, employeeDetails.getTele());
            ps_allow.setInt(11, 1);
            ps_allow.setString(12, BeanSystemLog.getUser());
            ps_allow.execute();
            ps_allow.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Save");
            ps.setString(2, "Employee profile : " + employeeDetails.getEmpid());
            ps.setString(3, BeanSystemLog.getComcode());
            ps.setString(4, BeanSystemLog.getUser());
            ps.execute();
            ps.close();

            connection.commit();
            connection.setAutoCommit(true);

            return "Details saved|" + employeeDetails.getEmpcode() + "|" + empId;

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return "Error:" + e.getLocalizedMessage();
        } finally {
            connection.close();
        }
    }

    @Override
    public String getEpfRegNo(Connection connection, String comCode) throws Exception {
        String epfRegCode = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String sql = "select EPFRegNo from company where comcode = '" + comCode + "' ";
            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                epfRegCode = rs.getString("EPFRegNo");
            }
            pstmt.close();
            rs.close();
        } catch (SQLException ex) {
            pstmt.close();
            rs.close();

        }
        return epfRegCode;
    }

    @Override
    public int getNextEpfNo(Connection connection, String comCode) throws Exception {
        int EmpCode = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "select max(empcode) from employee_details where comcode='" + comCode + "'";
            pstmt = connection.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                EmpCode = rs.getInt(1) + 1;
            }

            pstmt.close();
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            pstmt.close();
            rs.close();
        }
        return EmpCode;
    }

    @Override
    public boolean validateNIC(Connection connection, String ComCode, String NIC) throws Exception {
        String sql = "select count(*) from employee_details where comcode='" + ComCode + "' AND nic='" + NIC + "' AND active=1";

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int i = 0;
        while (rs.next()) {
            i = rs.getInt(1);
        }
        rs.close();
        ps.close();

        if (i > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String updateEmployeeProfile(BeanEmployee employeeDetails) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {

            String sql_emp_details = "UPDATE `employee_details`\n"
                    + "SET `date`=?,`titel`=?,`initials`=?,`surname`=?,`empinitialname`=?,`empfullname`=?,`nic`=?,`datebirth`=?,`gender`=?,`empaddress`=?,`tele`=?,`email`=?,`status`=?,\n"
                    + "`dateappointment`=?,`probationenddate`=?,`groupid`=?,`categoryid`=?,`typofemp`=?,`division`=?,`designation`=?,`salarytyp`=?,`basicsalary`=?,`budgetallowance`=?,`bra2`=?,\n"
                    + "`allow1`=?,`allow2`=?,`allow3`=?,`allow4`=?,`allow5`=?,`deduct1`=?,`deduct2`=?,`deduct3`=?,`deduct4`=?,`deduct5`=?,`remarks`=?,`comcode`=?,`imgfilename`=?,`user`=?\n"
                    + "WHERE `empid`=? ";

            PreparedStatement ps_emp_details = connection.prepareStatement(sql_emp_details);
            ps_emp_details.setDate(1, new java.sql.Date(employeeDetails.getDate().getTime()));
            ps_emp_details.setString(2, employeeDetails.getTitel());
            ps_emp_details.setString(3, employeeDetails.getInitials());
            ps_emp_details.setString(4, employeeDetails.getSurname());
            ps_emp_details.setString(5, employeeDetails.getEmpinitialname());
            ps_emp_details.setString(6, employeeDetails.getEmpfullname());
            ps_emp_details.setString(7, employeeDetails.getNic());
            ps_emp_details.setDate(8, new java.sql.Date(employeeDetails.getDatebirth().getTime()));
            ps_emp_details.setInt(9, employeeDetails.getGender());
            ps_emp_details.setString(10, employeeDetails.getEmpaddress());
            ps_emp_details.setString(11, employeeDetails.getTele());
            ps_emp_details.setString(12, employeeDetails.getEmail());
            ps_emp_details.setString(13, employeeDetails.getStatus());
            ps_emp_details.setDate(14, new java.sql.Date(employeeDetails.getDateappointment().getTime()));
            ps_emp_details.setDate(15, new java.sql.Date(employeeDetails.getProbationenddate().getTime()));
            ps_emp_details.setInt(16, employeeDetails.getGroupid());
            ps_emp_details.setInt(17, employeeDetails.getCategoryid());
            ps_emp_details.setString(18, employeeDetails.getTypofemp());
            ps_emp_details.setString(19, employeeDetails.getDivision());
            ps_emp_details.setString(20, employeeDetails.getDesignation());
            ps_emp_details.setString(21, "Normal");// employeeDetails.getSalarytyp()
            ps_emp_details.setBigDecimal(22, employeeDetails.getBasicsalary());
            ps_emp_details.setBigDecimal(23, employeeDetails.getBudgetallowance());
            ps_emp_details.setBigDecimal(24, employeeDetails.getBra2());
            ps_emp_details.setBigDecimal(25, employeeDetails.getAllow1());
            ps_emp_details.setBigDecimal(26, employeeDetails.getAllow2());
            ps_emp_details.setBigDecimal(27, employeeDetails.getAllow3());
            ps_emp_details.setBigDecimal(28, employeeDetails.getAllow4());
            ps_emp_details.setBigDecimal(29, employeeDetails.getAllow5());
            ps_emp_details.setBigDecimal(30, employeeDetails.getDeduct1());
            ps_emp_details.setBigDecimal(31, employeeDetails.getDeduct2());
            ps_emp_details.setBigDecimal(32, employeeDetails.getDeduct3());
            ps_emp_details.setBigDecimal(33, employeeDetails.getDeduct4());
            ps_emp_details.setBigDecimal(34, employeeDetails.getDeduct5());
            ps_emp_details.setString(35, employeeDetails.getRemarks());
            ps_emp_details.setString(36, BeanSystemLog.getComcode());
            ps_emp_details.setString(37, employeeDetails.getImgfilename());
            ps_emp_details.setString(38, BeanSystemLog.getUser());
            ps_emp_details.setInt(39, employeeDetails.getEmpid());
            ps_emp_details.executeUpdate();
            ps_emp_details.close();

            if (employeeDetails.isEpfAuto()) {
                int empCode = getNextEpfNo(connection, BeanSystemLog.getComcode());
                employeeDetails.setEmpcode(empCode);
                employeeDetails.setEpfno(getEpfRegNo(connection, BeanSystemLog.getComcode()) + "/" + String.valueOf(empCode));
                String sql_emp_epf_update = "UPDATE `employee_details`  SET `empcode`=?,`epfno`=? WHERE empid=? ";
                PreparedStatement ps_emp_epf = connection.prepareStatement(sql_emp_epf_update);
                ps_emp_epf.setInt(1, employeeDetails.getEmpcode());
                ps_emp_epf.setString(2, employeeDetails.getEpfno());
                ps_emp_epf.setInt(3, employeeDetails.getEmpid());
                ps_emp_epf.executeUpdate();
                ps_emp_epf.close();
            }

            BeanEmployeeBank empBank = employeeDetails.getBankDetails();

            Statement cstmt = connection.createStatement();
            String sql_delete = "DELETE FROM `employee_bank`  WHERE empid = '" + empBank.getEmpid() + "' ";
            cstmt.executeUpdate(sql_delete);
            cstmt.close();

            String sql_emp_bank = "INSERT INTO `employee_bank` (empid,bankid1,accno1,amount1,bankid2,accno2,amount2,user,comcode) VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps_emp_bank = connection.prepareStatement(sql_emp_bank);
            ps_emp_bank.setInt(1, empBank.getEmpid());
            ps_emp_bank.setInt(2, empBank.getBankid1());
            ps_emp_bank.setString(3, empBank.getAccno1());
            ps_emp_bank.setBigDecimal(4, empBank.getAmount1());
            ps_emp_bank.setInt(5, empBank.getBankid2());
            ps_emp_bank.setString(6, empBank.getAccno2());
            ps_emp_bank.setBigDecimal(7, empBank.getAmount2());
            ps_emp_bank.setString(8, BeanSystemLog.getUser());
            ps_emp_bank.setString(9, BeanSystemLog.getComcode());
            ps_emp_bank.execute();
            ps_emp_bank.close();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Update");
            ps.setString(2, "Employee profile : " + employeeDetails.getEmpid());
            ps.setString(3, BeanSystemLog.getComcode());
            ps.setString(4, BeanSystemLog.getUser());
            ps.execute();
            ps.close();

            connection.commit();
            connection.setAutoCommit(true);

            return "Details updated|" + employeeDetails.getEmpcode() + "|" + employeeDetails.getEmpid();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return "Error:" + e.getLocalizedMessage();
        } finally {
            connection.close();
        }

    }

    @Override
    public BeanEmployeeBank getEmployeeBankDetails(Connection connection, int empId, String comCode) {
        BeanEmployeeBank empBank = null;

        try {

            Statement st = connection.createStatement();
            String sql = "SELECT ifnull(bankid1,'0') as BankID1,accno1,amount1, ifnull(bankid2,'0') as BankID2,accno2,amount2 FROM employee_bank "
                    + "WHERE empid='" + empId + "' AND comcode='" + comCode + "'";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                empBank = new BeanEmployeeBank();
                empBank.setBankid1(rs.getInt("BankID1"));
                empBank.setAccno1(rs.getString("accno1"));
                empBank.setAmount1(rs.getBigDecimal("amount1"));
                empBank.setBankid2(rs.getInt("BankID2"));
                empBank.setAccno2(rs.getString("accno2"));
                empBank.setAmount2(rs.getBigDecimal("amount2"));
            }

            st.close();
            rs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return empBank;
    }

    @Override
    public BeanEmployee getSelectedEmployeeProfile(Connection connection, int empId, String comCode) throws Exception {
        BeanEmployee employeeDetails = null;

        try {

            Statement st = connection.createStatement();
            String sql = "SELECT * FROM employee_details WHERE empid='" + empId + "' AND comcode='" + comCode + "'";
            ResultSet rs = st.executeQuery(sql);
            if (!rs.isBeforeFirst()) {

            } else {
                rs.first();

                employeeDetails = new BeanEmployee();
                employeeDetails.setEmpid(rs.getInt("empid"));
                employeeDetails.setDate(rs.getDate("date"));
                employeeDetails.setEmpcode(rs.getInt("empcode"));
                employeeDetails.setTitel(rs.getString("titel"));
                employeeDetails.setInitials(rs.getString("initials"));
                employeeDetails.setSurname(rs.getString("surname"));
                employeeDetails.setEmpinitialname(rs.getString("empinitialname"));
                employeeDetails.setEmpfullname(rs.getString("empfullname"));
                employeeDetails.setNic(rs.getString("nic"));
                employeeDetails.setDatebirth(rs.getDate("datebirth"));
                employeeDetails.setGender(rs.getInt("gender"));
                employeeDetails.setEmpaddress(rs.getString("empaddress"));
                employeeDetails.setTele(rs.getString("tele"));
                employeeDetails.setEmail(rs.getString("email"));
                employeeDetails.setStatus(rs.getString("status"));
                employeeDetails.setDateappointment(rs.getDate("dateappointment"));
                employeeDetails.setProbationenddate(rs.getDate("probationenddate"));
                employeeDetails.setGroupid(rs.getInt("groupid"));
                employeeDetails.setCategoryid(rs.getInt("categoryid"));
                employeeDetails.setTypofemp(rs.getString("typofemp"));
                employeeDetails.setDivision(rs.getString("division"));
                employeeDetails.setDesignation(rs.getString("designation"));
                employeeDetails.setSalarytyp(rs.getString("salarytyp"));
                employeeDetails.setBasicsalary(rs.getBigDecimal("basicsalary"));
                employeeDetails.setBudgetallowance(rs.getBigDecimal("budgetallowance"));
                employeeDetails.setBra2(rs.getBigDecimal("bra2"));
                employeeDetails.setAllow1(rs.getBigDecimal("allow1"));
                employeeDetails.setAllow2(rs.getBigDecimal("allow2"));
                employeeDetails.setAllow3(rs.getBigDecimal("allow3"));
                employeeDetails.setAllow4(rs.getBigDecimal("allow4"));
                employeeDetails.setAllow5(rs.getBigDecimal("allow5"));
                employeeDetails.setDeduct1(rs.getBigDecimal("deduct1"));
                employeeDetails.setDeduct2(rs.getBigDecimal("deduct2"));
                employeeDetails.setDeduct3(rs.getBigDecimal("deduct3"));
                employeeDetails.setDeduct4(rs.getBigDecimal("deduct4"));
                employeeDetails.setDeduct5(rs.getBigDecimal("deduct5"));
                employeeDetails.setEpfno(rs.getString("epfno"));
                employeeDetails.setRemarks(rs.getString("remarks"));
                employeeDetails.setActive(rs.getShort("active"));
                employeeDetails.setComcode(rs.getString("comcode"));
                employeeDetails.setUser(rs.getString("user"));
                employeeDetails.setImgfilename(rs.getString("imgfilename"));
                employeeDetails.setBankDetails(getEmployeeBankDetails(connection, rs.getInt("empid"), rs.getString("comcode")));

                st.close();
                rs.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employeeDetails;
    }

    @Override
    public List<BeanEmployee> getEmployeeList(Connection connection, String comCode) throws Exception {
        List<BeanEmployee> empList = new ArrayList<>();

        try {

            String sql = "SELECT `empid`,`empinitialname`,`empcode`,`division`,`designation`,`dateappointment`,`datebirth`,`gender`,`active` "
                    + "FROM employee_details WHERE  comcode='" + comCode + "' ORDER BY empid DESC";
            System.out.println(sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {

                    BeanEmployee employeeDetails = new BeanEmployee();
                    employeeDetails.setEmpid(rs.getInt("empid"));
                    employeeDetails.setEmpinitialname(rs.getString("empinitialname"));
                    employeeDetails.setEmpcode(rs.getInt("empcode"));
                    employeeDetails.setDivision(rs.getString("division"));
                    employeeDetails.setDesignation(rs.getString("designation"));
                    employeeDetails.setDateappointment(rs.getDate("dateappointment"));
                    employeeDetails.setDatebirth(rs.getDate("datebirth"));
                    employeeDetails.setGender(rs.getInt("gender"));
                    employeeDetails.setActive(rs.getShort("active"));
                    employeeDetails.setSalary(getConsolidatedSalry(connection, rs.getInt("empid"), comCode));
                    empList.add(employeeDetails);
                }

            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return empList;
    }

    @Override
    public boolean validateEPF(Connection connection, String ComCode, int EmpCode) throws Exception {
        String sql = "select count(*) from employee_details where comcode='" + ComCode + "' AND empcode='" + EmpCode + "'";

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int i = 0;
        while (rs.next()) {
            i = rs.getInt(1);
        }
        rs.close();
        ps.close();

        if (i > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public BigDecimal getConsolidatedSalry(Connection connection, int empId, String comCode) throws Exception {
        BigDecimal salary = BigDecimal.ZERO;
        String query = "SELECT (`basicsalary`+`budgetallowance`+`bra2`) AS Salary \n"
                + "FROM `employee_details` WHERE `empid`=? AND `comcode`=?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, empId);
        ps.setString(2, comCode);
        ResultSet rs = ps.executeQuery();

        if (rs.isBeforeFirst()) {
            while (rs.next()) {
                salary = rs.getBigDecimal("Salary");
            }
        }
        ps.close();
        return salary;
    }

    @Override
    public String terminateEmployee(int empId, String terminateNote) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            String sql = "UPDATE `employee_details` SET `terminatedate`=?,`terminateNote`=?,`active`=? WHERE empid=?;";
            PreparedStatement ps_allow = connection.prepareStatement(sql);
            ps_allow.setDate(1, new java.sql.Date(new Date().getTime()));
            ps_allow.setString(2, terminateNote);
            ps_allow.setInt(3, 0);
            ps_allow.setInt(4, empId);
            ps_allow.executeUpdate();

            String sql_u = "UPDATE `user` SET `terminatedate`=?,`status`=? WHERE empid=?;";
            PreparedStatement ps_u = connection.prepareStatement(sql_u);
            ps_u.setDate(1, new java.sql.Date(new Date().getTime()));
            ps_u.setInt(2, 0);
            ps_u.setInt(3, empId);
            ps_u.executeUpdate();

            String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(saveQuery);
            ps.setString(1, "Terminate");
            ps.setString(2, "Emp Id : " + empId);
            ps.setString(3, BeanSystemLog.getComcode());
            ps.setString(4, BeanSystemLog.getUser());
            ps.execute();
            ps.close();

            connection.commit();
            connection.setAutoCommit(true);

            return "Termination Process Completed";
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return "Error:" + e.getLocalizedMessage();
        } finally {
            connection.close();
        }
    }

    @Override
    public List<BeanEmployee> getTerminateEmployeeList(Connection connection) throws Exception {
        List<BeanEmployee> empList = new ArrayList<>();

        try {

            String sql = "SELECT `empid`,`empinitialname`,`empcode`,`division`,`designation`,`dateappointment`,`terminatedate`,`gender`,IFNULL(`terminateNote`,'') AS terminateNote "
                    + "FROM employee_details WHERE  comcode='" + BeanSystemLog.getComcode() + "' and  `active`=0 ORDER BY empid DESC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {

                    BeanEmployee employeeDetails = new BeanEmployee();
                    employeeDetails.setEmpid(rs.getInt("empid"));
                    employeeDetails.setEmpinitialname(rs.getString("empinitialname"));
                    employeeDetails.setEmpcode(rs.getInt("empcode"));
                    employeeDetails.setDivision(rs.getString("division"));
                    employeeDetails.setDesignation(rs.getString("designation"));
                    employeeDetails.setDateappointment(rs.getDate("dateappointment"));
                    employeeDetails.setTerminateDate(rs.getDate("terminatedate"));
                    employeeDetails.setTerminateNote(rs.getString("terminateNote"));
                    empList.add(employeeDetails);
                }

            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return empList;
    }

    @Override
    public List<BeanEmployee> getActiveEmployeeList(Connection connection, String comCode) throws Exception {
        List<BeanEmployee> empList = new ArrayList<>();

        try {

            String sql = "SELECT `empid`,`empinitialname`,`empcode`,`division`,`designation`,`dateappointment`,`datebirth`,`gender`,`active` "
                    + "FROM employee_details WHERE  comcode='" + comCode + "' AND `active`=1 ORDER BY empid DESC";
            System.out.println(sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.isBeforeFirst()) {
                while (rs.next()) {

                    BeanEmployee employeeDetails = new BeanEmployee();
                    employeeDetails.setEmpid(rs.getInt("empid"));
                    employeeDetails.setEmpinitialname(rs.getString("empinitialname"));
                    employeeDetails.setEmpcode(rs.getInt("empcode"));
                    employeeDetails.setDivision(rs.getString("division"));
                    employeeDetails.setDesignation(rs.getString("designation"));
                    employeeDetails.setDateappointment(rs.getDate("dateappointment"));
                    employeeDetails.setDatebirth(rs.getDate("datebirth"));
                    employeeDetails.setGender(rs.getInt("gender"));
                    employeeDetails.setActive(rs.getShort("active"));
                    employeeDetails.setSalary(getConsolidatedSalry(connection, rs.getInt("empid"), comCode));
                    empList.add(employeeDetails);
                }

            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return empList;
    }

    @Override
    public String saveEmpDataExcel(List<BeanEmployee> list) throws SQLException, Exception {
        Connection connection = JDBC.con_payroll();
        connection.setAutoCommit(false);

        try {
            int empId = 0;
            for (BeanEmployee employeeDetails : list) {
                if (!validateNIC(connection, employeeDetails.getComcode(), employeeDetails.getNic().trim())) {
                    return "Error:" + "This NIC already registered for EPF-" + employeeDetails.getEmpcode();
                }

                if (employeeDetails.getEmpcode() == 0) {
                    employeeDetails.setEpfno("");
                } else {
                    if (!validateEPF(connection, employeeDetails.getComcode().trim(), employeeDetails.getEmpcode())) {
                        return "Error:" + "This EPF already registered for EPF-" + employeeDetails.getEmpcode();
                    }
                    employeeDetails.setEpfno(getEpfRegNo(connection, employeeDetails.getComcode().trim()) + "/" + String.valueOf(employeeDetails.getEmpcode()));
                }

                if (employeeDetails.getGroupid() == 0) {
                    return "Error:" + "Invalid Wage Board for EPF-" + employeeDetails.getEmpcode();
                }

                if (employeeDetails.getCategoryid() == 0) {
                    return "Error:" + "Invalid Ocuupational Group for EPF-" + employeeDetails.getEmpcode();
                }

                if (employeeDetails.getDesignation().equalsIgnoreCase("Not Found")) {
                    return "Error:" + "Invalid Designation for EPF-" + employeeDetails.getEmpcode();
                }

                if (employeeDetails.getDivision().equalsIgnoreCase("Not Found")) {
                    return "Error:" + "Invalid Department for EPF-" + employeeDetails.getEmpcode();
                }
                String sql_emp_details = "INSERT INTO `employee_details`\n"
                        + "(`date`,`empcode`,`titel`,`initials`,`surname`,`empinitialname`,`empfullname`,`nic`,`datebirth`,`gender`,`empaddress`,`tele`,`email`,`status`,\n"
                        + "`dateappointment`,`probationenddate`,`groupid`,`categoryid`,`typofemp`,`division`,`designation`,`salarytyp`,`basicsalary`,`budgetallowance`,`bra2`,\n"
                        + "`allow1`,`allow2`,`allow3`,`allow4`,`allow5`,`deduct1`,`deduct2`,`deduct3`,`deduct4`,`deduct5`,`epfno`,`remarks`,`comcode`,`active`,`imgfilename`,`user`)\n"
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

                PreparedStatement ps_emp_details = connection.prepareStatement(sql_emp_details, Statement.RETURN_GENERATED_KEYS);
                ps_emp_details.setDate(1, new java.sql.Date(employeeDetails.getDate().getTime()));
                ps_emp_details.setInt(2, employeeDetails.getEmpcode());
                ps_emp_details.setString(3, employeeDetails.getTitel().trim());
                ps_emp_details.setString(4, employeeDetails.getInitials().trim());
                ps_emp_details.setString(5, employeeDetails.getSurname().trim());
                ps_emp_details.setString(6, employeeDetails.getEmpinitialname().trim());
                ps_emp_details.setString(7, employeeDetails.getEmpfullname().trim());
                ps_emp_details.setString(8, employeeDetails.getNic().trim());
                ps_emp_details.setDate(9, new java.sql.Date(employeeDetails.getDatebirth().getTime()));
                ps_emp_details.setInt(10, employeeDetails.getGender());
                ps_emp_details.setString(11, employeeDetails.getEmpaddress().trim());
                ps_emp_details.setString(12, employeeDetails.getTele().trim());
                ps_emp_details.setString(13, employeeDetails.getEmail().trim());
                ps_emp_details.setString(14, employeeDetails.getStatus().trim());
                ps_emp_details.setDate(15, new java.sql.Date(employeeDetails.getDateappointment().getTime()));
                ps_emp_details.setDate(16, new java.sql.Date(employeeDetails.getProbationenddate().getTime()));
                ps_emp_details.setInt(17, employeeDetails.getGroupid());
                ps_emp_details.setInt(18, employeeDetails.getCategoryid());
                ps_emp_details.setString(19, employeeDetails.getTypofemp().trim());
                ps_emp_details.setString(20, employeeDetails.getDivision().trim());
                ps_emp_details.setString(21, employeeDetails.getDesignation().trim());
                ps_emp_details.setString(22, "Normal");//employeeDetails.getSalarytyp()
                ps_emp_details.setBigDecimal(23, employeeDetails.getBasicsalary());
                ps_emp_details.setBigDecimal(24, employeeDetails.getBudgetallowance());
                ps_emp_details.setBigDecimal(25, employeeDetails.getBra2());
                ps_emp_details.setBigDecimal(26, employeeDetails.getAllow1());
                ps_emp_details.setBigDecimal(27, employeeDetails.getAllow2());
                ps_emp_details.setBigDecimal(28, employeeDetails.getAllow3());
                ps_emp_details.setBigDecimal(29, employeeDetails.getAllow4());
                ps_emp_details.setBigDecimal(30, employeeDetails.getAllow5());
                ps_emp_details.setBigDecimal(31, employeeDetails.getDeduct1());
                ps_emp_details.setBigDecimal(32, employeeDetails.getDeduct2());
                ps_emp_details.setBigDecimal(33, employeeDetails.getDeduct3());
                ps_emp_details.setBigDecimal(34, employeeDetails.getDeduct4());
                ps_emp_details.setBigDecimal(35, employeeDetails.getDeduct5());
                ps_emp_details.setString(36, employeeDetails.getEpfno().trim());
                ps_emp_details.setString(37, employeeDetails.getRemarks().trim());
                ps_emp_details.setString(38, BeanSystemLog.getComcode());
                ps_emp_details.setInt(39, 1);//employeeDetails.getActive()
                ps_emp_details.setString(40, employeeDetails.getImgfilename());
                ps_emp_details.setString(41, BeanSystemLog.getUser());
                ps_emp_details.executeUpdate();

                ResultSet keys = ps_emp_details.getGeneratedKeys();
                keys.next();
                empId = keys.getInt(1);

                employeeDetails.setEmpid(empId);

                ps_emp_details.close();

                BeanEmployeeBank empBank = employeeDetails.getBankDetails();
                empBank.setEmpid(empId);

                String sql_emp_bank = "INSERT INTO `employee_bank` (empid,bankid1,accno1,amount1,bankid2,accno2,amount2,user,comcode) VALUES(?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps_emp_bank = connection.prepareStatement(sql_emp_bank);
                ps_emp_bank.setInt(1, empBank.getEmpid());
                ps_emp_bank.setInt(2, empBank.getBankid1());
                ps_emp_bank.setString(3, empBank.getAccno1().trim());
                ps_emp_bank.setBigDecimal(4, empBank.getAmount1());
                ps_emp_bank.setInt(5, empBank.getBankid2());
                ps_emp_bank.setString(6, empBank.getAccno2().trim());
                ps_emp_bank.setBigDecimal(7, empBank.getAmount2());
                ps_emp_bank.setString(8, BeanSystemLog.getUser());
                ps_emp_bank.setString(9, BeanSystemLog.getComcode());
                ps_emp_bank.execute();
                ps_emp_bank.close();

                String sql_user = "INSERT INTO `user`(`empid`,`username`,`password`,`fullname`,`comcode`,`loccode`,`regdate`,`usergroup`,"
                        + "`email`,`mobileno`,`status`,`dataentryuser`)\n"
                        + "VALUES (?,?,md5(?),?,?,?,?,?,?,?,?,?);";
                PreparedStatement ps_allow = connection.prepareStatement(sql_user);
                ps_allow.setInt(1, employeeDetails.getEmpid());
                ps_allow.setString(2, employeeDetails.getEmail().trim());
                ps_allow.setString(3, employeeDetails.getNic().trim().concat(String.valueOf(employeeDetails.getEmpid())));
                ps_allow.setString(4, employeeDetails.getEmpinitialname().trim());
                ps_allow.setString(5, BeanSystemLog.getComcode());
                ps_allow.setString(6, "NA");
                ps_allow.setDate(7, new java.sql.Date(new Date().getTime()));
                ps_allow.setString(8, "Payslip User");
                ps_allow.setString(9, employeeDetails.getEmail().trim());
                ps_allow.setString(10, employeeDetails.getTele().trim());
                ps_allow.setInt(11, 1);
                ps_allow.setString(12, BeanSystemLog.getUser());
                ps_allow.execute();
                ps_allow.close();

                String saveQuery = "INSERT INTO `log`(`event`,`description`,`comcode`,`user`) VALUES (?,?,?,?);";
                PreparedStatement ps = connection.prepareStatement(saveQuery);
                ps.setString(1, "Save");
                ps.setString(2, "Employee profile : " + employeeDetails.getEmpid());
                ps.setString(3, BeanSystemLog.getComcode());
                ps.setString(4, BeanSystemLog.getUser());
                ps.execute();
                ps.close();

            }

            connection.commit();
            connection.setAutoCommit(true);

            return "Details saved";

        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
            return "Error:" + e.getLocalizedMessage();
        } finally {
            connection.close();
        }
    }
}
