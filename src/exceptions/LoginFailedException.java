package exceptions;

public class LoginFailedException extends Exception {
    public LoginFailedException() {
        super("Login failed. Attempts exceeded limit.");
    }
}
