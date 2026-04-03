package database;

public class Deposit {
    //Private fields
    private final double amountDeposited;
    private final int accountId;
    //Constructor for object creation
    public Deposit(double amountDeposited, int accountId) {
        this.amountDeposited = amountDeposited;
        this.accountId = accountId;
    }
    //Prints the deposit information
    public void printInfo () {
        System.out.println("Account ID: " + accountId);
        System.out.println("Amount Deposited: " + amountDeposited);
        System.out.println();
    }
}
