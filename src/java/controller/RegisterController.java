/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import model.RegisterModel;
import util.DateUtils;
import util.MessagesUtils;
import util.SessionUtils;
import util.StringUtils;

/**
 *
 * @author Nguyễn Tiến Dũng
 */
@ManagedBean
@ViewScoped
public class RegisterController implements Serializable {

    private User user;
    private Date sysdate;
    private String focus;
    private List<User> mlstUser;
    private RegisterModel registerModel;
    private boolean isAdd;
    private boolean isEdit;

    /**
     * Creates a new instance of RegisterController
     */
    public RegisterController() {
        try {
            isAdd = false;
            isEdit = false;
            user = new User();
            sysdate = new Date();
            registerModel = new RegisterModel();
            focus = "name";
            mlstUser = new ArrayList<>();
            mlstUser = registerModel.getListUser();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void handOK() {
        try {
            SessionUtils.getRequest();
            HttpSession session = SessionUtils.getSession();
            String userLogin = (String) session.getAttribute("username");
            user.setChgWho(userLogin);
            user.setChgDate(new Date());
            user.setStatus("1");
            if (isAdd) {
                //thi goi ham them
                registerModel.add(user);
                mlstUser.add(0, user);
                //insert database
                MessagesUtils.info("", "Chúc mừng bạn đăng ký thành công !!!");
            } else if (isEdit) {
                //thi goi ham sua
                registerModel.update(user);
                //insert database
                MessagesUtils.info("", "Chúc mừng bạn sửa thành công !!!");
            }

            handCancel();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }

    }

    public void changeStateAdd() {
        try {
            isAdd = true;
            isEdit = false;
            user = new User();
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void changeStateEdit(User user) {
        try {
            isAdd = false;
            isEdit = true;
            this.user = user;
            this.user.setRePassword(this.user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public void handCancel() {
        try {
            isAdd = false;
            isEdit = false;
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }
    
    public void handDelete(User user) {
        try {
            registerModel.remove(user);
            mlstUser.remove(user);
        } catch (Exception e) {
            e.printStackTrace();
            MessagesUtils.error("", e.toString());
        }
    }

    public boolean valid() throws Exception {
        // valid du lieuj neu dung thi return true sai thi return false
        if (user.getName().length() >= 50) {
            MessagesUtils.error("", "Họ tên phải nhở hơn 50 ký tự");
            return false;
        } else if (user.getUsername().length() >= 15) {
            focus = "username";
            MessagesUtils.error("", "Tên đăng nhập phải lớn hơn 15 ký tự");
            return false;
        } else if (!StringUtils.checkRegex("^[\\w]+$", user.getUsername())) {
            focus = "username";
            MessagesUtils.error("", "Tên đăng nhập không hợp lệ");
            return false;
        } else if (!user.getPassword().equals(user.getRePassword())) {
            MessagesUtils.error("", "Nhập lại mật khẩu không đúng");
            focus = "rePassword";
            return false;
        } else if (!checkEmailOrPhone()) {
            return false;
        } else if (!checkIdNo()) {
            return false;
        }
        return true;
    }

    public boolean checkPassword() {
        if (!StringUtils.checkRegex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", user.getPassword())) {
            MessagesUtils.error("", "Mật khẩu không đúng định dạng");
            return false;
        } else {
            return true;
        }
    }

    public boolean checkEmailOrPhone() {
        if ((user.getEmail() == null && user.getPhoneNumber() == null)
                || (user.getEmail().equals("") && user.getPhoneNumber().equals(""))) {
            MessagesUtils.error("", "Bạn phải nhập email hoặc số điện thoại");
            return false;
        } else {

            if (user.getPhoneNumber() != null && !user.getPhoneNumber().isEmpty()) {
                if (!StringUtils.checkRegex("^[\\d]{10}$", user.getPhoneNumber())) {
                    MessagesUtils.error("", "Số điện thoại không hợp lệ");
                    return false;
                }
            }
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                if (!StringUtils.checkRegex("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", user.getEmail())) {
                    MessagesUtils.error("", "Email không hợp lệ");
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkIdNo() throws Exception {
        if (user.getDocType() != null && !user.getDocType().isEmpty()) {
            if (user.getDocType().equals("CMND")) {
                if (!StringUtils.checkRegex("^[\\d]{9}$", user.getIdNo())) {
                    MessagesUtils.error("", "Số CMND không hợp lệ");
                    return false;
                }
            } else if (user.getDocType().equals("CCCD")) {
                if (!StringUtils.checkRegex("^[\\d]{12}$", user.getIdNo())) {
                    MessagesUtils.error("", "Số CCCD không hợp lệ");
                    return false;
                }
            }
            if (user.getIdIssueDate() == null) {
                MessagesUtils.error("", "Ngày cấp bắt buộc phải nhập");
                return false;
            } else if (user.getIdIssuePlace() == null || user.getIdIssuePlace().isEmpty()) {
                MessagesUtils.error("", "Nơi cấp bắt buộc phải nhập");
                return false;
            } else if (user.getIdIssueDate().after(new Date())) {
                MessagesUtils.error("", "Ngày cấp không được lớn hơn ngày hiện tại");
                return false;
            } else if (user.getBirtDay() == null) {
                MessagesUtils.error("", "Ngày sinh bắt buộc nhậpnhaapj");
                return false;
            } else if (DateUtils.addYear(user.getBirtDay(), 14).after(new Date())) {
                MessagesUtils.error("", "Phải trên 14 tuổi");
                return false;
            } else {
                return true;
            }
        } else {
            if (user.getBirtDay() != null || StringUtils.nvl(user.getIdNo(), "").isEmpty() || user.getIdIssueDate() != null || StringUtils.nvl(user.getIdIssuePlace(), "").isEmpty()) {
                MessagesUtils.error("", "Không được nhập ngày sinh, số giấy tờ, ngày cấp, nơi cấp");
                return false;
            } else {
                return true;
            }
        }
    }

    public void createEmail() {
        String email = user.getUsername() + "@gmail.com";
        user.setEmail(email);
    }

    public void resetForm() {
        user = new User();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getSysdate() {
        return sysdate;
    }

    public void setSysdate(Date sysdate) {
        this.sysdate = sysdate;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public List<User> getMlstUser() {
        return mlstUser;
    }

    public void setMlstUser(List<User> mlstUser) {
        this.mlstUser = mlstUser;
    }

    public boolean isIsAdd() {
        return isAdd;
    }

    public void setIsAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public boolean isIsEdit() {
        return isEdit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

}
