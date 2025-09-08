
// Main.java
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Bank bank = new Bank();

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== Account Management System (Admin) ===");
            System.out.println("1) Open New Account");
            System.out.println("2) Close Account");
            System.out.println("3) Login to Account (by account number)");
            System.out.println("4) Set Savings Interest Rate");
            System.out.println("5) Display All Account Details");
            System.out.println("6) Display All Account Deductions");
            System.out.println("7) Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    openNewAccountMenu();
                    break;
                case "2":
                    closeAccountMenu();
                    break;
                case "3":
                    loginToAccountMenu();
                    break;
                case "4":
                    setInterestRateMenu();
                    break;
                case "5":
                    bank.displayAllAccounts();
                    break;
                case "6":
                    bank.displayAllDeductions();
                    break;
                case "7":
                    exit = true;
                    System.out.println("Exiting. Goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void openNewAccountMenu() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter address: ");
        String addr = scanner.nextLine().trim();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine().trim();

        // Always create a new customer
        Customer cust = bank.createCustomer(name, addr, phone);

        System.out.println("Open which account? 1) Savings  2) Checking");
        String t = scanner.nextLine().trim();
        String accNum = Bank.generateUniqueAccountNumber();
        System.out.println("Assigned Account Number: " + accNum);
        System.out.print("Enter opening balance: ");
        double bal = readDoubleInput();

        if (t.equals("1")) {
            bank.openSavingsAccount(cust, accNum, bal);
        } else {
            bank.openCheckingAccount(cust, accNum, bal);
        }
    }

    private static void closeAccountMenu() {
        System.out.print("Enter account number to close: ");
        String acc = scanner.nextLine().trim();
        bank.closeAccount(acc);
    }

    private static void loginToAccountMenu() {
        System.out.print("Enter account number to login: ");
        String acc = scanner.nextLine().trim();
        AbstractAccount account = bank.findAccountByAccNumber(acc);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
        System.out.println("Logged into account " + acc + " (" + account.getAccountType() + ")");
        boolean logout = false;
        while (!logout) {
            System.out.println("\nAccount Menu:");
            System.out.println("1) Check Balance");
            System.out.println("2) Print Statement");
            System.out.println("3) Deposit");
            System.out.println("4) Withdraw");
            System.out.println("5) Transfer");
            System.out.println("6) Calculate Zakat (Savings only)");
            System.out.println("7) Calculate Interest (Savings only)");
            System.out.println("8) Display Deductions");
            System.out.println("9) Back to Admin Menu");
            System.out.print("Choose: ");
            String ch = scanner.nextLine().trim();
            switch (ch) {
                case "1":
                    account.checkBalance();
                    break;
                case "2":
                    account.printStatement();
                    break;
                case "3":
                    System.out.print("Amount to deposit: ");
                    double dam = readDoubleInput();
                    account.makeDeposit(dam);
                    break;
                case "4":
                    System.out.print("Amount to withdraw: ");
                    double wam = readDoubleInput();
                    account.makeWithdrawal(wam);
                    break;
                case "5":
                    System.out.print("Enter target account number: ");
                    String tar = scanner.nextLine().trim();
                    AbstractAccount target = bank.findAccountByAccNumber(tar);
                    if (target == null) {
                        System.out.println("Target account not found.");
                    } else {
                        System.out.print("Amount to transfer: ");
                        double tam = readDoubleInput();
                        account.transferAmount(target, tam);
                    }
                    break;
                case "6":
                    if (account instanceof SavingsAccount) {
                        double zakat = ((SavingsAccount) account).calculateZakat();
                        System.out.println("Zakat calculated: " + zakat);
                    } else {
                        System.out.println("Zakat applies only to Savings accounts.");
                    }
                    break;
                case "7":
                    if (account instanceof SavingsAccount) {
                        ((SavingsAccount) account).calculateInterest();
                    } else {
                        System.out.println("Interest applies only to Savings accounts.");
                    }
                    break;
                case "8":
                    account.displayDeductions();
                    break;
                case "9":
                    logout = true;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void setInterestRateMenu() {
        System.out.print("Enter new interest rate as percent (e.g., 2 for 2%): ");
        double pct = readDoubleInput();
        double rate = pct / 100.0;
        SavingsAccount.setInterestRate(rate);
    }

    private static double readDoubleInput() {
        double val = 0.0;
        try {
            String s = scanner.nextLine().trim();
            val = Double.parseDouble(s);
        } catch (Exception e) {
            System.out.println("Invalid number. Using 0.");
            val = 0.0;
        }
        return val;
    }
}