/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.google.gson.JsonObject;
import dao.BeanLoan;
import dao.BeanLoanSettlement;
import dao.BeanSalaryMonth;
import daoImpls.ImplLoans;
import daoImpls.ImplSalaryMonth;
import interfaces.InterfaceLoans;
import interfaces.InterfaceSalaryMonth;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Nimesha
 */
public class ModelLoans {

    Connection connection;
    InterfaceLoans loans;
    InterfaceSalaryMonth month;
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    public ModelLoans(Connection connection) {
        this.connection = connection;
        this.loans = new ImplLoans();
        this.month = new ImplSalaryMonth();
    }

    public JSONArray getLoanList(String comCode, int loanStatus) throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanLoan> itemList = loans.getLoanDetailList(connection, comCode, loanStatus);
            if (itemList.isEmpty()) {
                LinkedHashMap mp = new LinkedHashMap();
                jsa.add(mp);
            } else {
                Iterator<BeanLoan> Listiterator = itemList.iterator();

                while (Listiterator.hasNext()) {
                    BeanLoan bean = Listiterator.next();
                    LinkedHashMap mp = new LinkedHashMap();
                    mp.put("loanid", bean.getLoanid());
                    mp.put("empid", bean.getEmpid());
                    mp.put("loanvalue", bean.getLoanvalue());
                    mp.put("typeofloan", bean.getTypeofloan());
                    mp.put("noofinstallments", bean.getNoofinstallments());
                    mp.put("monthlydeduction", bean.getMonthlydeduction());
                    mp.put("empcode", bean.getEmpcode());
                    mp.put("empname", bean.getEmpname());
                    mp.put("designation", bean.getDesignation());
                    mp.put("outstanding", bean.getOutstanding());
                    jsa.add(mp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONArray getLoanOutstandingList(String comCode, int empId) throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanLoan> itemList = loans.getLoanOutstandingList(connection, comCode, empId);
            Iterator<BeanLoan> Listiterator = itemList.iterator();

            while (Listiterator.hasNext()) {
                BeanLoan bean = Listiterator.next();
                LinkedHashMap mp = new LinkedHashMap();
                mp.put("loanid", bean.getLoanid());
                mp.put("empid", bean.getEmpid());
                mp.put("loanvalue", bean.getLoanvalue());
                mp.put("typeofloan", bean.getTypeofloan());
                mp.put("noofinstallments", bean.getNoofinstallments());
                mp.put("monthlydeduction", bean.getMonthlydeduction());
                mp.put("empcode", bean.getEmpcode());
                mp.put("empname", bean.getEmpname());
                mp.put("designation", bean.getDesignation());
                mp.put("outstanding", bean.getOutstanding());
                jsa.add(mp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONArray getSelectedSettlementList(int loanId) throws Exception {
        JSONArray jsa = new JSONArray();
        try {

            List<BeanLoanSettlement> itemList = loans.getSettlementList(connection, loanId);
            Iterator<BeanLoanSettlement> Listiterator = itemList.iterator();

            while (Listiterator.hasNext()) {
                BeanLoanSettlement bean = Listiterator.next();
                LinkedHashMap mp = new LinkedHashMap();
                mp.put("monthcode", bean.getMonthcode());
                mp.put("monthlyamount", bean.getMonthlyAmount());
                mp.put("paymode", bean.getPayMode());
                jsa.add(mp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsa;
    }

    public JSONArray getSelectedLoan(int loanId) throws Exception {

        BeanLoan bean = loans.getSelectedLoanData(connection, loanId);

        JSONArray jsa = new JSONArray();
        LinkedHashMap mp = new LinkedHashMap();
        mp.put("loanid", bean.getLoanid());
        mp.put("loandate", df.format(bean.getLoandate()));
        mp.put("loanvalue", bean.getLoanvalue());
        mp.put("reason", bean.getReason());
        mp.put("monthlydeduction", bean.getMonthlydeduction());
        mp.put("deductfrom", bean.getDeductfrom());
        mp.put("noofinstallments", bean.getNoofinstallments());
        mp.put("garanteeid1", bean.getGaranteeid1());
        mp.put("garanteeid2", bean.getGaranteeid2());
        mp.put("empid", bean.getEmpid());
        mp.put("typeofloan", bean.getTypeofloan());
        mp.put("setoff", bean.isSetoff());
        mp.put("outstanding", bean.getOutstanding());
        mp.put("settlementlist", bean.getSettlementList());

        jsa.add(mp);
        return jsa;
    }

    public JSONObject saveLoan(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();
        JsonObject loanData = requestJson.getAsJsonObject();

        DateFormat Dateformat_YYMMDD = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        BeanLoan bean = new BeanLoan();
        bean.setLoanid(loanData.get("loanid").getAsInt());
        bean.setLoandate(Dateformat_YYMMDD.parse(loanData.get("loandate").getAsString()));
        bean.setLoanvalue(loanData.get("loanvalue").getAsBigDecimal());
        bean.setReason(loanData.get("reason").getAsString());
        bean.setMonthlydeduction(loanData.get("monthlydeduction").getAsBigDecimal());
        bean.setDeductfrom(loanData.get("deductfrom").getAsString());//salary month
        bean.setNoofinstallments(loanData.get("noofinstallments").getAsInt());
        bean.setGaranteeid1(loanData.get("garanteeid1").getAsInt());
        bean.setGaranteeid2(loanData.get("garanteeid2").getAsInt());
        bean.setEmpid(loanData.get("empid").getAsInt());
        bean.setTypeofloan(loanData.get("typeofloan").getAsString());

        BeanSalaryMonth salaryMonth = month.getSelectedMonthStatus(connection, bean.getDeductfrom());
        if (salaryMonth.isRunpayroll()) {
            jsa.put("message", "Selected month is already closed");
        } else {
            String status = null;
            if (loanData.get("loanid").getAsInt() == 0) {
                status = loans.saveLoanData(bean);
            } else {
                status = loans.updateLoanData(bean);
            }

            if (status.startsWith("Error:")) {
                jsa.put("message", status);
                jsa.put("type", "error");
            } else {
                jsa.put("message", status);
                jsa.put("type", "sucess");
            }
        }

        return jsa;
    }

    public JSONObject settleLoan(JsonObject requestJson, Connection connection) throws Exception {
        JSONObject jsa = new JSONObject();
        JsonObject loanData = requestJson.getAsJsonObject();

        DateFormat Dateformat_YYMMDD = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        BeanLoan bean = new BeanLoan();
        bean.setLoanid(loanData.get("loanid").getAsInt());
        bean.setEmpid(loanData.get("empid").getAsInt());
        bean.setSettlementdate(Dateformat_YYMMDD.parse(loanData.get("settlementdate").getAsString()));
        bean.setSettlementamount(loanData.get("settlementamount").getAsBigDecimal());
        bean.setSettlementnote(loanData.get("settlementnote").getAsString());

        String status = null;
        if (loanData.get("loanid").getAsInt() > 0) {
            status = loans.settleLoan(bean);
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
}
