/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nguyễn Tiến Dũng
 */
public class NhanVien implements Serializable{
    private long id;
    private String ten;
    private String diaChi;
    private double luong;
    private Date ngaySinh;
    private long idPhong;
    private String dienThoai;
    private String tenPhong;
    private List<Long> lstNgayLamViec;

    public NhanVien() {
        lstNgayLamViec = new ArrayList<>();
    }

    public NhanVien(NhanVien obj) {
        this.id = obj.id;
        this.ten = obj.ten;
        this.diaChi = obj.diaChi;
        this.luong = obj.luong;
        this.ngaySinh = obj.ngaySinh;
        this.idPhong = obj.idPhong;
        this.dienThoai = obj.dienThoai;
        this.tenPhong = obj.tenPhong;
        this.lstNgayLamViec = obj.lstNgayLamViec;
    }

    public List<Long> getLstNgayLamViec() {
        return lstNgayLamViec;
    }

    public void setLstNgayLamViec(List<Long> lstNgayLamViec) {
        this.lstNgayLamViec = lstNgayLamViec;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public double getLuong() {
        return luong;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public long getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(long idPhong) {
        this.idPhong = idPhong;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }
    
    
    
}
