/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dao.BeanEmployee;
import dao.BeanEmployeeBank;
import dao.BeanSystemLog;
import daoImpls.ImplBank;
import daoImpls.ImplCategory;
import daoImpls.ImplDesignation;
import daoImpls.ImplDivision;
import daoImpls.ImplEmployeeProfile;
import daoImpls.ImplOccupationalGroup;
import interfaces.InterfaceBank;
import interfaces.InterfaceCategory;
import interfaces.InterfaceDesignation;
import interfaces.InterfaceDivision;
import java.sql.Connection;
import interfaces.InterfaceEmployeeProfile;
import interfaces.InterfaceOccupationalGroup;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Nimesha
 */
public class ModelEmployeeProfile {

    Connection connection;
    InterfaceEmployeeProfile employee;
    InterfaceOccupationalGroup occupationGroup;
    InterfaceCategory wageBoard;
    InterfaceBank bankData;
    InterfaceDesignation designation;
    InterfaceDivision division;
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    public ModelEmployeeProfile(Connection connection) {
        this.connection = connection;
        employee = new ImplEmployeeProfile();
        occupationGroup = new ImplOccupationalGroup();
        wageBoard = new ImplCategory();
        bankData = new ImplBank();
        designation = new ImplDesignation();
        division = new ImplDivision();
    }

    public JSONArray getSelectedEmpDetails(int empId, String comCode) throws Exception {

        BeanEmployee bean = employee.getSelectedEmployeeProfile(connection, empId, comCode);

        JSONArray jsa = new JSONArray();
        LinkedHashMap mp = new LinkedHashMap();
        mp.put("empid", bean.getEmpid());
        mp.put("date", df.format(bean.getDate()));
        mp.put("empcode", bean.getEmpcode());
        mp.put("titel", bean.getTitel());
        mp.put("initials", bean.getInitials());
        mp.put("surname", bean.getSurname());
        mp.put("empfullname", bean.getEmpfullname());
        mp.put("empinitialname", bean.getEmpinitialname());
        mp.put("nic", bean.getNic());
        mp.put("datebirth", df.format(bean.getDatebirth()));
        mp.put("gender", bean.getGender());
        mp.put("empaddress", bean.getEmpaddress());
        mp.put("tele", bean.getTele());
        mp.put("email", bean.getEmail());
        mp.put("status", bean.getStatus());
        mp.put("dateappointment", df.format(bean.getDateappointment()));
        mp.put("probationenddate", df.format(bean.getProbationenddate()));
        mp.put("groupid", bean.getGroupid());
        mp.put("categoryid", bean.getCategoryid());
        mp.put("typofemp", bean.getTypofemp());
        mp.put("division", bean.getDivision());
        mp.put("designation", bean.getDesignation());
        mp.put("salarytyp", bean.getSalarytyp());
        mp.put("basicsalary", bean.getBasicsalary());
        mp.put("budgetallowance1", bean.getBudgetallowance());
        mp.put("budgetallowance2", bean.getBra2());
        mp.put("allow1", bean.getAllow1());
        mp.put("allow2", bean.getAllow2());
        mp.put("allow3", bean.getAllow3());
        mp.put("allow4", bean.getAllow4());
        mp.put("allow5", bean.getAllow5());
        mp.put("deduct1", bean.getDeduct1());
        mp.put("deduct2", bean.getDeduct2());
        mp.put("deduct3", bean.getDeduct3());
        mp.put("deduct4", bean.getDeduct4());
        mp.put("deduct5", bean.getDeduct5());
        mp.put("epfno", bean.getEpfno());
        mp.put("remarks", bean.getRemarks());
        mp.put("active", bean.getActive());
        mp.put("comcode", bean.getComcode());
        mp.put("user", bean.getUser());
        mp.put("imgfilename", bean.getImgfilename());

        BeanEmployeeBank empBank = bean.getBankDetails();
        if (empBank != null) {
            mp.put("bankid1", empBank.getBankid1());
            mp.put("accno1", empBank.getAccno1());
            mp.put("amount1", empBank.getAmount1());
            mp.put("bankid2", empBank.getBankid2());
            mp.put("accno2", empBank.getAccno2());
            mp.put("amount2", empBank.getAmount2());
        }

        jsa.add(mp);
        return jsa;
    }

    public JSONArray getEmployeeList(String comCode) throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanEmployee> itemList = employee.getEmployeeList(connection, comCode);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                jsa.add(mp);
            } else {
                Iterator<BeanEmployee> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanEmployee bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("empid", bean.getEmpid());
                    mp.put("empinitialname", bean.getEmpinitialname());
                    mp.put("empcode", bean.getEmpcode());
                    mp.put("division", bean.getDivision());
                    mp.put("designation", bean.getDesignation());
                    mp.put("dateappointment", df.format(bean.getDateappointment()));
                    mp.put("datebirth", df.format(bean.getDatebirth()));
                    mp.put("gender", bean.getGender());
                    mp.put("active", bean.getActive());
                    mp.put("salary", bean.getSalary());
                    jsa.add(mp);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONArray getActiveEmployeeList(String comCode) throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanEmployee> itemList = employee.getActiveEmployeeList(connection, comCode);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                jsa.add(mp);
            } else {
                Iterator<BeanEmployee> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanEmployee bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("empid", bean.getEmpid());
                    mp.put("empinitialname", bean.getEmpinitialname());
                    mp.put("empcode", bean.getEmpcode());
                    mp.put("division", bean.getDivision());
                    mp.put("designation", bean.getDesignation());
                    mp.put("dateappointment", df.format(bean.getDateappointment()));
                    mp.put("datebirth", df.format(bean.getDatebirth()));
                    mp.put("gender", bean.getGender());
                    mp.put("active", bean.getActive());
                    mp.put("salary", bean.getSalary());
                    jsa.add(mp);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONArray getTerminateEmployeeList() throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanEmployee> itemList = employee.getTerminateEmployeeList(connection);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                jsa.add(mp);
            } else {
                Iterator<BeanEmployee> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanEmployee bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("empid", bean.getEmpid());
                    mp.put("empinitialname", bean.getEmpinitialname());
                    mp.put("empcode", bean.getEmpcode());
                    mp.put("division", bean.getDivision());
                    mp.put("dateappointment", df.format(bean.getDateappointment()));
                    mp.put("dateterminate", df.format(bean.getTerminateDate()));
                    mp.put("remark", bean.getTerminateNote());
                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONObject saveEmployeeProfile(JsonObject requestJson, Connection connection) throws Exception {

        JSONObject jsa = new JSONObject();

        BeanEmployee details = new BeanEmployee();
        BeanEmployeeBank empBank = new BeanEmployeeBank();
        DateFormat Dateformat_YYMMDD = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        JsonObject employeeDetails = requestJson.getAsJsonObject();

        details.setDate(new Date());
        details.setEpfAuto(employeeDetails.get("epfauto").getAsBoolean());
        details.setEmpid(employeeDetails.get("empid").getAsInt());
        details.setEmpcode(employeeDetails.get("empcode").getAsInt());
        details.setTitel(employeeDetails.get("titel").getAsString());
        details.setInitials(employeeDetails.get("initials").getAsString());
        details.setSurname(employeeDetails.get("surname").getAsString());
        details.setEmpinitialname(employeeDetails.get("empinitialname").getAsString());
        details.setEmpfullname(employeeDetails.get("empfullname").getAsString());
        details.setNic(employeeDetails.get("nic").getAsString());
        details.setDatebirth(Dateformat_YYMMDD.parse(employeeDetails.get("datebirth").getAsString()));
        details.setGender(employeeDetails.get("gender").getAsString().equals("Male") ? 1 : 0);
        details.setEmpaddress(employeeDetails.get("empaddress").getAsString());
        if (employeeDetails.get("tele") != null) {
            details.setTele(employeeDetails.get("tele").getAsString());
        } else {
            details.setTele("");
        }
        if (employeeDetails.get("email") != null) {
            details.setEmail(employeeDetails.get("email").getAsString());
        } else {
            details.setEmail("");
        }
        details.setStatus(employeeDetails.get("status").getAsString());
        details.setDateappointment(Dateformat_YYMMDD.parse(employeeDetails.get("dateappointment").getAsString()));
        if (employeeDetails.get("probationenddate") != null) {
            details.setProbationenddate(Dateformat_YYMMDD.parse(employeeDetails.get("probationenddate").getAsString()));
        } else {
            details.setProbationenddate(Dateformat_YYMMDD.parse("1900-01-01"));
        }
        details.setGroupid(employeeDetails.get("groupid").getAsInt());
        details.setCategoryid(employeeDetails.get("categoryid").getAsInt());
        details.setTypofemp(employeeDetails.get("typofemp").getAsString());
        details.setDivision(employeeDetails.get("division").getAsString());
        details.setDesignation(employeeDetails.get("designation").getAsString());
        details.setSalarytyp("Normal");
        if (employeeDetails.get("basicsalary") != null) {
            details.setBasicsalary(employeeDetails.get("basicsalary").getAsBigDecimal());
        } else {
            details.setBasicsalary(BigDecimal.ZERO);
        }
        if (employeeDetails.get("budgetallowance1") != null) {
            details.setBudgetallowance(employeeDetails.get("budgetallowance1").getAsBigDecimal());
        } else {
            details.setBudgetallowance(BigDecimal.ZERO);
        }
        if (employeeDetails.get("budgetallowance2") != null) {
            details.setBra2(employeeDetails.get("budgetallowance2").getAsBigDecimal());
        } else {
            details.setBra2(BigDecimal.ZERO);
        }
        if (employeeDetails.get("allow1") != null) {
            details.setAllow1(employeeDetails.get("allow1").getAsBigDecimal());
        } else {
            details.setAllow1(BigDecimal.ZERO);
        }
        if (employeeDetails.get("allow2") != null) {
            details.setAllow2(employeeDetails.get("allow2").getAsBigDecimal());
        } else {
            details.setAllow2(BigDecimal.ZERO);
        }
        if (employeeDetails.get("allow3") != null) {
            details.setAllow3(employeeDetails.get("allow3").getAsBigDecimal());
        } else {
            details.setAllow3(BigDecimal.ZERO);
        }
        if (employeeDetails.get("allow4") != null) {
            details.setAllow4(employeeDetails.get("allow4").getAsBigDecimal());
        } else {
            details.setAllow4(BigDecimal.ZERO);
        }
        if (employeeDetails.get("allow5") != null) {
            details.setAllow5(employeeDetails.get("allow5").getAsBigDecimal());
        } else {
            details.setAllow5(BigDecimal.ZERO);
        }
        if (employeeDetails.get("deduct1") != null) {
            details.setDeduct1(employeeDetails.get("deduct1").getAsBigDecimal());
        } else {
            details.setDeduct1(BigDecimal.ZERO);
        }
        if (employeeDetails.get("deduct2") != null) {
            details.setDeduct2(employeeDetails.get("deduct2").getAsBigDecimal());
        } else {
            details.setDeduct2(BigDecimal.ZERO);
        }
        if (employeeDetails.get("deduct3") != null) {
            details.setDeduct3(employeeDetails.get("deduct3").getAsBigDecimal());
        } else {
            details.setDeduct3(BigDecimal.ZERO);
        }
        if (employeeDetails.get("deduct4") != null) {
            details.setDeduct4(employeeDetails.get("deduct4").getAsBigDecimal());
        } else {
            details.setDeduct4(BigDecimal.ZERO);
        }
        if (employeeDetails.get("deduct5") != null) {
            details.setDeduct5(employeeDetails.get("deduct5").getAsBigDecimal());
        } else {
            details.setDeduct5(BigDecimal.ZERO);
        }
        /*if (employeeDetails.get("epfno") == null || employeeDetails.get("epfno").getAsString().equals("") || employeeDetails.get("epfno").getAsString().equals("0") || employeeDetails.get("epfno").getAsString().equals("<<Auto>>")) {
            details.setEpfno("");
            details.setEmpcode(0);
        } else {
            details.setEpfno(employeeDetails.get("epfno").getAsString());
            details.setEmpcode(employeeDetails.get("empcode").getAsInt());
        }*/
        if (employeeDetails.get("remarks") != null) {
            details.setRemarks(employeeDetails.get("remarks").getAsString());
        } else {
            details.setRemarks("");
        }
        if (employeeDetails.get("imgfilename") != null) {
            details.setImgfilename(employeeDetails.get("imgfilename").getAsString());
        } else {
            details.setImgfilename("");
        }

        details.setActive(1);
        details.setUser(BeanSystemLog.getUser());
        details.setComcode(BeanSystemLog.getComcode());

        empBank.setEmpid(employeeDetails.get("empid").getAsInt());
        if (employeeDetails.get("bankid1") != null) {
            empBank.setBankid1(employeeDetails.get("bankid1").getAsInt());
        } else {
            empBank.setBankid1(0);
        }
        if (employeeDetails.get("accno1") != null) {
            empBank.setAccno1(employeeDetails.get("accno1").getAsString());
        } else {
            empBank.setAccno1("-");
        }
        if (employeeDetails.get("amount1") != null) {
            empBank.setAmount1(employeeDetails.get("amount1").getAsBigDecimal());
        } else {
            empBank.setAmount1(BigDecimal.ZERO);
        }
        System.out.print("bankid2 " + employeeDetails.get("bankid2"));
        if (employeeDetails.get("bankid2") != null) {
            empBank.setBankid2(employeeDetails.get("bankid2").getAsInt());
        } else {
            empBank.setBankid2(0);
        }

        if (employeeDetails.get("accno2") != null) {
            empBank.setAccno2(employeeDetails.get("accno2").getAsString());
        } else {
            empBank.setAccno2("-");
        }
        if (employeeDetails.get("amount2") != null) {
            empBank.setAmount2(employeeDetails.get("amount2").getAsBigDecimal());
        } else {
            empBank.setAmount2(BigDecimal.ZERO);
        }
        empBank.setUser(BeanSystemLog.getUser());
        empBank.setComcode(BeanSystemLog.getComcode());
        details.setBankDetails(empBank);

        String status = null;
        if (employeeDetails.get("empid").getAsInt() != 0) {
            status = employee.updateEmployeeProfile(details);
        } else {
            status = employee.saveEmployeeProfile(details);
        }

        if (status.startsWith("Error:")) {
            jsa.put("message", status);
            jsa.put("type", "error");
        } else {
            jsa.put("message", status);
            jsa.put("type", "sucess");
        }

        return jsa;
    }

    public JSONObject saveEmpExcel(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        JsonObject empData = requestJson.getAsJsonObject();
        JsonArray empExcelData = empData.get("excelData").getAsJsonArray();

        List<BeanEmployee> list = new ArrayList<>();
        for (JsonElement jsonElement : empExcelData) {

            JsonObject employeeDetails = jsonElement.getAsJsonObject();

            String bdateText = employeeDetails.get("Birth Date").getAsString();
            ZonedDateTime z1 = ZonedDateTime.parse(bdateText);
            LocalDate bdate = z1.toLocalDate().plusDays(1);
            Date date1 = java.sql.Date.valueOf(bdate);
            String formattedBday = sdf.format(date1);

            String appdateText = employeeDetails.get("Appoiment Date").getAsString();
            ZonedDateTime z2 = ZonedDateTime.parse(appdateText);
            LocalDate appdate = z2.toLocalDate().plusDays(1);
            Date date2 = java.sql.Date.valueOf(appdate);
            String formattedAppday = sdf.format(date2);

            String formattedProdate = null;
            if (employeeDetails.get("Probation End Date") != null) {
                String prodateText = employeeDetails.get("Probation End Date").getAsString();
                ZonedDateTime z3 = ZonedDateTime.parse(prodateText);
                LocalDate prodate = z3.toLocalDate().plusDays(1);
                Date date3 = java.sql.Date.valueOf(prodate);
                formattedProdate = sdf.format(date3);
            } else {
                formattedProdate = "1900-01-01";
            }

            BeanEmployee details = new BeanEmployee();

            details.setDatebirth(sdf.parse(formattedBday));
            details.setDateappointment(sdf.parse(formattedAppday));
            details.setProbationenddate(sdf.parse(formattedProdate));
            details.setDate(new Date());
            details.setEpfAuto(false);
            details.setEmpcode(employeeDetails.get("EPF").getAsInt());
            details.setTitel(employeeDetails.get("Title").getAsString());
            details.setInitials(employeeDetails.get("Initials").getAsString());
            details.setSurname(employeeDetails.get("Surname").getAsString());
            details.setEmpinitialname(employeeDetails.get("Initials").getAsString() + " " + employeeDetails.get("Surname").getAsString());
            details.setEmpfullname(employeeDetails.get("Fullname").getAsString());
            details.setNic(employeeDetails.get("NIC").getAsString());
            details.setGender(employeeDetails.get("Gender").getAsString().equals("Male") ? 1 : 0);
            details.setEmpaddress(employeeDetails.get("Address").getAsString());
            if (employeeDetails.get("TP No") != null) {
                details.setTele(employeeDetails.get("TP No").getAsString());
            } else {
                details.setTele("");
            }
            if (employeeDetails.get("Email") != null) {
                details.setEmail(employeeDetails.get("Email").getAsString());
            } else {
                details.setEmail("");
            }
            details.setStatus(employeeDetails.get("Emp Status").getAsString());
            details.setGroupid(occupationGroup.getOccupationalId(connection, employeeDetails.get("Occupational Group").getAsString()));
            details.setCategoryid(wageBoard.getCategoryId(connection, employeeDetails.get("Wage Board").getAsString()));
            details.setTypofemp(employeeDetails.get("Emp Type").getAsString());
            details.setDivision(division.validateDivision(connection, employeeDetails.get("Department").getAsString()));
            details.setDesignation(designation.validateDesignation(connection, employeeDetails.get("Designation").getAsString()));
            details.setSalarytyp("Normal");
            if (employeeDetails.get("Basic Salary") != null) {
                details.setBasicsalary(employeeDetails.get("Basic Salary").getAsBigDecimal());
            } else {
                details.setBasicsalary(BigDecimal.ZERO);
            }
            if (employeeDetails.get("Budget Allowance1") != null) {
                details.setBudgetallowance(employeeDetails.get("Budget Allowance1").getAsBigDecimal());
            } else {
                details.setBudgetallowance(BigDecimal.ZERO);
            }
            if (employeeDetails.get("BRA2") != null) {
                details.setBra2(employeeDetails.get("BRA2").getAsBigDecimal());
            } else {
                details.setBra2(BigDecimal.ZERO);
            }
            if (employeeDetails.get("Allow1") != null) {
                details.setAllow1(employeeDetails.get("Allow1").getAsBigDecimal());
            } else {
                details.setAllow1(BigDecimal.ZERO);
            }
            if (employeeDetails.get("Allow2") != null) {
                details.setAllow2(employeeDetails.get("Allow2").getAsBigDecimal());
            } else {
                details.setAllow2(BigDecimal.ZERO);
            }
            if (employeeDetails.get("Allow3") != null) {
                details.setAllow3(employeeDetails.get("Allow3").getAsBigDecimal());
            } else {
                details.setAllow3(BigDecimal.ZERO);
            }
            if (employeeDetails.get("Allow4") != null) {
                details.setAllow4(employeeDetails.get("Allow4").getAsBigDecimal());
            } else {
                details.setAllow4(BigDecimal.ZERO);
            }
            if (employeeDetails.get("Allow5") != null) {
                details.setAllow5(employeeDetails.get("Allow5").getAsBigDecimal());
            } else {
                details.setAllow5(BigDecimal.ZERO);
            }
            if (employeeDetails.get("Deduct1") != null) {
                details.setDeduct1(employeeDetails.get("Deduct1").getAsBigDecimal());
            } else {
                details.setDeduct1(BigDecimal.ZERO);
            }
            if (employeeDetails.get("Deduct2") != null) {
                details.setDeduct2(employeeDetails.get("Deduct2").getAsBigDecimal());
            } else {
                details.setDeduct2(BigDecimal.ZERO);
            }
            if (employeeDetails.get("Deduct3") != null) {
                details.setDeduct3(employeeDetails.get("Deduct3").getAsBigDecimal());
            } else {
                details.setDeduct3(BigDecimal.ZERO);
            }
            if (employeeDetails.get("Deduct4") != null) {
                details.setDeduct4(employeeDetails.get("Deduct4").getAsBigDecimal());
            } else {
                details.setDeduct4(BigDecimal.ZERO);
            }
            if (employeeDetails.get("Deduct5") != null) {
                details.setDeduct5(employeeDetails.get("Deduct5").getAsBigDecimal());
            } else {
                details.setDeduct5(BigDecimal.ZERO);
            }
            if (employeeDetails.get("Active") != null) {
                details.setActive(employeeDetails.get("Active").getAsInt());
            } else {
                details.setActive(1);
            }

            details.setRemarks("");
            details.setImgfilename("");
            details.setUser(BeanSystemLog.getUser());
            details.setComcode(employeeDetails.get("Company").getAsString());

            BeanEmployeeBank empBank = new BeanEmployeeBank();
            if (employeeDetails.get("SWIFTCODE") != null) {
                empBank.setBankid1(bankData.getBankId(connection, employeeDetails.get("SWIFTCODE").getAsString()));
                empBank.setAccno1(employeeDetails.get("AccountNo").getAsString());
                empBank.setAmount1(BigDecimal.ZERO);
            } else {
                empBank.setBankid1(0);
                empBank.setAccno1(employeeDetails.get("AccountNo").getAsString());
                empBank.setAmount1(BigDecimal.ZERO);

            }
            empBank.setBankid2(0);
            empBank.setAccno2("-");
            empBank.setAmount2(BigDecimal.ZERO);
            empBank.setUser(BeanSystemLog.getUser());
            empBank.setComcode(employeeDetails.get("Company").getAsString());
            details.setBankDetails(empBank);

            list.add(details);
        }

        String status = null;
        status = employee.saveEmpDataExcel(list);

        if (status.startsWith("Error:")) {
            jsa.put("message", status);
            jsa.put("type", "error");
        } else {
            jsa.put("message", status);
            jsa.put("type", "sucess");
        }

        return jsa;
    }

    public JSONObject terminateEmployee(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();

        JsonObject invData = requestJson.getAsJsonObject();
        int empId = invData.get("empId").getAsInt();
        String terminateNote = invData.get("terminateNote").getAsString();

        String status = employee.terminateEmployee(empId, terminateNote);

        if (status.startsWith("Error:")) {
            jsa.put("message", status);
            jsa.put("type", "error");
        } else {
            jsa.put("message", status);
            jsa.put("type", "sucess");
        }

        return jsa;
    }
}
