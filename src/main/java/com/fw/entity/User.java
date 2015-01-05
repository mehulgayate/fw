package com.fw.entity;

import java.util.Date;

import javax.persistence.Entity;

import com.evalua.entity.support.EntityBase;

@Entity
public class User extends EntityBase{

	public enum Status {
		ACTIVE, BLACK_LISTED,DELETED;
	}
	
	private String name;
	private String email;
	private String password;
	private String address;
	private String phone;
	private Integer bannedWordsCount=0;
	private Status status=Status.ACTIVE;
	private Date date=new Date();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getBannedWordsCount() {
		return bannedWordsCount;
	}
	public void setBannedWordsCount(Integer bannedWordsCount) {
		this.bannedWordsCount = bannedWordsCount;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
