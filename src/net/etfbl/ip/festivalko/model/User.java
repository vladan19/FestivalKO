package net.etfbl.ip.festivalko.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.etfbl.ip.festivalko.dao.UserDAO;
import net.etfbl.ip.festivalko.dao.impl.DAOFactory;

@ManagedBean(name="loggedUser", eager=true)
@SessionScoped
public class User implements Model<Integer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String firstName = "Vladan";
	private String lastName;
	private String jmbg;
	private String email;
	private UserType type = UserType.GUEST;
	private String pictureUri;
	private String username;
	private String password;
	private LocalDateTime registationDate;
	private boolean activated;
	private LocalDateTime activationDate;

	public User(int id, String firstName, String lastName, String jmbg, String email, UserType type, String pictureUri,
			String username, String password, LocalDateTime registationDate, boolean activated,
			LocalDateTime activationDate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.jmbg = jmbg;
		this.email = email;
		this.type = type;
		this.pictureUri = pictureUri;
		this.username = username;
		this.password = password;
		this.registationDate = registationDate;
		this.activated = activated;
		this.activationDate = activationDate;
	}

	public User() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public String getPictureUri() {
		return pictureUri;
	}

	public void setPictureUri(String pictureUri) {
		this.pictureUri = pictureUri;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDateTime getRegistationDate() {
		return registationDate;
	}

	public void setRegistationDate(LocalDateTime registationDate) {
		this.registationDate = registationDate;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public LocalDateTime getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(LocalDateTime activationDate) {
		this.activationDate = activationDate;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Integer getPrimaryKey() {
		return id;
	}
}
