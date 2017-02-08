package client;

import java.io.*;
import java.net.*;
import java.util.*;

public class BankServerService implements IBankServer {
	
	private Scanner console;
	
	private Socket clientSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public BankServerService(Socket clientSocket) throws IOException {
		console = new Scanner(System.in);	
		this.clientSocket = clientSocket;
		this.out = new ObjectOutputStream(this.clientSocket.getOutputStream());
		this.in = new ObjectInputStream(this.clientSocket.getInputStream());	
	}

	@Override
	public void register() {

		String command = "REGISTER";
		
		try {
			out.writeObject(command);
			out.flush();
			
			System.out.print("Enter Name: ");
			String name = console.nextLine();
			out.writeObject(name);
			
			System.out.print("Enter Address: ");
			String address = console.nextLine();
			out.writeObject(address);
			
			System.out.print("Enter Username: ");
			String username = console.next();
			out.writeObject(username);
			
			System.out.print("Enter Password: ");
			String password = console.next();
			out.writeObject(password);
			
			out.flush();
			//flush buffer
			console.nextLine();
			
			//Response
			String response = (String) in.readObject();
			System.out.println(response);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean login(String username, String password) {
		String command = "LOGIN";
		boolean loggedIn = false;
		
		try {
			out.writeObject(command);
			out.flush();
			
			out.writeObject(username);
			out.writeObject(password);
			
			loggedIn = in.readBoolean();
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return loggedIn;
	}
	
	@Override
	public boolean logout() {
		return false;
	}
	
	public void getBalance() {
		String command = "BALANCE";
		
		try {
			out.writeObject(command);
			out.flush();
			
			float balance = in.readFloat();
			System.out.printf("\nYour balance is: %.2f\n", balance);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editAccount() {
		String command = "EDIT";
		
		try {
			//Request command
			out.writeObject(command);
			out.flush();
			
			//Enter the user details to change
			System.out.print("Enter Name: ");
			String name = console.nextLine();
			out.writeObject(name);
			
			System.out.print("Enter Address: ");
			String address = console.nextLine();
			out.writeObject(address);
			
			System.out.print("Enter Username: ");
			String username = console.next();
			out.writeObject(username);
			
			System.out.print("Enter Password: ");
			String password = console.next();
			out.writeObject(password);
			
			out.flush();
			//flush buffer
			console.nextLine();
			
			String response = (String) in.readObject();
			System.out.println(response);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void lodge(float amount) {
		String command = "LODGE";
		
		try {
			//Send request to LODGE
			out.writeObject(command);
			
			//Send the amount to lodge
			out.writeFloat(amount);
			out.flush();

			//Receive response of new balance
			float balance = in.readFloat();
			
			System.out.printf("\nBalance = %.2f\n", balance);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void withdraw(float amount) {
		String command = "WITHDRAW";
		
		try {
			//Send request to WITHDRAW
			out.writeObject(command);
			
			out.writeFloat(amount);
			out.flush();
			
			//Receive response of new balance
			float balance = in.readFloat();
			
			System.out.printf("\nBalance = %.2f\n", balance);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void listTransactions() {
		String command = "TRANSACTIONS";
		
		try {
			//Send request for TRANSACTIONS
			out.writeObject(command);
			out.flush();
			
			//Receive response
			//LinkedList<Transaction> transactions = (LinkedList) in.readObject();
			//Read the size of the list
			int size = in.readInt();
			String transaction;
			
			if(size == 0) {
				System.out.println("\nThere are no transactions on record");
			} else {
				//Print the transactions
				for(int i = 0; i < size; i++) {
					//For every transaction in the list, read its toString() method and print
					transaction = (String) in.readObject();
					System.out.println(transaction);
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	@Override
	public void quit() {
		String command = "QUIT";
		
		try {
			out.writeObject(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
