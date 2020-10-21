/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.ChuyenNganh;
import entity.NgayLamViec;
import entity.NhanVien;
import entity.Phong;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.ConnectionUtil;
import util.DateUtils;

/**
 *
 * @author Nguyễn Tiến Dũng
 */
public class PhongBanModel extends ConnectionUtil {

    public List<Phong> getListPhongBan() throws Exception {
        List<Phong> lstReturn = new ArrayList<>();
        String strSQL = "select a.* \n"
                + "from phong as a \n"
                + "where a.status = '1'\n"
                + "order by a.chuyen_nganh_id";
        try {
            open();
            mStmt = mConnection.prepareStatement(strSQL);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                Phong phong = new Phong();
                phong.setId(mRs.getLong("id"));
                phong.setTenPhong(mRs.getString("ten_phong"));
                phong.setMaPhong(mRs.getString("ma_phong"));
                phong.setChgWho(mRs.getString("chg_who"));
                phong.setChgDate(mRs.getTimestamp("chg_date"));
                phong.setStatus(mRs.getString("status"));
                phong.setChuyenNganhId(mRs.getLong("chuyen_nganh_id"));
//                phong.setTenNganh(mRs.getString("ten_nganh"));
                lstReturn.add(phong);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
        return lstReturn;
    }

    public List<ChuyenNganh> getListChuyenNganh() throws Exception {
        List<ChuyenNganh> lstChuyenNganh = new ArrayList<>();
        String strSQL = "select * from chuyen_nganh";
        try {
            open();
            mStmt = mConnection.prepareStatement(strSQL);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                ChuyenNganh chuyenNganh = new ChuyenNganh();
                chuyenNganh.setId(mRs.getLong("id"));
                chuyenNganh.setTenNganh(mRs.getString("ten_nganh"));
                lstChuyenNganh.add(chuyenNganh);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
        return lstChuyenNganh;
    }

    public List<NgayLamViec> getListNgayLamViec() throws Exception {
        List<NgayLamViec> lstNgayLamViec = new ArrayList<>();
        String strSQL = "select * from ngay_lam_viec";
        try {
            open();
            mStmt = mConnection.prepareStatement(strSQL);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                NgayLamViec ngatLamViec = new NgayLamViec();
                ngatLamViec.setId(mRs.getLong("id"));
                ngatLamViec.setName(mRs.getString("name"));
                lstNgayLamViec.add(ngatLamViec);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
        return lstNgayLamViec;
    }

    public void insert(Phong phong) throws Exception {
        String strSQL = "insert into phong(ten_phong, ma_phong, chg_who, chg_date, status, chuyen_nganh_id) \n"
                + "  values (?,?,?,sysdate(),'1',?)";
        try {
            open();
            mStmt = mConnection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);
            mStmt.setString(1, phong.getTenPhong());
            mStmt.setString(2, phong.getMaPhong());
            mStmt.setString(3, phong.getChgWho());
            mStmt.setLong(4, phong.getChuyenNganhId());
            mStmt.executeUpdate();
            mRs = mStmt.getGeneratedKeys();
            if (mRs.next()) {
                phong.setId(mRs.getInt(1));
                phong.setStatus("1");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    public void insertNhanVien(NhanVien nhanVien) throws Exception {
        PreparedStatement stmtMap = null;
        String strSQL = "insert into nhan_vien(ten, dia_chi, luong, ngay_sinh, dien_thoai, id_phong)\n"
                + "values (?, ? , ?, ?, ?, ?)";
        String strSQLMap = "insert into ngay_lam_viec_map values (?, ?)";
        try {
            open();
            mStmt = mConnection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);
            mStmt.setString(1, nhanVien.getTen());
            mStmt.setString(2, nhanVien.getDiaChi());
            mStmt.setDouble(3, nhanVien.getLuong());
            mStmt.setObject(4, DateUtils.getSQLDate(nhanVien.getNgaySinh()));
            mStmt.setString(5, nhanVien.getDienThoai());
            mStmt.setLong(6, nhanVien.getIdPhong());
            mStmt.executeUpdate();
            mRs = mStmt.getGeneratedKeys();
            if (mRs.next()) {
                nhanVien.setId(mRs.getInt(1));
            }
            stmtMap = mConnection.prepareStatement(strSQLMap);
            for (int i = 0; i < nhanVien.getLstNgayLamViec().size(); i++) {
                stmtMap.setLong(1, nhanVien.getId());
                stmtMap.setLong(2, nhanVien.getLstNgayLamViec().get(i));
                stmtMap.execute();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    public void update(Phong phong) throws Exception {
        String strSQL = "update phong set ten_phong = ?, ma_phong = ? , chg_who = ?, chg_date = sysdate(), chuyen_nganh_id = ? where id = ? and status = '1'";
        try {
            open();
            mStmt = mConnection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);
            mStmt.setString(1, phong.getTenPhong());
            mStmt.setString(2, phong.getMaPhong());
            mStmt.setString(3, phong.getChgWho());
            mStmt.setLong(4, phong.getChuyenNganhId());
            mStmt.setLong(5, phong.getId());
            int check = mStmt.executeUpdate();
            if (check != 1) {
                throw new Exception("Trạng thái cập nhật không hợp lệ");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    public void remove(Phong phong) throws Exception {
        String strSQL = "update phong set status = '0' , chg_who = ?, chg_date = sysdate() where id = ? and status = '1'";
        try {
            open();
            mStmt = mConnection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);
            mStmt.setString(1, phong.getChgWho());
            mStmt.setLong(2, phong.getId());
            int check = mStmt.executeUpdate();
            if (check != 1) {
                throw new Exception("Trạng thái cập nhật không hợp lệ");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    public List<NhanVien> getListNhanVienById(long idPhong) throws Exception {
        PreparedStatement stmtMap = null;
        ResultSet rsMap = null;
        List<NhanVien> lstReturn = new ArrayList<>();
        String strSQL = "select * from nhan_vien where id_phong  = ? order by ten";
        String strSQLMap = "select * from ngay_lam_viec_map where id_nhan_vien = ?";
        try {
            open();
            mStmt = mConnection.prepareStatement(strSQL);
            mStmt.setLong(1, idPhong);
            mRs = mStmt.executeQuery();
            while (mRs.next()) {
                NhanVien nv = new NhanVien();
                nv.setId(mRs.getLong("id"));
                nv.setTen(mRs.getString("ten"));
                nv.setDiaChi(mRs.getString("dia_chi"));
                nv.setLuong(mRs.getLong("luong"));
                nv.setNgaySinh(mRs.getTimestamp("ngay_sinh"));
                nv.setIdPhong(mRs.getLong("id_phong"));
                nv.setDienThoai(mRs.getString("dien_thoai"));
                stmtMap = mConnection.prepareCall(strSQLMap);
                stmtMap.setLong(1, nv.getId());
                rsMap = stmtMap.executeQuery();
                List<Long> lstNgayLamViec = new ArrayList<>();
                while (rsMap.next()) {
                    lstNgayLamViec.add(rsMap.getLong("id_ngay_lam_viec"));
                }
                nv.setLstNgayLamViec(lstNgayLamViec);
                lstReturn.add(nv);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
        return lstReturn;
    }
}
