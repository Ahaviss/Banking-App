package database;
import utilities.ProjectUtils;

import java.io.Serial;
import java.io.Serializable;

public class Owner implements Serializable {
    //Private fields
    @Serial
    private static final long serialVersionUID = 1L;
    private String username = "tempUsername@123";
    private String password = ProjectUtils.hashPassword("tempPassword@123");
    public void setPassword (String password) {
        this.password = ProjectUtils.hashPassword(password);
    }
    public String getPassword () {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
