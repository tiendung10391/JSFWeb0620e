/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import entity.User;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import util.MessagesUtils;
import util.SessionUtils;

/**
 *
 * @author Nguyễn Tiến Dũng
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController {
    private User user;
    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
        user = new User();
    }
    
    public String handLogin(){
        String usernameDefault = "DungNT";
        String passwordDefault = "123456";
        if(user.getUsername().equals(usernameDefault) && user.getPassword().equals(passwordDefault)){
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", user.getUsername());
            return "/admin/register?faces-redirect=true";
        }else{
            MessagesUtils.error("Lỗi", "Sai tên đăng nhập hoặc mật khẩu");
            return "";
        }
    }

    public String handLogout(){
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "/login?faces-redirect=true";
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
