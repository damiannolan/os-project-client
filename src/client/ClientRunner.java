package client;

import java.util.*;
import java.io.IOException;
import java.net.*;

public class ClientRunner {

	public static void main(String[] args) throws UnknownHostException, IOException {
		final int port = 8080;
		final String host = "localhost";
		
		Scanner console = new Scanner(System.in);
		boolean running = true;
		boolean loggedIn = false;
		int option;		
		String username, password;
		
		Socket clientSocket = new Socket(host, port);
		BankServerService bankServerService = new BankServerService(clientSocket);
			
		while(running) {
			System.out.println("\n1. Register");
			System.out.println("2. Log in");
			System.out.println("3. Quit");
			System.out.print("\nPlease choose an option: ");
			option = console.nextInt();
			
			switch(option) {
				case 1: //Register
					bankServerService.register();
					break;
				case 2: //Login
					System.out.print("Enter Username: ");
					username = console.next();
					System.out.print("Enter Password: ");
					password = console.next();
					
					//Attempt to login using username and password
					loggedIn = bankServerService.login(username, password);
					
					if(loggedIn == false) {
						System.out.println("\nUsername and/or Password is incorrect!");
					} else {
						System.out.println("\nLogin Successful\n");
						bankServerService.getBalance();
					}
					
					while(loggedIn) {	
						System.out.println("\n1. Edit Details");
						System.out.println("2. Lodgement");
						System.out.println("3. Withdrawal");
						System.out.println("4. View Transactions");
						System.out.println("5. Log out");
						System.out.print("\nPlease choose an option: ");
						option = console.nextInt();
						
						switch(option) {
							case 1: //Edit Account
								bankServerService.editAccount();
								break;
							case 2: //Lodge
								System.out.print("\nPlease enter amount to lodge: ");
								bankServerService.lodge(console.nextFloat());
								break;
							case 3: //Withdraw
								System.out.println("\nYour account has a maximum credit limit of €1000");
								System.out.print("\nPlease enter amount to withdraw: ");
								bankServerService.withdraw(console.nextFloat());
								break;
							case 4: //List Transactions
								bankServerService.listTransactions();
								break;
							case 5: //Logout
								loggedIn = bankServerService.logout();
								break;
						} //end switch
					} //end while(loggedIn)
					break;
				case 3: //Quit
					System.out.println("Exiting...");
					bankServerService.quit();
					running = false;
					break;
				default:
			}
		} //end while
		
		console.close();
	}
}
