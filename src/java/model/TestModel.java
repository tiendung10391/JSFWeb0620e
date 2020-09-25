/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Statement;
import util.ConnectionUtil;

/**
 *
 * @author Nguyễn Tiến Dũng
 */
public class TestModel extends ConnectionUtil {

    public static void main(String[] args) {
        try {
            TestModel test = new TestModel();
            test.insert();
        } catch (Exception e) {
        } finally {

        }
    }

    public void insert() throws Exception {
        String strSQL = "insert into admin_user(name, username, password, status) values (?, ?, ?, ?)";
        try {
            open();
            mStmt = mConnection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);
            mStmt.setString(1, "tien dung");
            mStmt.setString(2, "dungnt");
            mStmt.setString(3, "123456");
            mStmt.setString(4, "1");
            mStmt.executeUpdate();
            mRs = mStmt.getGeneratedKeys();
            if(mRs.next()){
                System.out.println("id: " + mRs.getInt(1));
            }
        } finally {
            close();
        }
    }
}
