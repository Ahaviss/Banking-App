package logic;

import database.*;
import enums.AccountStatus;
import main.Main;
import utilities.ProjectUtils;
import java.util.Random;
import java.util.ArrayList;

public class AccountLogic {
    //RNG for account ID
    public static Random random = new Random();
    public static Account withdraw (Account account) {
        while (true) {
            //Asks for the withdrawal amount
            double withdrawAmount = ProjectUtils.getValidDouble(String.format("Enter the amount you want to withdraw (%.2f available): ", account.getBalance()));
            //Validates the withdrawal amount
            if (withdrawAmount > account.getBalance()) {
                System.out.println("Insufficient balance.");
                //Asks to retry the withdrawal
                if (!Main.askToContinue()) {
                    return account;
                }
                continue;
            }
            //Sets the new balance
            account.setBalance(account.getBalance() - withdrawAmount);
            while (true) {
                //Asks if the user wants to make another withdrawal
                String answer = ProjectUtils.getValidString("Withdrawal successful. Do you want to make another withdrawal? Y/N");
                if (answer.equalsIgnoreCase("N")) {
                    //Adds the withdrawal to history
                    account.addWithdraw(new Withdraw(withdrawAmount, account.getAccountId()));
                    return account;
                } else if (answer.equalsIgnoreCase("Y")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter Y or N.");
                }
            }
        }
    }
    public static Account deposit (Account account) {
        while (true) {
            //Asks for the deposit amount
            double depositAmount = ProjectUtils.getValidDouble("Enter the amount you want to deposit: ");
            account.setBalance(account.getBalance() + depositAmount);
            //Asks if the user wants to make another deposit
            while (true) {
                String answer = ProjectUtils.getValidString("Deposit successful. Do you want to make another deposit? Y/N");
                if (answer.equalsIgnoreCase("Y")) {
                    break;
                } else if (answer.equalsIgnoreCase("N")) {
                    //Adds the deposit to history
                    account.addDeposit(new Deposit(depositAmount, account.getAccountId()));
                    return account;
                } else {
                    System.out.println("Invalid input. Please enter Y or N.");
                }
            }
        }
    }
    public static ArrayList<Account> transfer (ArrayList<Account> accounts, int sourceAccountIndex) {
        //Asks the user for the recipient ID and amount to transfer
        while (true) {
            int recipientAccountId = ProjectUtils.getValidInt("Enter the ID of the recipient account: ");
            double transferAmount = ProjectUtils.getValidDouble(String.format("Enter the amount you want to transfer (%.2f available): ", accounts.get(sourceAccountIndex).getBalance()));
            //Validates amount to transfer
            if (transferAmount > accounts.get(sourceAccountIndex).getBalance()) {
                System.out.println("Insufficient balance.");
                continue;
            }
            int recipientIndex = loopThroughAccounts(accounts, recipientAccountId);
            if (recipientIndex == -1) {
                System.out.println("Account not found.");
                continue;
            }
            //Updates the balance
            accounts.get(sourceAccountIndex).setBalance(accounts.get(sourceAccountIndex).getBalance() - transferAmount);
            accounts.get(recipientIndex).setBalance(accounts.get(recipientIndex).getBalance() + transferAmount);
            //Adds the transfer to history
            accounts.get(sourceAccountIndex).addTransfer(new Transfer(transferAmount, recipientAccountId, accounts.get(sourceAccountIndex).getAccountId(), "Outgoing"));
            accounts.get(recipientIndex).addTransfer(new Transfer(transferAmount, recipientAccountId, accounts.get(sourceAccountIndex).getAccountId(), "Incoming"));
            System.out.println("Transfer successful!");
            return accounts;
        }
    }
    public static ArrayList<Account> createAccount (ArrayList<Account> accounts) {
        //Asks the user for the number of accounts to add
        int amountOfAccountToAdd = ProjectUtils.getValidInt("Enter the amount of accounts you want to add: ");
        for (int i = 0; i < amountOfAccountToAdd; i++) {
            while (true) {
                //Asks for the account details
                String name = ProjectUtils.getValidString("Enter the account holder's name: ");
                double balance = ProjectUtils.getValidDouble("Enter the account holder's balance: ");
                int creditScore = ProjectUtils.getValidInt("Enter the account holder's credit score");
                //Validates the credit score
                if (creditScore < 500 || creditScore > 800) {
                    System.out.println("Invalid credit score. Please enter a number between 500 and 800.");
                    continue;
                }
                //Asks for the account password
                String accountPassword = ProjectUtils.getValidPassword("Enter the account holder's password: ");
                //Generates a random account ID
                int accountId = random.nextInt(9999999 - 1000000 + 1) + 1000000;
                //Makes sure that the ID is not already taken
                while (true) {
                    if (loopThroughAccounts(accounts, accountId) == -1) {
                        break;
                    } else {
                        accountId++;
                        if (accountId > 9999999) {
                            accountId = 1000000;
                        }
                    }
                }
                //Prints the account ID and adds to the accounts list
                System.out.println("Account ID: " + accountId);
                accounts.add(new Account(accountId, name, balance, accountPassword, AccountStatus.ACTIVE, creditScore));
                System.out.println("Account created successfully!");
                break;
            }
        }
        return accounts;
    }
    public static int loopThroughAccounts (ArrayList<Account> accounts, int accountId) {
        //Finds a specific account using the account ID
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountId() == accountId) {
                return i;
            }
        }
        return -1;
    }
    public static ArrayList<Account> deleteAccounts (ArrayList<Account> accounts) {
        //Checks if the accounts list is empty
        if (!ProjectUtils.checkArrayList(accounts)) {
            System.out.println("No accounts available. Please create an account.");
            return null;
        }
        while (true) {
            try {
                //Asks the number of accounts to delete
                int amountOfPeople = ProjectUtils.getValidInt(String.format("Enter the amount of accounts you want to delete (%d total accounts): ", accounts.size()));
                //Validates input
                if (amountOfPeople > accounts.size()) {
                    System.out.println("Invalid input. Please enter a number less than or equal to the number of accounts.");
                    continue;
                }
                for (int i = 0; i < amountOfPeople; i++) {
                    while (true) {
                        //Asks for the account ID to delete
                        int accountId = ProjectUtils.getValidInt("Enter the ID of the account you want to delete: ");
                        int accountIndex = loopThroughAccounts(accounts, accountId);
                        //Validates input
                        if (accountIndex == -1) {
                            System.out.println("Account not found.");
                            continue;
                        }
                        //Deletes the account
                        accounts.remove(accountIndex);
                        System.out.println("Account deleted successfully!");
                        break;
                    }
                }
                return accounts;
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
    public static Account editPassword (Account account) {
        try {
            boolean passwordValidated = false;
            for (int i = 0; i < 3; i++) {
                //Asks the user for the current password and validates it
                System.out.printf("Password change attempt %d/3%n", i + 1);
                String currentPassword = ProjectUtils.getValidPassword("Enter the current password: ");
                if (!account.getAccountPassword().equals(currentPassword)) {
                    System.out.println("Incorrect password. Please try again.");
                } else {
                    passwordValidated = true;
                    break;
                }
            }
            //If the password is not validated, return null
            if (!passwordValidated) {
                System.out.println("Password change failed. Please try again.");
                return null;
            }
            //Asks the user for the new password, validates it and sets it
            String password = ProjectUtils.getValidPassword("Enter the new password: ");
            account.setAccountPassword(password);
            return account;
        //Catch invalid input
        } catch (Exception e) {
            System.out.printf("An unexpected error occurred: %s%n", e.getMessage());
            return account;
        }
    }
    public static Account editAccountHolder(Account account) {
        //Asks the user for the new account holder's name and sets it
        String name = ProjectUtils.getValidString("Enter the new account holder's name: ");
        account.setAccountHolder(name);
        return account;
    }
    public static Account editAccountStatus (Account account) {
        while (true) {
            try {
                //Asks the user for the new account status and validates it
                String status = ProjectUtils.getValidString("Enter the new account status (active/inactive): ");
                if (status.equalsIgnoreCase("active")) {
                    account.setAccountStatus(AccountStatus.ACTIVE);
                } else if (status.equalsIgnoreCase("inactive")) {
                    account.setAccountStatus(AccountStatus.LOCKED);
                } else {
                    System.out.println("Invalid input. Please enter 'active' or 'inactive'.");
                    continue;
                }
                return account;
            }
            //Catch invalid input
            catch (Exception e) {
                System.out.printf("An unexpected error occurred: %s%n", e.getMessage());
            }
        }
    }
    public static Account editCreditScore (Account account) {
        while (true) {
            try {
                //Asks the user for the new credit score and validates it
                int creditScore = ProjectUtils.getValidInt("Enter the new credit score: ");
                if (creditScore < 500 || creditScore > 800) {
                    System.out.println("Invalid credit score. Please enter a number between 500 and 800.");
                    continue;
                }
                account.setCreditScore(creditScore);
                return account;
            }
            //Catch invalid input
            catch (Exception e) {
                System.out.printf("An unexpected error occurred: %s%n", e.getMessage());
            }
        }
    }
    public static Account createOneAccount (ArrayList<Account> accounts) {
        while (true) {
            try {
                //Asks the user for the account details
                String name = ProjectUtils.getValidString("Enter the account holder's name: ");
                double balance = ProjectUtils.getValidDouble("Enter the account holder's balance: ");
                int creditScore = ProjectUtils.getValidInt("Enter the account holder's credit score");
                //Validates the credit score
                if (creditScore < 500 || creditScore > 800) {
                    System.out.println("Invalid credit score. Please enter a number between 500 and 800.");
                    continue;
                }
                //Asks for the account password
                String accountPassword = ProjectUtils.getValidPassword("Enter the account holder's password: ");
                //Generates a random account ID
                int accountId = random.nextInt(9999999 - 1000000 + 1) + 1000000;
                //Makes sure that the ID is not already taken
                while (true) {
                    if (loopThroughAccounts(accounts, accountId) == -1) {
                        break;
                    } else {
                        accountId++;
                        if (accountId > 9999999) {
                            accountId = 1000000;
                        }
                    }
                }
                //Prints the account ID returns the new account
                System.out.println("Account ID: " + accountId);
                System.out.println("Account created successfully!");
                return new Account(accountId, name, balance, accountPassword, AccountStatus.ACTIVE, creditScore);
            }
            //Catch invalid input
            catch (Exception e) {
                System.out.printf("An unexpected error occurred: %s%n", e.getMessage());
            }
        }
    }
}
