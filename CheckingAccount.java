
public class CheckingAccount extends AbstractAccount {
	public static final int FreeTransactions = 2;
	public static final double TransactionFee = 10.0;
	
	private int monthlyTransactionCount = 0;
	private double totalFeesDeducted = 0.0;
	
//Constructor
	public CheckingAccount(String accNumber, double opBalance) {
		super(accNumber, opBalance);
	}
		
@Override
	public boolean makeWithdrawal(double amount) {
		if(amount <= 0) {
			System.out.println("Withdrawal amount is not correct");
			return false;
		}
        // overdraft allowed up to -5000
		if(this.getBalance() - amount < -5000) {
			System.out.println("Insufficient balance, overdraft limit exceeded");
			return false;
		}
		debit(amount, "Withdrawal");
		applyTransactionFee("WithdrawalFee");
		System.out.println("Withdrawal of " + amount + " successful");	
		return true;
	}

@Override
	public boolean makeDeposit(double amount) {
		if(amount <= 0)	 {
			System.out.println("Deposit amount is not correct");
			return false;
		}
		credit(amount, "Deposit");
		applyTransactionFee("DepositFee");
		System.out.println("Deposit of " + amount + " successful");
		return true;
	}

@Override
	public double calculateZakat() {
		// No zakat for checking accounts
		return 0.0;
	}

@Override
	public void displayDeductions() {
		System.out.println("---- Deductions for Checking Account ----");
		System.out.println("Total Transaction Fees Deducted: " + formatMoney(totalFeesDeducted));	
		System.out.println("-----------------------------------------");
	}

@Override
	public String getAccountType() {
		return "CHECKING";
	}

	private void applyTransactionFee(String feeType) {
		monthlyTransactionCount++;
		if(monthlyTransactionCount > FreeTransactions) {
			logFee(TransactionFee, feeType);
			totalFeesDeducted += TransactionFee;
			System.out.println("Transaction fee of " + TransactionFee + " applied for " + feeType);
		}
	}
}
