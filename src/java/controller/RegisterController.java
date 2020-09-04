/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entity.User;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import util.DateUtils;
import util.MessagesUtils;
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

    /**
     * Creates a new instance of RegisterController
     */
    public RegisterController() {
        user = new User();
        sysdate = new Date();
        focus = "name";
    }

    public void handOK() {
        try {
            if (valid()) {
                MessagesUtils.info("", "Chúc mừng bạn đăng ký thành công !!!");
            }
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
        } 
        else if (!checkPassword()) {
            focus = "password";
            MessagesUtils.error("", "Mật khẩu không đúng định dạng");
            return false;
        } 
        else if (!user.getPassword().equals(user.getRePassword())) {
            MessagesUtils.error("", "Nhập lại mật khẩu không đúng");
            focus = "rePassword";
            return false;
        } else if (!checkEmailOrPhone()) {
            return false;
        } else if (user.getDocType() != null && user.getBirtDay() != null && user.getBirtDay().after(new Date())) {
            MessagesUtils.error("", "Ngày sinh lớn hơn ngày hiện tại");
            return false;
        } else if(!checkIdNo()){
            return false;
        }else {
            MessagesUtils.info("", "Đăng ký thành công");
        }
        return false;
    }
    
    public boolean checkPassword(){
        if (!StringUtils.checkRegex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", user.getPassword())) {
            MessagesUtils.error("", "Mật khẩu không đúng định dạng");
            return false;
        }else{
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
    
    public void createEmail(){
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
    
    

}
