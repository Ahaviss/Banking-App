package database;
//Java imports
import java.util.ArrayList;
//Local imports
import enums.AccountStatus;
public class Account {
    //Private fields
    private final int accountId;
    private String accountHolder;
    private double balance;
    private String accountPassword;
    private AccountStatus accountStatus;
    private int creditScore;
    //ArrayLists for deposit, withdraw, and transfer history
    private final ArrayList<Deposit> deposits = new ArrayList<>();
    private final ArrayList<Withdraw> withdraws = new ArrayList<>();
    private final ArrayList<Transfer> transfers = new ArrayList<>();
    //Constructor for object creation
    public Account(int accountId, String accountHolder, double balance, String accountPassword, AccountStatus accountStatus, int creditScore) {
        this.accountId = accountId;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.accountPassword = accountPassword;
        this.accountStatus = accountStatus;
        this.creditScore = creditScore;
    }
    //Getters and setters
    public int getCreditScore() {return creditScore;}
    public void setCreditScore(int creditScore) {this.creditScore = creditScore;}
    public AccountStatus getAccountStatus() {return accountStatus;}
    public void setAccountStatus(AccountStatus accountStatus) {this.accountStatus = accountStatus;}
    public String getAccountPassword() {return accountPassword;}
    public void setAccountPassword(String accountPassword) {this.accountPassword = accountPassword;}
    public void addDeposit(Deposit deposit) {deposits.add(deposit);}
    public void addWithdraw(Withdraw withdraw) {withdraws.add(withdraw);}
    public void addTransfer(Transfer transfer) {transfers.add(transfer);}
    public ArrayList<Deposit> getDeposits() {return deposits;}
    public ArrayList<Withdraw> getWithdraws() {return withdraws;}
    public ArrayList<Transfer> getTransfers() {return transfers;}
    public int getAccountId() {return accountId;}
    public String getAccountHolder() {return accountHolder;}
    public double getBalance() {return balance;}
    public void setBalance(double balance) {this.balance = balance;}
    public void setAccountHolder(String accountHolder) {this.accountHolder = accountHolder;}
    //Prints the account logs/history
    public void printHistory () {
        System.out.println("Account History:");
        System.out.println("Account ID: " + accountId);
        System.out.println("Deposits:");
        for (Deposit deposit : deposits) {
            deposit.printInfo();
        }
        System.out.println("Withdraws:");
        for (Withdraw withdraw : withdraws) {
            withdraw.printInfo();
        }
        System.out.println("Transfers:");
        for (Transfer transfer : transfers) {
            transfer.printInfo();
        }
    }
    //Prints the account information
    public void printInfo() {
        System.out.println("Account ID: " + accountId);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Account Status: " + accountStatus);
        System.out.println("Credit Score: " + creditScore);
        System.out.println("Balance: " + balance);
    }
}
