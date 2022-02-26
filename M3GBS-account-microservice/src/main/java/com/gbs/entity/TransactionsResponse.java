package com.gbs.entity;

import java.util.List;

public class TransactionsResponse {
	private List<Transaction> transactions;
	
	public List<Transaction> getTransactions(){
		return transactions;
	}
	
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
}
