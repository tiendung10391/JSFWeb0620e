/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Nguyễn Tiến Dũng
 */
public class Phong implements Serializable{
    private long id;
    private String tenPhong;
    private String maPhong;
    private String chgWho;
    private Date chgDate;
    private String status;
    private long chuyenNganhId;
    private String tenNganh;

    public Phong() {
    }

    public Phong(Phong obj) {
        this.id = obj.id;
        this.tenPhong = obj.tenPhong;
        this.maPhong = obj.maPhong;
        this.chgWho = obj.chgWho;
        this.chgDate = obj.chgDate;
        this.status = obj.status;
        this.chuyenNganhId = obj.chuyenNganhId;
        this.tenNganh = obj.tenNganh;
    }
    
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getChgWho() {
        return chgWho;
    }

    public void setChgWho(String chgWho) {
        this.chgWho = chgWho;
    }

    public Date getChgDate() {
        return chgDate;
    }

    public void setChgDate(Date chgDate) {
        this.chgDate = chgDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getChuyenNganhId() {
        return chuyenNganhId;
    }

    public void setChuyenNganhId(long chuyenNganhId) {
        this.chuyenNganhId = chuyenNganhId;
    }

    public String getTenNganh() {
        return tenNganh;
    }

    public void setTenNganh(String tenNganh) {
        this.tenNganh = tenNganh;
    }
    
    
}
