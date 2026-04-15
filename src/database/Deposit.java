package database;
import java.io.Serial;
import java.io.Serializable;
public class Deposit implements Serializable {
    //Private fields
    @Serial
    private static final long serialVersionUID = 1L;
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
