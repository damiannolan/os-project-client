# Operating Systems Project

## Usage

	1. Run the server in your terminal using:
		
		java -cp bankserver.jar server.ServerRunner
		
	2. Run multiple instances of the client in your terminal using:
		
		java -cp bankclient.jar client.ClientRunner
		

## Server

- The server operates on the port number 8080 which is currently hard coded as a constant in ServerRunner.java
- The port is used as a parameter in the constructor for instantiating a new BankServer object
- BankServer spawns a new Thread with a Listener runnable (see inner class - Listener)
- Listener is used to intercept new client connections and spawn a new RequestHandler thread to deal with client interactions
- The socket remains open for the duration that the client is running
- Many client instances can be started using different terminal windows to demonstrate the multithreading capabilities

- The RequestHandler employees an AccountsManager object to handle operations dealing with Accounts
- Every Account is composed with a LinkedList data structure containing Transaction objects (Bug - Transaction will still be recorded if the transactions doesn't go through. i.e. If withdrawal is more than the CREDITLIMIT - €1000)

## Client

- The client application is based on a basic while loop and switch statement
- It employees the use of a BankServerService (BankServerService.java) object to handle requests to the server
- BankServerService implements all of the methods found in the interface - IBankServer.java
- These methods define the various functionality listed in the project brief as well as one or two more
	- Register
	- Login
	- Logout
	- Edit Account
	- Lodgement
	- Withdrawal
	- List of last 10 transactions
	- Quit