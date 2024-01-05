package es.unex.pi.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	private long id;
	private String name;
	private String surname;
	private String image;
	private String email;
	private String password;
	
	public User() {
		super();
		this.image="";
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public boolean validatePassword() {
		String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
		
		Pattern pattern = Pattern.compile(regex);
		
		Matcher matcher = pattern.matcher(getPassword());
		
		return matcher.matches();
	}
}
