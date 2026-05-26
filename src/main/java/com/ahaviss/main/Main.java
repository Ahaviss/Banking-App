package com.ahaviss.main;

import com.ahaviss.logic.AccountLogic;
import com.ahaviss.logic.AdminLogic;
import com.ahaviss.logic.LoginSystem;
import com.ahaviss.logic.Logins;
import com.ahaviss.menus.AccountMenus;
import com.ahaviss.menus.AdminMenus;
import com.ahaviss.utilities.ProjectUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//Local Imports
public class Main {
    //Main method
    public static void main(String[] args) {
        ProjectUtils projectUtils = new ProjectUtils(new BufferedReader(new InputStreamReader(System.in)));
        AccountLogic accountLogic = new AccountLogic(projectUtils);
        new App(projectUtils, accountLogic, new AccountMenus(accountLogic, projectUtils), new AdminMenus(new AdminLogic(projectUtils), accountLogic, projectUtils), new Logins(new LoginSystem(projectUtils))).start();
    }
}
