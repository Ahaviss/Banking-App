package database;

public class Transfer {
    //Private fields
    private double amountTransferred;
    private int targetAccountId;
    private int sourceAccountId;
    private String direction;
    //Constructor for object creation
    public Transfer(double amountTransferred, int targetAccountId, int sourceAccountId, String direction) {
        this.amountTransferred = amountTransferred;
        this.targetAccountId = targetAccountId;
        this.sourceAccountId = sourceAccountId;
        this.direction = direction;
    }
    //Prints the transfer information
    public void printInfo () {
        System.out.printf("Target Account ID: %d%nSource Account ID: %d%nAmount Transferred: %.2f%n%s%n", targetAccountId, sourceAccountId, amountTransferred, direction);
    }
}
