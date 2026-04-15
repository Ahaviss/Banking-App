package database;
import java.io.Serial;
import java.io.Serializable;
public class Admin implements Serializable {
    //Private fields
    @Serial
    private static final long serialVersionUID = 1L;
    private final int adminId;
    private String adminName;
    private String adminPassword;
    //Constructor for object creation
    public Admin(int adminId, String adminName, String adminPassword) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminPassword = adminPassword;
    }
    //Getters and setters
    public int getAdminId() {return adminId;}
    public String getAdminName() {return adminName;}
    public String getAdminPassword() {return adminPassword;}
    public void setAdminPassword(String adminPassword) {this.adminPassword = adminPassword;}
    public void setAdminName(String adminName) {this.adminName = adminName;}
    //Prints the admin information
    public void printInfo () {
        System.out.println("Admin ID: " + adminId);
        System.out.println("Admin Name: " + adminName);
    }
}
