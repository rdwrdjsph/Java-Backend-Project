package com.gbs;

import java.util.Scanner;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.client.RestTemplate;

import com.gbs.entity.Account;
import com.gbs.entity.AccountsResponse;
import com.gbs.entity.Transaction;
import com.gbs.entity.TransactionsResponse;
import com.gbs.entity.User;


@EnableEurekaClient
@SpringBootApplication
public class GBSAccountServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(GBSAccountServiceApp.class, args);
	}
	
	//METHOD TO DISPLAY THE ACCOUNTS OF USER
	public static void displayAccounts(String usernameInput) {
		RestTemplate accountsRestTemplate = new RestTemplate();
		final String restApiURLStringAccount = "http://localhost:9091/gbs/api/accountsResponse";
		AccountsResponse accountsResponse = accountsRestTemplate.getForObject(restApiURLStringAccount, AccountsResponse.class);
		System.out.println("==================================");
		System.out.println("-----------ACCOUNT LIST-----------");
		System.out.println("==================================");
		for (Account accountsList : accountsResponse.getAccounts()) {
			if (accountsList.getUsername().equals(usernameInput)) {
				System.out.println("ACCOUNT NUMBER: " + accountsList.getAccountNumber());
				System.out.println("AVAILABLE BALANCE: " + accountsList.getAccountBalance());
				System.out.println("==================================");
			}
		}
	}
	
	//METHOD FOR FUND TRANSFER
	public static void fundTransfer(String usernameInput, String passwordInput) {
		boolean found = false;
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		RestTemplate accountsRestTemplate = new RestTemplate();
		final String restApiURLStringAccount = "http://localhost:9091/gbs/api/accountsResponse";
		AccountsResponse accountsResponse = accountsRestTemplate.getForObject(restApiURLStringAccount, AccountsResponse.class);

		System.out.println("\n==================================");
		System.out.println("-----------FUND TRANSFER----------");
		System.out.println("==================================");
		System.out.println("SOURCE ACCOUNT: ");
		String sourceAccount = in.nextLine();

		for (Account accounts : accountsResponse.getAccounts()) {
			if (usernameInput.equals(accounts.getUsername())) {
				if (sourceAccount.equals(accounts.getAccountNumber())) {
					found = true;
				}
			}
		}

		if (!found) {
			System.out.println("\nINVALID ACCOUNT NUMBER. TRY AGAIN.");
		} else {
			boolean toFound = false;

			System.out.println("\nDESTINATION ACCOUNT: ");
			String destinationAccount = in.nextLine();

			for (Account accounts : accountsResponse.getAccounts()) {
				if (accounts.getAccountNumber().equals(destinationAccount)) {
					toFound = true;
				}
			}
			if (!toFound) {
				System.out.println("\nACCOUNT NUMBER NOT EXIST.");
			} else {
				boolean valid = false;

				System.out.println("\nAMOUNT: ");
				String tempAmount = in.nextLine();
				float amount = Float.parseFloat(tempAmount);

				for (Account accounts : accountsResponse.getAccounts()) {
					if (accounts.getUsername().equals(usernameInput)) {
						if (accounts.getAccountNumber().equals(sourceAccount)) {
							float amountBalance = accounts.getAccountBalance();
							if (amountBalance >= amount) {
								valid = true;
							}
						}
					}
				}
				if (!valid) {
					System.out.println("\nNOT ENOUGH BALANCE.");
				} else {
					System.out.println("\nENTER PASSWORD: ");
					String retypePassword = in.nextLine();

					if (passwordInput.equals(retypePassword)) {
						updateSourceAccount(sourceAccount, amount);
						updateDestinationAccount(destinationAccount, amount);
						updateTotalBalanceSourceAccount(usernameInput, sourceAccount,destinationAccount,amount);
						updateTotalBalanceDestinationAccount(usernameInput,destinationAccount,amount);
						saveTransaction(sourceAccount, destinationAccount, amount);													
					} else {
						System.out.println("\nWRONG PASSWORD.");

					}
				}
			}
		}
	}
	
	//METHOD TO DISPLAY THE TRANSACTION HISTORY OF ACCOUNT NUMBER
	public static void displayTransactions() {	
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		RestTemplate transactionsRestTemplate = new RestTemplate();
		final String restApiURLStringTransaction = "http://localhost:9091/gbs/api/transactionResponse";
		TransactionsResponse transactionsResponse = transactionsRestTemplate.getForObject(restApiURLStringTransaction, TransactionsResponse.class);
		System.out.println("==================================");
		System.out.println("--------TRANSACTION HISTORY-------");
		System.out.println("==================================");
		System.out.println("Enter Account Number: ");
		String accNum = in.nextLine();
		
		for (Transaction transactions : transactionsResponse.getTransactions()) {	
			if (transactions.getFromAccount().equals(accNum) || transactions.getToAccount().equals(accNum)) {
				System.out.println("TRANSACTION ID: " + transactions.getTransactionId());
				System.out.println("SOURCE ACCOUNT: " + transactions.getFromAccount());
				System.out.println("DESTINATION ACCOUNT: " + transactions.getToAccount());
				System.out.println("AMOUNT: " + transactions.getAmount());
				System.out.println("==================================");
			}					
		}
	}	
	
	//METHOD TO UPDATE THE ACCOUNT BALANCE OF SENDER
	public static  void updateSourceAccount(String sourceAccount, float amount) {
		RestTemplate accountsRestTemplate = new RestTemplate();
		Account accountUpdate = new Account();
		accountUpdate.setAccountBalance(amount);

		final String urlRESTAPIUpdate = "http://localhost:9091/gbs/api/accountSource/" + sourceAccount;
		accountsRestTemplate.put(urlRESTAPIUpdate, accountUpdate);
	}
	
	//METHOD TO UPDATE THE ACCOUNT BALANCE OF RECEIVER
	public static void updateDestinationAccount(String destinationAccount, float amount) {
		RestTemplate accountsRestTemplate = new RestTemplate();
		Account accountUpdate = new Account();
		accountUpdate.setAccountBalance(amount);

		final String urlRESTAPIUpdate = "http://localhost:9091/gbs/api/accountDestination/" + destinationAccount;
		accountsRestTemplate.put(urlRESTAPIUpdate, accountUpdate);		
	}
	
	//METHOD TO SAVE TRANSACTION
	public static void saveTransaction(String sourceAccount, String destinationAccount, float amount) {
		RestTemplate transactionsRestTemplate = new RestTemplate();
		final String restApiURLString = "http://localhost:9091/gbs/api/transactionSave";
			
		Transaction transaction = new Transaction();
		transaction.setFromAccount(sourceAccount);
		transaction.setToAccount(destinationAccount);
		transaction.setAmount(amount);
			
		transactionsRestTemplate.postForObject(restApiURLString, transaction, Transaction.class);
		
		System.out.println("\nSUCCESSFULLY SENT "+amount+" FROM ACCOUNT NUMBER: "+sourceAccount+" TO ACCOUNT NUMBER: "+destinationAccount);
	}
	
	//METHOD TO UPDATE THE TOTAL BALANCE OF SENDER ACCOUNT'S USER
	public static void updateTotalBalanceSourceAccount(String userName, String sourceAccount, String destinationAccount, float amount) {	
		RestTemplate totalBalanceRestTemplate = new RestTemplate();
		final String restApiURLString = "http://localhost:9091/gbs/api/accountsResponse";
		AccountsResponse accountsResponse = totalBalanceRestTemplate.getForObject(restApiURLString,AccountsResponse.class);

		for (Account account : accountsResponse.getAccounts()) {
			if (account.getUsername().equals(userName)) {
				if (account.getAccountNumber().equals(sourceAccount)) {
					RestTemplate userFromRestTemplate = new RestTemplate();
					User userUpdateFrom = new User();
					userUpdateFrom.setTotalBalance(0);
					final String urlRESTAPIUpdateUserFrom = "http://localhost:8081/gbs/api/updateFromAccountBalance/"+ userName;
					userFromRestTemplate.put(urlRESTAPIUpdateUserFrom, userUpdateFrom);
				}
			} 
			if (!account.getUsername().equals(userName)) {
				if(account.getAccountNumber().equals(destinationAccount)) {
					RestTemplate userFromRestTemplate = new RestTemplate();
					User userUpdateFrom = new User();
					userUpdateFrom.setTotalBalance(amount);
					final String urlRESTAPIUpdateUserFrom = "http://localhost:8081/gbs/api/updateFromAccountBalance/"+ userName;
					userFromRestTemplate.put(urlRESTAPIUpdateUserFrom, userUpdateFrom);
				}
			}
		}
	}
	
	//METHOD TO UPDATE THE TOTAL BALANCE OF RECEIVER ACCOUNT'S USER
	public static void updateTotalBalanceDestinationAccount(String userName, String destinationAccount, float amount) {		
		RestTemplate totalBalanceRestTemplate = new RestTemplate();
		final String restApiURLString = "http://localhost:9091/gbs/api/accountsResponse";
		AccountsResponse accountsResponse = totalBalanceRestTemplate.getForObject(restApiURLString,AccountsResponse.class);
		for (Account accounts : accountsResponse.getAccounts()) {
			if (accounts.getUsername().equals(userName)) {
				if (accounts.getAccountNumber().equals(destinationAccount)) {
					RestTemplate userFromRestTemplate = new RestTemplate();
					User userUpdateFrom = new User();
					userUpdateFrom.setTotalBalance(0);
					final String urlRESTAPIUpdateUserFrom = "http://localhost:8081/gbs/api/updateToAccountBalance/"+ userName;
					userFromRestTemplate.put(urlRESTAPIUpdateUserFrom, userUpdateFrom);
				}
			} else { 
				if(accounts.getAccountNumber().equals(destinationAccount)) {
					RestTemplate userFromRestTemplate = new RestTemplate();
					String userNameFrom = accounts.getUsername();
					User userUpdateFrom = new User();
					userUpdateFrom.setTotalBalance(amount);
					String urlRESTAPIUpdateUserFrom = "http://localhost:8081/gbs/api/updateToAccountBalance/"+ userNameFrom;
					userFromRestTemplate.put(urlRESTAPIUpdateUserFrom, userUpdateFrom);
				}
			}
		}				
	}
}
