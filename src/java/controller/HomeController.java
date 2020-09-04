/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author Nguyễn Tiến Dũng
 */
@ManagedBean
@ViewScoped
public class HomeController implements Serializable{
    private String name = "";

    /**
     * Creates a new instance of HomeController
     */
    public HomeController() {
    }

    public String viewName(){
        return "abc";
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
