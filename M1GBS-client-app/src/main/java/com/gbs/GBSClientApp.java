package com.gbs;

import java.util.Scanner;

public class GBSClientApp {

	public static void main(String[] args) {
		int option, option2;

		Scanner input = new Scanner(System.in);

		try {
			do {
				System.out.println("==================================");
				System.out.println("-----GLOBAL BANKING SOLUTIONS-----");
				System.out.println("==================================");
				System.out.println("[1] LOG IN ");
				System.out.println("[2] EXIT");

				System.out.println("PLEASE CHOOSE AN OPTION: ");

				String tempOption = input.nextLine();
				option = Integer.parseInt(tempOption);

				if (option == 1) {
					loginUser();
				} else if (option == 2) {
					System.out.println("EXITING...");
					System.exit(0);
				} else {
					System.out.println("OPTION INVALID! PLEASE TRY AGAIN.");
				}

				System.out.println("DO YOU WANT TO LOG IN? \n[1] YES \n[2] NO");
				String tempOption2 = input.nextLine();
				option2 = Integer.parseInt(tempOption2);

			} while (option2 == 1);
		} catch (NumberFormatException numberFormatException) {
			System.out.println("OPTION INVALID!");
		}
		System.out.println("EXITING...");
		input.close();
		System.exit(0);
	}
	
	public static void loginUser() {		
		boolean loginSuccess = false;
		int accOption = 0;
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		
		System.out.println("==================================");
		System.out.println("------------GBS LOG IN------------");
		System.out.println("==================================");
		System.out.println("USERNAME: ");
		String usernameInput = in.nextLine();
		System.out.println("PASSWORD: ");
		String passwordInput = in.nextLine();

		loginSuccess = GBSUserServiceApp.validateUser(usernameInput, passwordInput);

		if (loginSuccess) {
			GBSAccountServiceApp.displayAccounts(usernameInput);
			do {
				System.out.println("[1] FUND TRANSFER");
				System.out.println("[2] TRANSACTION HISTORY");
				System.out.println("[3] LOG OUT");
				System.out.println("PLEASE CHOOSE AN OPTION: ");

				String tempOption = in.nextLine();
				accOption = Integer.parseInt(tempOption);

				if (accOption == 1) {
					GBSAccountServiceApp.fundTransfer(usernameInput, passwordInput);
				} else if (accOption == 2) {
					GBSAccountServiceApp.displayTransactions();
				} else if (accOption == 3) {
					System.out.println("LOGGING OUT...");
				} else {
					System.out.println("OPTION INVALID!");
				}
			} while (accOption != 3);
		}
	}
}
