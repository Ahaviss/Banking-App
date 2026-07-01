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

import com.ahaviss.logs.manager.LogManager;
import com.ahaviss.save.SaveData;
import com.ahaviss.session.Session;
import com.ahaviss.utilities.ProjectUtils;

public class GeneralMenus {
    private final ProjectUtils projectUtils;
    public GeneralMenus (ProjectUtils projectUtils) {this.projectUtils = projectUtils;}
    public void manageLogs () {
        while (true) {
            try {
                String option = projectUtils.getValidString("Print logs, Clear all logs, Quit managing.");
                if (option.equalsIgnoreCase("print logs")) {
                    //Prints logs
                    LogManager.printLogs();
                } else if (option.equalsIgnoreCase("clear all logs")) {
                    //Clears current arraylist and deletes file
                    LogManager.clearLogs();
                    SaveData.clearLogs();
                } else if (option.equalsIgnoreCase("quit managing")) {
                    return;
                    //Invalid input
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            }
            catch (Exception e) {
                System.out.printf("An unexpected error occurred: %s%n", e.getMessage());
            }
        }
    }
    public void manageAutoSaver () {
        while (true) {
            //Asks for option
            String option = projectUtils.getValidString("Change Autosave Duration, Stop Autosaver, Start Autosaver, Force Start Autosaver, Quit Managing");
            switch (option.toLowerCase()) {
                //Changes autosaver duration (converts to milliseconds)
                case "change autosave duration" -> {
                    long duration = projectUtils.getValidLong("New autosave duration (sec):");
                    Session.changeSaveDuration(duration * 1000);
                }
                //Stops autosaver
                case "stop autosaver" -> Session.stopAutoSaver();
                //Starts autosaver if autosaver isn't on
                case "start autosaver" -> {
                    if (Session.isAutoSaverRunning()) System.out.println("Autosaver is already running");
                    else Session.restartAutoSaver();
                }
                //Starts autosaver no matter its state
                case "force start autosaver" -> Session.restartAutoSaver();
                //Returns to the owner panel
                case "quit managing" -> {return;}
                //Invalid input
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
