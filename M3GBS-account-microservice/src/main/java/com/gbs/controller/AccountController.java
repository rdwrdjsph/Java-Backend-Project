package com.gbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gbs.entity.Account;
import com.gbs.entity.AccountsResponse;
import com.gbs.repository.AccountRepository;

@RestController
@RequestMapping("/api")
public class AccountController {
	@Autowired
	AccountRepository accountRepository;
	
	@GetMapping("/accountsResponse")
	public AccountsResponse getAllAccount(){
		List<Account> accountList = accountRepository.findAll();
		AccountsResponse accountsResponse = new AccountsResponse();
		accountsResponse.setAccounts(accountList);
		return accountsResponse;
	}
	
	@PutMapping("/accountSource/{accountNumber}")
	public Account updateSourceAccount(@PathVariable(value="accountNumber") String accountNumber, @RequestBody Account accountFromClient) {
		Account existingAccount = accountRepository.findById(accountNumber).get();
		float availableBalance = existingAccount.getAccountBalance();
		float amount = accountFromClient.getAccountBalance();
		float newBalance = availableBalance - amount;
		
		existingAccount.setAccountBalance(newBalance);
		
		Account updatedAccount = accountRepository.save(existingAccount);
		return updatedAccount;
	}
	
	@PutMapping("/accountDestination/{accountNumber}")
	public Account updateDestinationAccount(@PathVariable(value="accountNumber") String accountNumber, @RequestBody Account accountFromClient) {
		Account existingAccount = accountRepository.findById(accountNumber).get();
		float availableBalance = existingAccount.getAccountBalance();
		float amount = accountFromClient.getAccountBalance();
		float newBalance = availableBalance + amount;
		
		existingAccount.setAccountBalance(newBalance);
		
		Account updatedAccount = accountRepository.save(existingAccount);
		return updatedAccount;
	}
}
