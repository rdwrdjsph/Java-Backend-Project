package com.gbs.entity;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TRANSACTIONS")
public class Transaction {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(name="TRANSACTION_ID")
	private Long transactionId;
	@Column(name="FROM_ACCOUNT")
	private String fromAccount;
	@Column(name="TO_ACCOUNT")
	private String toAccount;
	@Column(name="AMOUNT")
	private float amount;
	
	
	public Transaction() {
		
	}


	public Long getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}


	public String getFromAccount() {
		return fromAccount;
	}


	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}


	public String getToAccount() {
		return toAccount;
	}


	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}


	public float getAmount() {
		return amount;
	}


	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	
	
}
