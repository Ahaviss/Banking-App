package database;
import java.io.Serial;
import java.io.Serializable;
import enums.TransferDirection;
public class Transfer implements Serializable {
    //Private fields
    @Serial
    private static final long serialVersionUID = 1L;
    private final double amountTransferred;
    private final int targetAccountId;
    private final int sourceAccountId;
    private final TransferDirection direction;
    //Constructor for object creation
    public Transfer(double amountTransferred, int targetAccountId, int sourceAccountId, TransferDirection direction) {
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
