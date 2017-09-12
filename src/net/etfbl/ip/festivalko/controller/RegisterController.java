package net.etfbl.ip.festivalko.controller;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;

import net.etfbl.ip.festivalko.dao.UserDAO;
import net.etfbl.ip.festivalko.dao.impl.DAOFactory;
import net.etfbl.ip.festivalko.model.User;
import net.etfbl.ip.festivalko.model.UserType;
import net.etfbl.ip.festivalko.utility.CryptographyUtility;

@ManagedBean(name = "registerController")
@RequestScoped
public class RegisterController implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String lastName;
	private String jmbg;
	private String email;
	private String username;
	private String password;
	private String confirmPassword;
	private Part picture;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getJmbg() {
		return jmbg;
	}
	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public Part getPicture() {
		return picture;
	}
	public void setPicture(Part picture) {
		this.picture = picture;
	}
	
	public void register() {
		String pictureUri = null;
		
		//TODO: Dodati provjeru da li je picture null
		try(InputStream input = picture.getInputStream()){
			pictureUri = CryptographyUtility.hash(username.getBytes()).replaceAll("/", "#");
			String name = picture.getSubmittedFileName();
			String[] splitted = name.split("\\.");
			
			//Adding extension
			pictureUri += "." + splitted[splitted.length - 1];
			
			//TODO: Promjeniti da cita putanju iz configa
			Files.copy(input, new File("D:\\eclipse-workspace"+File.separator+"ServerFS"+File.separator+"userPictures"+File.separator+pictureUri).toPath());
		} catch (Exception e) {
		}
		User u = new User();
		u.setFirstName(firstName);
		u.setLastName(lastName);
		u.setJmbg(jmbg);
		u.setEmail(email);
		u.setType(UserType.REGISTERED);
		u.setUsername(username);
		u.setPassword(password);
		u.setPictureUri(pictureUri);
		UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
		userDAO.save(u);
	}
	
	//TODO: Add validators
}
