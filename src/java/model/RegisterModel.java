/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.User;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import util.ConnectionUtil;
import util.DateUtils;

/**
 *
 * @author Nguyễn Tiến Dũng
 */
public class RegisterModel extends ConnectionUtil {

    public List<User> getListUser() throws Exception {
        List<User> lstReturn = new ArrayList<>();
        String strSQL = "select * from admin_user";
        try {
            open();
            mStmt = mConnection.prepareStatement(strSQL);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                User user = new User();
                user.setId(mRs.getLong("id"));
                user.setName(mRs.getString("name"));
                user.setUsername(mRs.getString("username"));
                user.setPassword(mRs.getString("password"));
                user.setPhoneNumber(mRs.getString("phone_number"));
                user.setEmail(mRs.getString("email"));
                user.setBirtDay(mRs.getDate("birt_day"));
                user.setGender(mRs.getInt("gender"));
                user.setDocType(mRs.getString("doc_type"));
                user.setIdNo(mRs.getString("id_no"));
                user.setIdIssueDate(mRs.getDate("issue_date"));
                user.setIdIssuePlace(mRs.getString("id_issue_place"));
                user.setAddress(mRs.getString("address"));
                user.setDescription(mRs.getString("description"));
                user.setChgWho(mRs.getString("chg_who"));
                user.setChgDate(mRs.getDate("chg_date"));
                user.setStatus(mRs.getString("status"));
                lstReturn.add(user);
            }
        } finally {
            close();
        }
        return lstReturn;
    }

    public void add(User user) throws Exception {
        String strSQL = "insert into  admin_user\n"
                + "(name, \n"
                + "username,\n"
                + " password, \n"
                + " phone_number, \n"
                + " email, \n"
                + " birt_day, \n"
                + " gender, \n"
                + " doc_type, \n"
                + " id_no, \n"
                + "issue_date, \n"
                + "id_issue_place, \n"
                + "address, \n"
                + "description, \n"
                + "chg_who, \n"
                + "chg_date, \n"
                + "status)\n"
                + "values (?, ?, ?, ?, ?, STR_TO_DATE(?, '%d/%m/%Y'), \n"
                + "?, ?, ?, STR_TO_DATE(?, '%d/%m/%Y'),?,?,?, ?,STR_TO_DATE(?, '%d/%m/%Y'), ?)";
        try {
            open();
            mStmt = mConnection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);
            mStmt.setString(1, user.getName());
            mStmt.setString(2, user.getUsername());
            mStmt.setString(3, user.getPassword());
            mStmt.setString(4, user.getPhoneNumber());
            mStmt.setString(5, user.getEmail());
            mStmt.setString(6, DateUtils.convertDate(user.getBirtDay(), "dd/MM/yyyy"));
            mStmt.setInt(7, user.getGender());
            mStmt.setString(8, user.getDocType());
            mStmt.setString(9, user.getIdNo());
            mStmt.setString(10, DateUtils.convertDate(user.getIdIssueDate(), "dd/MM/yyyy"));
            mStmt.setString(11, user.getIdIssuePlace());
            mStmt.setString(12, user.getAddress());
            mStmt.setString(13, user.getDescription());
            mStmt.setString(14, user.getChgWho());
            mStmt.setString(15, DateUtils.convertDate(user.getChgDate(), "dd/MM/yyyy"));
            mStmt.setString(16, user.getStatus());
            mStmt.executeUpdate();
            mRs = mStmt.getGeneratedKeys();
            if(mRs.next()){
                user.setId(mRs.getInt(1));
            }
        } finally {
            close();
        }
    }
    
    public void update(User user) throws Exception{
        String strSQL = "update  \n" +
                "	admin_user\n" +
                "   SET	name = ?, username = ?, password = ?, phone_number = ?, email = ?, \n" +
                "	birt_day = STR_TO_DATE(?, '%d/%m/%Y'), gender = ?, doc_type = ?, id_no = ?, \n" +
                "	issue_date = STR_TO_DATE(?, '%d/%m/%Y'), id_issue_place = ?, address = ?, \n" +
                "	description = ?, chg_who = ?, chg_date = STR_TO_DATE(?, '%d/%m/%Y') \n" +
                "   where id = ? and status  = '1'";
        try {
            open();
            mStmt = mConnection.prepareStatement(strSQL);
            mStmt.setString(1, user.getName());
            mStmt.setString(2, user.getUsername());
            mStmt.setString(3, user.getPassword());
            mStmt.setString(4, user.getPhoneNumber());
            mStmt.setString(5, user.getEmail());
            mStmt.setString(6, DateUtils.convertDate(user.getBirtDay(), "dd/MM/yyyy"));
            mStmt.setInt(7, user.getGender());
            mStmt.setString(8, user.getDocType());
            mStmt.setString(9, user.getIdNo());
            mStmt.setString(10, DateUtils.convertDate(user.getIdIssueDate(), "dd/MM/yyyy"));
            mStmt.setString(11, user.getIdIssuePlace());
            mStmt.setString(12, user.getAddress());
            mStmt.setString(13, user.getDescription());
            mStmt.setString(14, user.getChgWho());
            mStmt.setString(15, DateUtils.convertDate(user.getChgDate(), "dd/MM/yyyy"));
            mStmt.setLong(16, user.getId());
            int check = mStmt.executeUpdate();
            if(check == 0){
                throw new Exception("Trạng thái cập nhật không hợp lệ");
            }
        }finally{
            close();
        }
    }
    
    public void remove(User user) throws Exception{
        String strSQL = "DELETE FROM admin_user where id = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(strSQL);
            mStmt.setLong(1, user.getId());
            mStmt.executeUpdate();
        } finally{
            close();
        }
    }
}
