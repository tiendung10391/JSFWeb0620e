/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.ChuyenNganh;
import entity.NgayLamViec;
import entity.NhanVien;
import entity.Phong;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.PhongBanModel;
import org.primefaces.event.SelectEvent;
import util.ActionInterface;
import util.ActionUtil;
import util.DateUtils;
import util.MessagesUtils;

/**
 *
 * @author Nguyễn Tiến Dũng
 */
@ManagedBean
@ViewScoped
public class PhongBanController extends ActionUtil implements ActionInterface, Serializable {

    private PhongBanModel phongBanModel;
    private List<Phong> mlstPhong;
    private Phong phongSelected;
    private List<ChuyenNganh> mlstChuyenNganhs;
    private List<NgayLamViec> mlstNgayLamViec;
    private Phong phongBan;
    private List<NhanVien> mlstNhanVien;
    private boolean isPhongBan;
    private boolean isNhanVien;
    private NhanVien nhanVien;

    /**
     * Creates a new instance of PhongBanController
     */
    public PhongBanController() {
        try {
            phongBan = new Phong();
            nhanVien = new NhanVien();
            phongBanModel = new PhongBanModel();
            mlstPhong = phongBanModel.getListPhongBan();
            mlstChuyenNganhs = phongBanModel.getListChuyenNganh();
            mlstNgayLamViec = phongBanModel.getListNgayLamViec();
            isPhongBan = false;
            isNhanVien = false;
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    @Override
    public void handSave() {
        try {
            phongBan.setChgWho(LoginController.getUserLogin());
            if (isAdd) {
                insertOrCopy();
            } else if (isCopy) {
                insertOrCopy();
            } else if (isEdit) {
                phongBanModel.update(phongBan);
            }
            handCancel();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void insertOrCopy() throws Exception {
        if (isPhongBan) {
            //insert them moi phong ban
            phongBanModel.insert(phongBan);
            mlstPhong.add(phongBan);
        } else if (isNhanVien) {
            //insert them moi nhan vien
            phongBanModel.insertNhanVien(nhanVien);
            mlstNhanVien.add(nhanVien);
        }
    }

    public void handDelete(Phong phong) {
        try {
            phong.setChgWho(LoginController.getUserLogin());
            phongBanModel.remove(phong);
            mlstPhong.remove(phong);
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void onRowSelect(SelectEvent<Phong> event) {
        try {
            mlstNhanVien = phongBanModel.getListNhanVienById(event.getObject().getId());
            nhanVien = new NhanVien();
            nhanVien.setIdPhong(event.getObject().getId());
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void changeStateAddPhongBan() {
        try {
            super.changeStateAdd();
            isPhongBan = true;
            isNhanVien = false;
            phongBan = new Phong();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void changeStateAddNhanVien() {
        try {
            super.changeStateAdd();
            isPhongBan = false;
            isNhanVien = true;
            long idPhong = nhanVien.getIdPhong();
            nhanVien = new NhanVien();
            nhanVien.setIdPhong(idPhong);
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public String getTenPhongTheoId(long idPhong) {
        try {
            if (mlstPhong != null && !mlstPhong.isEmpty()) {
                for (Phong phong : mlstPhong) {
                    if (phong.getId() == idPhong) {
                        return phong.getTenPhong();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
        return "";
    }

    public String getTenChuyenNganh(long id) {
        try {
            if (mlstChuyenNganhs != null && !mlstChuyenNganhs.isEmpty()) {
                for (ChuyenNganh chuyenNganh : mlstChuyenNganhs) {
                    if (chuyenNganh.getId() == id) {
                        return chuyenNganh.getTenNganh();
                    }
                }
            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
        return "";
    }

    public String getNgaySinhView(Date dtNgaySinh) {
        try {
            if (dtNgaySinh != null) {
                return DateUtils.convertDate(dtNgaySinh, "dd/MM/yyyy");
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
        return "";
    }

    public void changeStateEdit(Phong phong) {
        super.changeStateEdit();
        phongBan = phong;
    }

    public void changeStateCopy(Phong phong) {
        super.changeStateCopy();
        phongBan = new Phong(phong);
    }

    public void changeStateView(Phong phong) {
        super.changeStateView();
        phongBan = new Phong(phong);
    }

    public void changeStateViewNhanVien(NhanVien nhanVien) {
        super.changeStateView();
        isPhongBan = false;
        isNhanVien = true;
        this.nhanVien = nhanVien;
    }

    public List<Phong> getMlstPhong() {
        return mlstPhong;
    }

    public void setMlstPhong(List<Phong> mlstPhong) {
        this.mlstPhong = mlstPhong;
    }

    public List<ChuyenNganh> getMlstChuyenNganhs() {
        return mlstChuyenNganhs;
    }

    public void setMlstChuyenNganhs(List<ChuyenNganh> mlstChuyenNganhs) {
        this.mlstChuyenNganhs = mlstChuyenNganhs;
    }

    public Phong getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(Phong phongBan) {
        this.phongBan = phongBan;
    }

    public List<NhanVien> getMlstNhanVien() {
        return mlstNhanVien;
    }

    public void setMlstNhanVien(List<NhanVien> mlstNhanVien) {
        this.mlstNhanVien = mlstNhanVien;
    }

    public Phong getPhongSelected() {
        return phongSelected;
    }

    public void setPhongSelected(Phong phongSelected) {
        this.phongSelected = phongSelected;
    }

    public boolean isIsPhongBan() {
        return isPhongBan;
    }

    public void setIsPhongBan(boolean isPhongBan) {
        this.isPhongBan = isPhongBan;
    }

    public boolean isIsNhanVien() {
        return isNhanVien;
    }

    public void setIsNhanVien(boolean isNhanVien) {
        this.isNhanVien = isNhanVien;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public List<NgayLamViec> getMlstNgayLamViec() {
        return mlstNgayLamViec;
    }

    public void setMlstNgayLamViec(List<NgayLamViec> mlstNgayLamViec) {
        this.mlstNgayLamViec = mlstNgayLamViec;
    }

}
