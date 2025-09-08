
public class Customer {
	private final String name;
	private final String address;
	private final String phone;
	
	private SavingsAccount savingsAccount;
	private CheckingAccount checkingAccount;
	
	
//Constructor
	public Customer (String name, String address, String phone) {
		if(name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null or empty");
		}
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

//Setter/Getters ============================
	public String getName() { return name; }
	public String getAddress() { return address; }
	public String getPhone() { return phone; }
	
	public SavingsAccount getSavingsAccount() { return savingsAccount; }
	public CheckingAccount getCheckingAccount() { return checkingAccount; }
	
    public void setSavingsAccount(SavingsAccount acc) {
        this.savingsAccount = acc;
        if (acc != null) acc.setOwner(this);
    }

    public void setCheckingAccount(CheckingAccount acc) {
        this.checkingAccount = acc;
        if (acc != null) acc.setOwner(this);
    }	
    
    public void closeSavingsAccount() {
        this.savingsAccount = null;
    }

    public void closeCheckingAccount() {
        this.checkingAccount = null;
    }
	
//accounts
	public void openSavingsAccount(String accNumber, double opBalance) {
		if(this.savingsAccount != null) {
			System.out.println("Customer already has a savings account");
			return ;
		}
		this.savingsAccount = new SavingsAccount(accNumber, opBalance);
		this.savingsAccount.setOwner(this);
		System.out.println("Savings account " + accNumber + " opened for customer " + name);
	}
	public void openCheckingAccount(String accNumber, double opBalance) {
		if(this.checkingAccount != null) {
			System.out.println("Customer already has a checking account");
			return ;
		}
		this.checkingAccount = new CheckingAccount(accNumber, opBalance);
		this.checkingAccount.setOwner(this);
		System.out.println("Checking account " + accNumber + " opened for customer " + name);
	}
	
//details
	public void printCustomerDetails() {
		System.out.println("----- Customer Details -----");
		System.out.println("Name: " + name);
		System.out.println("Address: " + address);
		System.out.println("Phone: " + phone);
		if(savingsAccount != null) {
			System.out.println("Savings Account: " + savingsAccount.getAccountNumber() + ", Balance: " + savingsAccount.getBalance());
		} else {
			System.out.println("No Savings Account");
		}
		if(checkingAccount != null) {
			System.out.println("Checking Account: " + checkingAccount.getAccountNumber() + ", Balance: " + checkingAccount.getBalance());
		} else {
			System.out.println("No Checking Account");
		}
		System.out.println("----------------------------");
	}
}