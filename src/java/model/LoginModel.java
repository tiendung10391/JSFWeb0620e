/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import util.ConnectionUtil;

/**
 *
 * @author Nguyễn Tiến Dũng
 */
public class LoginModel extends ConnectionUtil {

    public boolean checkLogin(String username, String password) throws Exception {
        boolean blCheck = false;
        String strSQL = "select * from admin_user where username = ? and password = ? and status = '1' ";
        try {
            open();
            mStmt = mConnection.prepareStatement(strSQL);
            mStmt.setString(1, username);
            mStmt.setString(2, password);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                blCheck = true;
            }
        } finally {
            close();
        }
        return blCheck;
    }
}
