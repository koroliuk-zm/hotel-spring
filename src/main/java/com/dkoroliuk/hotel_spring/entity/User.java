package com.dkoroliuk.hotel_spring.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
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
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = false, length = 60)
  private String name;
  @Column(name = "surname", nullable = false, length = 60)
  private String surname;
  @Column(name = "login", nullable = false, unique = true, length = 30)
  private String login;
  @Column(name = "password", nullable = false, length = 200)
  private String password;
  @Column(name = "email", nullable = false, unique = true, length = 60)
  private String email;
  @ManyToOne
  @JoinColumn(name = "user_role_id", referencedColumnName = "id", nullable = false)
  private UserRole userRole;
  @Column(name = "is_enable", nullable = false)
  private boolean isEnable;
}
