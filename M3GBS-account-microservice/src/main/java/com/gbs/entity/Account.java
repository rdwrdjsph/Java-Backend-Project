package com.gbs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ACCOUNTS")
public class Account {
	@Id
	@Column(name="ACCOUNT_NUMBER")
	private String accountNumber;
	@Column(name="USER_NAME")
	private String username;
	@Column(name="ACCOUNT_BALANCE")
	private float accountBalance;
	
	
	public Account() {
		
	}

	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public float getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	

}
