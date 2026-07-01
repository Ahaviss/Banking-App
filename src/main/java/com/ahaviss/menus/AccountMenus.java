/*
 * Copyright [2026] [Ahaviss]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ahaviss.menus;

import com.ahaviss.database.Account;
import com.ahaviss.database.Admin;
import com.ahaviss.enums.ControlFlow;
import com.ahaviss.enums.LoginEnums;
import com.ahaviss.logic.AccountLogic;
import com.ahaviss.session.Session;
import com.ahaviss.utilities.ProjectUtils;

public class AccountMenus {
    private final AccountLogic accountLogic;
    private final ProjectUtils projectUtils;
    public AccountMenus(AccountLogic accountLogic, ProjectUtils projectUtils) {this.accountLogic = accountLogic; this.projectUtils = projectUtils;}
    public ControlFlow accountPanel () {
        while (true) {
            //Account holder options
            System.out.println("Account Panel");
            String option = projectUtils.getValidString("Deposit, Withdraw, Transfer, View Balance, View History, View Account Info, Logout, Change Password, Quit Program");
            switch (option.toLowerCase()) {
                case "view account info"->
                    //Print account information
                    Session.getCurrentAccount().printInfo();
                case "deposit" ->
                    //Call deposit method
                    accountLogic.deposit(Session.getCurrentAccount());
                case "withdraw" ->
                    //Call withdraw method
                    accountLogic.withdraw(Session.getCurrentAccount());
                case "transfer" ->
                    //Call transfer method
                    accountLogic.transfer(Session.getAccounts(), Session.getCurrentAccount());
                case "view balance" ->
                    //Get user balance
                    System.out.println("$" + Session.getCurrentAccount().getBalance());
                case "view history" ->
                    //Print account logs
                    Session.getCurrentAccount().printHistory();
                case "logout" -> {
                    //Logs out the user
                    System.out.println("Logging out...");
                    //Sets user role to none
                    Session.setRole(LoginEnums.NONE);
                    Session.setCurrentAccount(null);
                    return ControlFlow.MAIN_MENU;
                }
                case "change password" -> {
                    //Call edit method
                    Account newAcc = accountLogic.editPassword(Session.getCurrentAccount());
                    if (newAcc != null) {
                        //Check if the account isn't null
                        Session.setCurrentAccount(newAcc);
                    }
                }
                case "quit program" -> {
                    System.out.println("Terminating program...");
                    //Send a quit message
                    return ControlFlow.QUIT;
                }
                default ->
                    //Invalid option
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    public void editAccount () {
        //Checks if the accounts list is empty
        if (!ProjectUtils.checkMap(Session.getAccounts())) {
            System.out.println("No accounts available. Please create an account.");
            return;
        }
        //Gets the number of accounts to edit
        int amountOfAccountToEdit = projectUtils.getValidInt(String.format("Enter the amount of the accounts you want to edit (%d total accounts): ", Session.getAccounts().size()));
        //Gets a valid input
        if (amountOfAccountToEdit > Session.getAccounts().size()) {
            System.out.println("Invalid input. Please enter a number less than or equal to the number of accounts.");
            return;
        } else if (amountOfAccountToEdit == 0) {
            System.out.println("No accounts edited.");
            return;
        }
        for (int i = 0; i < amountOfAccountToEdit; i++) {
            while (true) {
                //Gets the ID of the account to edit
                int accountId = projectUtils.getValidInt("Enter the ID of the account you want to edit: ");
                Account account = Session.getAccounts().get(accountId);
                //Checks if account is found
                if (account == null) {
                    System.out.printf("Account ID %d not found.%n", accountId);
                    continue;
                }
                while (true) {
                    //Account editing options
                    Admin admin = null;
                    if (Session.getRole() == LoginEnums.ADMIN) {
                        admin = Session.getCurrentAdmin();
                    }
                    String whatToEdit = projectUtils.getValidString("Edit Holder, Edit Password, Edit Credit Score, Edit Account Status, Quit Editing");
                    switch (whatToEdit.toLowerCase()) {
                        case "edit holder" ->
                            //Call editAccountHolder method
                            accountLogic.editAccountHolder(account, admin);
                        case "edit password" ->
                            //Call editPassword method
                            accountLogic.editPasswordAdmin(account, admin);
                        case "edit credit score" ->
                            //Call editCreditScore method
                            accountLogic.editCreditScore(account, admin);
                        case "edit account status" ->
                            //Call editAccountStatus method
                            accountLogic.editAccountStatus(account, admin);
                        case "quit editing" -> {return;}
                        default -> {
                            //Invalid option
                            System.out.println("Invalid option. Please try again.");
                            continue;
                        }
                    }
                    break;
                }
                //Ask to make more changes
                if (!projectUtils.askToContinue()) {
                    return;
                }
                break;
            }
        }
    }

}
