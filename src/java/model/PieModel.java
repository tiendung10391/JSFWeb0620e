/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Chart;
import java.util.ArrayList;
import java.util.List;
import util.ConnectionUtil;

/**
 *
 * @author Nguyễn Tiến Dũng
 */
public class PieModel extends ConnectionUtil {

    public List<Chart> getListPieChart() throws Exception {
        List<Chart> lstReturn = new ArrayList<>();
        String strSQL = "select sum(luong) as tong_luong, (select ten_phong from phong where id = id_phong) as ten_phong from nhan_vien group by id_phong";
        try {
            open();
            mStmt = mConnection.prepareCall(strSQL);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Chart ett = new Chart(mRs.getDouble("tong_luong"), mRs.getString("ten_phong"));
                lstReturn.add(ett);
            }
        } finally {
            close();
        }
        return lstReturn;
    }
}
