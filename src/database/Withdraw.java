package database;

public class Withdraw {
    //Private fields
    private double amountWithdrawn;
    private int accountId;
    //Constructor for object creation
    public Withdraw(double amountWithdrawn, int accountId) {
        this.amountWithdrawn = amountWithdrawn;
        this.accountId = accountId;
    }
    //Prints the withdrawal information
    public void printInfo () {
        System.out.println("Account ID: " + accountId);
        System.out.println("Amount Withdrawn: " + amountWithdrawn);
        System.out.println();
    }
}
