/*
 * Copyright (c) 2026 Ahaviss. All Rights Reserved.
 *
 * This file is part of the Banking-App project.
 * Unauthorized copying, modification, or distribution of this file,
 * via any medium is strictly prohibited.
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
