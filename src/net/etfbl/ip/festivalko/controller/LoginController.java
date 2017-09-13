package net.etfbl.ip.festivalko.controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import net.etfbl.ip.festivalko.dao.UserDAO;
import net.etfbl.ip.festivalko.dao.impl.DAOFactory;
import net.etfbl.ip.festivalko.model.User;
import net.etfbl.ip.festivalko.utility.PasswordUtility;

@ManagedBean(name = "loginController")
@ViewScoped
public class LoginController implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	
	@ManagedProperty(value="#{loggedUser}")
	private User user;
	
	public void setUser(User user) {
		this.user = user;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String login() {
		UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
		User u = userDAO.findByUsername(username);
		if(u!=null && PasswordUtility.check(password, u.getPassword())) {
			if(u.isActivated()) {
				System.out.println("ok");
				//user.setFirstName(u.getFirstName());
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedUser", u);
				return "WEB-INF/test.xhtml";
			}
			else {
				System.out.println("Wait for activation!");
			}
		}
		else {
			System.out.println("Wrong username or password!");
		}
		return null;
	}
}
