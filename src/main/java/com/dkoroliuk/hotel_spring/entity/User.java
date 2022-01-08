package com.dkoroliuk.hotel_spring.entity;


import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;

  @Column(name = "name", nullable = false, length = 20)
  private String name;
  @Column(name = "surname", nullable = false, length = 20)
  private String surname;
  @Column(name = "login", nullable = false, unique = true, length = 20)
  private String login;
  @Column(name = "password", nullable = false, length = 20)
  private String password;
  @Column(name = "email", nullable = false, unique = true, length = 40)
  private String email;
  @ManyToOne
  @JoinColumn(name = "user_role_id", referencedColumnName = "id", nullable = false)
  private UserRole userRole;
  @Column(name = "is_enable", nullable = false)
  private boolean isEnable;
  
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
public String getLogin() {
	return login;
}
public void setLogin(String login) {
	this.login = login;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public UserRole getUserRole() {
	return userRole;
}
public void setUserRole(UserRole userRole) {
	this.userRole = userRole;
}
public boolean isEnable() {
	return isEnable;
}
public void setEnable(boolean isEnable) {
	this.isEnable = isEnable;
}
  
}
