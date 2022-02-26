package com.gbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.client.RestTemplate;

import com.gbs.entity.User;
import com.gbs.entity.UsersResponse;

@EnableEurekaClient
@SpringBootApplication
public class GBSUserServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(GBSUserServiceApp.class, args);
	}
	
	//METHOD TO VALIDATE THE USERNAME AND PASSWORD
	public static boolean validateUser(String usernameInput, String passwordInput) {
		RestTemplate loginRestTemplate = new RestTemplate();
		final String restApiURLString = "http://localhost:8081/gbs/api/validate";
		UsersResponse usersResponse = loginRestTemplate.getForObject(restApiURLString, UsersResponse.class);
		boolean userFound = false;
		boolean loginSuccess = false;

		for (User user : usersResponse.getUsers()) {
			String validateUsername = user.getUsername().toString();
			String validateUserPassword = user.getUserPassword().toString();
			
			if (validateUsername.equals(usernameInput)) {
				userFound = true;
				
				if (validateUserPassword.equals(passwordInput)) {
					System.out.println("\nLOGIN SUCCESS.\n");
					loginSuccess = true;
					System.out.println("==================================");
					System.out.println("-------------USER INFO------------");
					System.out.println("==================================");
					for (User usersList : usersResponse.getUsers()) {
						if (usersList.getUsername().equals(usernameInput)) {
							System.out.println("USERNAME: " +usersList.getUsername());
							System.out.println("CREATION DATE: " + usersList.getCreationDate());
							System.out.println("NUMBER OF ACCOUNTS: " + usersList.getNumberOfAccounts());
							System.out.println("TOTAL BALANCE OF ACCOUNTS: " + usersList.getTotalBalance());
							System.out.println("CONTACT NUMBER: " + usersList.getContactNumber());
							System.out.println("==================================\n");
						}
					}
				} else {
					System.out.println("WRONG PASSWORD");
				}
			}
		}
		if(!userFound) {
			System.out.println("NO RECORDS FOUND.");
		}
		return loginSuccess;
	}
}
