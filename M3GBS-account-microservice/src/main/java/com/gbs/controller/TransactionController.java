package com.gbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gbs.entity.Transaction;
import com.gbs.entity.TransactionsResponse;
import com.gbs.repository.TransactionRepository;

@RestController
@RequestMapping("/api")
public class TransactionController {
	@Autowired
	TransactionRepository transactionRepository;
	
	@GetMapping("/transactionResponse")
	public TransactionsResponse getAllTransaction(){
		List<Transaction> transactionList = transactionRepository.findAll();
		TransactionsResponse transactionsResponse = new TransactionsResponse();
		transactionsResponse.setTransactions(transactionList);
		return transactionsResponse;
	}
	
	@PostMapping("/transactionSave")
	public Transaction savedTransaction(@RequestBody Transaction transactionFromClient) {
		Transaction savedTransaction = transactionRepository.save(transactionFromClient);
		return savedTransaction;
	}
}

