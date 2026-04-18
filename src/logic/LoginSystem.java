package logic;
//Local imports
import database.Admin;
import database.Owner;
import enums.AccountStatus;
import exceptions.*;
import utilities.ProjectUtils;
import database.Account;
//Java imports
import java.util.ArrayList;
public class LoginSystem {
    //Total tries
    public static int accountLogin (ArrayList<Account> accounts) throws AccountLockedException, LoginFailedException {
        //Arrays to track if the same username is targeted multiple times
        int[] foundUsernames = new int[3];
        int[] indexes = new int[3];
        for (int i = 0; i < 3; i++) {
            System.out.printf("Login attempt %d/3%n", i + 1);
            //Gets the account ID and password
            String tempAccountId = ProjectUtils.getValidString("Enter your account ID: ");
            String tempAccountPassword = ProjectUtils.getValidString("Enter your account password: ");
            String accountPassword = ProjectUtils.hashPassword(tempAccountPassword);
            //Checks if the ID is a number
            boolean isNumber = tempAccountId.matches("\\d+");
            if (!isNumber) {
                System.out.println("Invalid admin ID. Please enter a valid admin ID.");
                continue;
            }
            //Parses the ID to an integer
            int accountId = Integer.parseInt(tempAccountId);
            for (int j = 0; j < accounts.size(); j++) {
                //Checks if the account ID and password match the current account which is being checked
                if (accounts.get(j).getAccountId() == accountId && accounts.get(j).getAccountPassword().equals(accountPassword)) {
                    //Checks if the account is locked if the above is true
                    if (accounts.get(j).getAccountStatus() == AccountStatus.LOCKED) {
                        throw new AccountLockedException(accounts.get(j).getAccountId());
                    }
                    //Otherwise, return the index
                    return j;
                }
                //If none of the above is true, check if the account ID matches input
                if (accounts.get(j).getAccountId() == accountId) {
                    foundUsernames[i] = accountId;
                    indexes[i] = j;
                    break;
                }
                else foundUsernames[i] = -1;
            }
            System.out.println("Invalid account ID or password. Please try again.");
        }
        //If attempts are exceeded
        System.out.println("Unauthorised access. Please try again.");
        int amountOfTimes = 0;
        //Checks if the same ID was targeted multiple times
        int repeatedUsername = foundUsernames[0];
        for (int foundUsername : foundUsernames) {
            if (foundUsername == repeatedUsername) {
                amountOfTimes++;
            }
        }
        //Returns the value to indicate the account should be locked
        if (amountOfTimes >= 3) {
            throw new AccountLockedException(accounts.get(indexes[0]).getAccountId(), indexes[0]);
        }
        //Otherwise
        throw new LoginFailedException();
    }
    public static int adminLogin (ArrayList<Admin> admins, Owner owner) {
        for (int i = 0; i < 3; i++) {
            System.out.printf("Login attempt %d/3%n", i + 1);
            //Gets the admin ID and password
            String adminId = ProjectUtils.getValidString("Enter your admin ID: ");
            String tempAdminPassword = ProjectUtils.getValidString("Enter your admin password: ");
            String adminPassword = ProjectUtils.hashPassword(tempAdminPassword);
            //Checks if the ID and password match the owner
            if (adminId.equals(owner.getUsername()) && adminPassword.equals(owner.getPassword())) {
                return Integer.MIN_VALUE;
            }
            //Checks if the ID is a number
            boolean isNumber = adminId.matches("[0-9]+");
            if (!isNumber) {
                System.out.println("Invalid admin ID. Please enter a valid admin ID.");
                continue;
            }
            //Checks if the input matches the current admin being checked
            int adminIdInt = Integer.parseInt(adminId);
            for (int j = 0; j < admins.size(); j++) {
                if (admins.get(j).getAdminId() == adminIdInt && admins.get(j).getAdminPassword().equals(adminPassword)) {
                    //If the input matches, return the index
                    return j;
                }
            }
        }
        //If attempts are exceeded
        System.out.println("Unauthorised access. Defaulting...");
        //Terminates the JVM
        System.exit(0);
        return -1;
    }
}
