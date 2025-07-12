package com.java.crv.BibleReaderCommentary.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name= "`USER`")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) /* DB sama brine o generiranju ID-eva (AUTO) property */
	private Long id;
	@Column(length = 100)
	private String password;	
	private String username;
	private String email;
	private UserRoles role;
	
	@OneToMany(mappedBy = "user")
	@JsonProperty("comments")
	@JsonBackReference
	private List<Commentary> comments;
	
	
	
	public UserRoles getRole() {
		return role;
	}
	public void setRole(UserRoles role) {
		this.role = role;
	}
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
