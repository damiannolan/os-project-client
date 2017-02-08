package client;

public interface IBankServer {
	
	public void register();
	
	public boolean login(String username, String password);
	
	public boolean logout();
	
	public void editAccount();
	
	public void lodge(float amount);
	
	public void withdraw(float amount);
	
	public void listTransactions();
	
	public void quit();
}
