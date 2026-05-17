package com.ahaviss.logic;

import com.ahaviss.database.Account;
import com.ahaviss.database.Admin;
import com.ahaviss.enums.LoginEnums;
import com.ahaviss.exceptions.AccountLockedException;
import com.ahaviss.exceptions.LoginFailedException;
import com.ahaviss.logs.enums.Action;
import com.ahaviss.logs.enums.User;
import com.ahaviss.logs.manager.LogManager;
import com.ahaviss.session.Session;
import com.ahaviss.utilities.ProjectUtils;

public class Logins {
    //Account login
    public static void accountLogin () {
        while (true) {
            try {
                //Check if the accounts list is empty
                if (!ProjectUtils.checkMap(Session.getAccounts())) {
                    System.out.println("No accounts available. Please create an account.");
                    return;
                }
                //Call the login system for accounts
                Account tempUser;
                try {
                    tempUser = LoginSystem.accountLogin(Session.getAccounts());
                }
                catch (AccountLockedException e) {
                    //Traces account
                    Account account = e.traceAccount();
                    String prevStatus;
                    //If account isn't null
                    if (account != null) {
                        prevStatus = String.valueOf(account.getAccountStatus());
                        //Locks account
                        AccountLogic.lockAccount(account);
                        //Prints error message
                        System.out.println(e.getMessage());
                        //Tells user duration of lock
                        if (account.getDurationLocked() == Integer.MAX_VALUE) {
                            System.out.println("Locked permanently.");
                            break;
                        }
                        System.out.printf("Locked for: %d minutes.%n", account.getDurationLocked());
                        LogManager.addLog(Action.ACCOUNT_AUTO_LOCKED, User.USER, String.format("%d (%s)", account.getAccountId(), account.getAccountHolder()), null, prevStatus, String.valueOf(account.getAccountStatus()));
                        return;
                        //If account is null
                    } else {
                        System.out.println(e.getMessage());
                        break;
                    }
                }
                catch (LoginFailedException e) {
                    System.out.println(e.getMessage());
                    break;
                }
                //Login user if the above conditions aren't true
                System.out.println("Login successful!");
                Session.setCurrentAccount(tempUser);
                //Welcome message
                System.out.printf("Welcome back, %s!%n", Session.getCurrentAccount().getAccountHolder());
                //Role is set to user
                Session.setRole(LoginEnums.USER);
                //End loop
                break;
            }
            //Catch invalid input
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
            catch (Exception e) {
                System.out.printf("An unexpected error occurred: %s%n", e.getMessage());
            }
        }
    }
    public static void adminLogin () {
        while (true) {
            try {
                //Calls the login system for admins
                Admin admin = LoginSystem.adminLogin(Session.getAdmins(), Session.getOwner());
                //If the admin was validated as the owner
                if (admin == null) {
                    System.out.println("Welcome back, owner!");
                    //Role is set
                    Session.setRole(LoginEnums.OWNER);
                    break;
                }
                //If the admin is logged in successfully
                Session.setCurrentAdmin(admin);
                System.out.printf("Welcome back %s!%n", Session.getCurrentAdmin().getAdminName());
                //Role is set
                Session.setRole(LoginEnums.ADMIN);
                break;
            }
            //Catch invalid input
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
            catch (Exception e) {
                System.out.printf("An unexpected error occurred: %s%n", e.getMessage());
            }
        }
    }
}
