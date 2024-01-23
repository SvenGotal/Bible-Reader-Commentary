package com.java.crv.BibleReaderCommentary.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name= "`user`")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) /* DB sama brine o generiranju ID-eva (AUTO) property */
	private Long id;
	private String password;	
	private String username;
	private String email;
	
	@OneToMany(mappedBy = "user")
	private List<Commentary> comments;
	
	public List<Commentary> getComments() {
		return comments;
	}
	public void setComments(List<Commentary> comments) {
		this.comments = comments;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + "]";
	}
	
	
	
}
