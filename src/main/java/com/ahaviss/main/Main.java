package com.ahaviss.main;
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
