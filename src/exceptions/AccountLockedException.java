package exceptions;
//To handle newly locked or previously locked accounts
public class AccountLockedException extends Exception {
    private int accountIndex = -1;
    public AccountLockedException(int accountID) {
        super(String.format("Account is locked. Please contact the bank for assistance.%nCause: %d is locked", accountID));
    }
    public AccountLockedException (int accountID, int accountIndex) {
        super(String.format("Account has been locked. Please contact the bank for assistance.%nCause: Repeated attempts on: %d", accountID));
        this.accountIndex = accountIndex;
    }
    public int traceAccountIndex () {return accountIndex;}
}
