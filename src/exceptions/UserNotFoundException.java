package exceptions;
import enums.LoginEnums;
public class UserNotFoundException extends Exception {
    public UserNotFoundException(LoginEnums role, String cause) {
        super(String.format("%s not found.%nCause: %s", role == LoginEnums.ADMIN ? "Admin" : "Account", cause));
    }
}
