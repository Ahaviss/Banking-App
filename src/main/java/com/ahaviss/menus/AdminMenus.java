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
import com.ahaviss.logic.AdminLogic;
import com.ahaviss.session.Session;
import com.ahaviss.utilities.ProjectUtils;

import java.util.Map;

public class AdminMenus {
    private final AdminLogic adminLogic;
    private final AccountLogic accountLogic;
    private final ProjectUtils projectUtils;
    public AdminMenus(AdminLogic adminLogic, AccountLogic accountLogic, ProjectUtils projectUtils) {this.adminLogic = adminLogic; this.accountLogic = accountLogic; this.projectUtils = projectUtils;}
    public void editAdmin () {
        //Owner option to edit admins
        while (true) {
            try {
                while (true) {
                    //Checks admin list
                    if (!ProjectUtils.checkMap(Session.getAdmins())) {
                        System.out.println("No admins available. Please create an admin.");
                        return;
                    }
                    int amountOfAdminsToEdit = projectUtils.getValidInt(String.format("Enter the amount of the admins you want to edit (%d total admins): ", Session.getAdmins().size()));
                    //Gets valid input
                    if (amountOfAdminsToEdit > Session.getAdmins().size()) {
                        System.out.println("Invalid input. Please enter a number less than or equal to the number of admins.");
                        continue;
                    } else if (amountOfAdminsToEdit == 0) {
                        System.out.println("No admins edited.");
                        return;
                    }
                    for (int i = 0; i < amountOfAdminsToEdit; i++) {
                        Admin admin;
                        while (true) {
                            //Gets the ID of the admin to edit
                            int adminId = projectUtils.getValidInt("Enter the ID of the admin you want to edit: ");
                            admin = Session.getAdmins().get(adminId);
                            //Checks if admin is found
                            if (admin == null) {
                                System.out.printf("Admin ID %d not found", adminId);
                                continue;
                            }
                            break;
                        }

                        while (true) {
                            //Admin editing options
                            String option = projectUtils.getValidString("Edit Name, Edit Password, Quit editing");
                            switch (option.toLowerCase()) {
                                case "edit name" ->
                                    //Calls editAdminName method
                                    adminLogic.editAdminName(admin);
                                case "edit password" ->
                                    //Calls editPassword method
                                    adminLogic.editPassword(admin);
                                case "quit editing" -> {return;}
                                default -> {
                                    //Invalid option
                                    System.out.println("Invalid option. Please try again.");
                                    continue;
                                }
                            }
                            //Ask to make more changes
                            if (!projectUtils.askToContinue()) {
                                return;
                            }
                            break;
                        }
                    }
                    break;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
            catch (Exception e) {
                System.out.printf("An unexpected error occurred: %s%n", e.getMessage());
            }
        }
    }
    public ControlFlow adminPanel () {
        while (true) {
            //If not admin or owner
            if (Session.getRole() != LoginEnums.ADMIN && Session.getRole() != LoginEnums.OWNER) {
                System.out.println("You are not authorized to access this panel.");
                continue;
            }
            //Option declared outside the if-else, allowing both conditions to edit the String option
            String option;
            //Owner panel option for the owner
            if (Session.getRole() == LoginEnums.OWNER) {
                option = projectUtils.getValidString("Add Accounts, Delete Accounts, Edit accounts, Logout, Owner Panel, Quit program");
            }
            //General admin panel option
            else {
                option = projectUtils.getValidString("Add Accounts, Delete Accounts, Edit accounts, Logout, Quit program");
            }
            switch (option.toLowerCase()) {
                case "add accounts" ->
                    //Calls addAccount method
                    accountLogic.createAccount(Session.getAccounts(), Session.getCurrentAdmin());
                case "delete accounts" -> {
                    //Calls deleteAccount method
                    Map<Integer, Account> tempAccounts = accountLogic.deleteAccounts(Session.getAccounts(), Session.getCurrentAdmin());
                    if (tempAccounts != null) {
                        //Edits the accounts list only if tempAccount is not null
                        Session.setAccounts(tempAccounts);
                    }
                }
                case "edit accounts" ->
                    //Calls editAccount method
                    new AccountMenus(accountLogic, projectUtils).editAccount();
                case "logout" -> {
                    //Logs out the user
                    System.out.println("Logging out...");
                    //Sets user role to none
                    Session.setRole(LoginEnums.NONE);
                    Session.setCurrentAdmin(null);
                    return ControlFlow.MAIN_MENU;
                }
                case "quit program" -> {
                    System.out.println("Terminating program...");
                    //Send a quit message
                    return ControlFlow.QUIT;
                }
                default -> {
                    //Access the owner panel option only if the role is the owner
                    if (option.equalsIgnoreCase("owner panel") && Session.getRole() == LoginEnums.OWNER) {
                        ControlFlow controlFlow = new OwnerMenus(projectUtils, adminLogic, new GeneralMenus(projectUtils)).ownerPanel();
                        if (controlFlow == ControlFlow.MAIN_MENU) return ControlFlow.MAIN_MENU;
                        if (controlFlow == ControlFlow.BACK) continue;
                        if (controlFlow == ControlFlow.QUIT) return ControlFlow.QUIT;
                    }
                    //General admin panel error message
                    System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }
}
