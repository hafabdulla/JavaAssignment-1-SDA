import java.util.Scanner;
public class Bank {
	private static final int MaxCustomers = 100;
	private final Customer[]   customers = new Customer[MaxCustomers];
	private final String[] accounts = new String[500]; //to track all account numbers
	private int customerCount = 0;
	private static int accountNumberCounter = 10001;
	private static final String ACCOUNT_PREFIX = "ACC";
	
	
	public Customer createCustomer(String name, String address, String phone) {
		if(customerCount >= MaxCustomers) {
			System.out.println("Bank has reached maximum customer limit");
			return null;
		}
		Customer newCustomer = new Customer(name, address, phone);
		customers[customerCount++] = newCustomer;
		return newCustomer;
	}

	public Customer findCustomerByAccNumber(String accNumber) {
		if (accNumber == null || accNumber.isEmpty()) {
			System.out.println("Account number cannot be null or empty");
			return null;
		}
        for (int i = 0; i < customerCount; i++) {
            Customer c = customers[i];
            if (c.getSavingsAccount() != null && c.getSavingsAccount().getAccountNumber().equals(accNumber)) return c;
            if (c.getCheckingAccount() != null && c.getCheckingAccount().getAccountNumber().equals(accNumber)) return c;
        }
        return null;
	}
	
	public AbstractAccount findAccountByAccNumber(String accNumber) {
		if(accNumber == null || accNumber.isEmpty()) {
			System.out.println("Account number cannot be null or empty");
			return null;
		}
		for(int i = 0;  i < customerCount; i++) {
			Customer c = customers[i];
			if(c.getSavingsAccount() != null && c.getSavingsAccount().getAccountNumber().equals(accNumber)) {
				return c.getSavingsAccount();
			}
			if(c.getCheckingAccount() != null && c.getCheckingAccount().getAccountNumber().equals(accNumber)) {
				return c.getCheckingAccount();
			}
		}
		return null;
	}
	
	//opening accounts
	public boolean openSavingsAccount(Customer customer, String accNumber, double opBalance) {
		if(customer == null) {
			return false;
		}
	    if (findAccountByAccNumber(accNumber) != null) {
	            System.out.println("Account number already exists.");
	            return false;
	    }
	    SavingsAccount newSacc = new SavingsAccount(accNumber, opBalance);
	    customer.setSavingsAccount(newSacc);
        System.out.println("Savings account " + accNumber + " opened for " + customer.getName());
        return true;
    }
	
	public boolean openCheckingAccount(Customer customer, String accNumber, double opBalance) {
		if(customer == null) {
			return false;
		}
	    if (findAccountByAccNumber(accNumber) != null) {
	            System.out.println("Account number already exists.");
	            return false;
	    }
	    CheckingAccount newCacc = new CheckingAccount(accNumber, opBalance);
	    customer.setCheckingAccount(newCacc);
		System.out.println("Checking account " + accNumber + " opened for " + customer.getName());
		return true;
	}
	
	//closing accounts
	public boolean closeAccount(String accNumber) {
		Customer customer = findCustomerByAccNumber(accNumber);
		if(customer == null) {
			System.out.println("Account not found");
			return false;
		}
		if(customer.getSavingsAccount() != null && customer.getSavingsAccount().getAccountNumber().equals(accNumber)) {
			customer.closeSavingsAccount();
			System.out.println("Savings account " + accNumber + " closed for " + customer.getName());
			return true;			
		}
		if(customer.getCheckingAccount() != null && customer.getCheckingAccount().getAccountNumber().equals(accNumber)) {
			customer.closeCheckingAccount();
			System.out.println("Checking account " + accNumber + " closed for " + customer.getName());
			return true;			
		}
		return false;
	}
	
	public void displayAllAccounts() {
		System.out.println("-------------------- All Accounts --------------------");
		for(int i = 0; i < customerCount; i++) {
			customers[i].printCustomerDetails();
		}
		System.out.println("----------------------------------------");
	}
	public void displayAllDeductions() {
		System.out.println("-------------------- All Deductions --------------------");
		for(int i = 0; i < customerCount; i++) {
			Customer c = customers[i];
			if (c.getSavingsAccount() != null) {
				c.getSavingsAccount().displayDeductions();
			}
			if (c.getCheckingAccount() != null) {
				c.getCheckingAccount().displayDeductions();
			}
		}
		System.out.println("--------------------------------------");
	}
	
	public static String generateUniqueAccountNumber() {
	    return ACCOUNT_PREFIX + (accountNumberCounter++);
	}
}