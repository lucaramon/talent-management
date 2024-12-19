package com.example.talent_api.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="manager")
public class Manager {
    
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

	private String fullName;
	private String email;
	private String department;
    private String phone;

    public Manager() {
	}

	public Manager(User user, String fullName, String email, String department, String phone) {
        this.user = user;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
        this.phone = phone;        
	}


	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

		
}
