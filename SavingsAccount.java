
public class SavingsAccount extends AbstractAccount {
	private static double interestRate = 0.02; //2% annual interest rate
	private double totalZakatDeduction = 0.0;
	
//Constructor
	public SavingsAccount(String accNumber, double opBalance) {
		super(accNumber, opBalance);
	}
	
@Override
	public boolean makeWithdrawal(double amount) {
		if(amount <= 0) {
			System.out.println("Withdrawal amount is not correct");
			return false;
		}
		if(amount > this.getBalance()) {
			System.out.println("Insufficient balance");
			return false;
		}
		debit(amount, "Withdrawal");
		System.out.println("Withdrawal of " + amount + " successful");
		return true;
	}

@Override
	public double calculateZakat() {
	double balance = this.getBalance();

		if(balance >= 20000) {
			double zakat = balance * 0.025; //2.5% of balance
			debit(zakat, "Zakat Deduction");
			totalZakatDeduction += zakat;
			System.out.println("Zakat of " + zakat + " deducted from account " + this.getAccountNumber());
			return zakat;
		}
	return 0.0; // no zakat if balance < 20,000
	}

@Override
	public void displayDeductions() {
    	System.out.println("---- Deductions for Savings Account ----");
    	System.out.println("Total Zakat Deducted: " + formatMoney(totalZakatDeduction) + "from account " + this.getAccountNumber());	
    	System.out.println("----------------------------------------");
	}
@Override
	public String getAccountType() {
		return "SVINGS";
	}	

	public void calculateInterest() {
		double balance = this.getBalance();
		double interest = balance * (interestRate); //annual interest
		credit(interest, "Interest");
		System.out.println("Interest of " + interest + " credited to account " + this.getAccountNumber());
	}
	
	public static void setInterestRate(double newRate) {
		if(newRate < 0) {
			System.out.println("Interest rate cannot be negative");
			return ;
		}
		interestRate = newRate;
		System.out.println("Interest rate set to " + (newRate * 100) + "%");
	}
	public static double getInterestRate() {
		return interestRate;
	}
	
}