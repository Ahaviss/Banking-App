/*
 * Copyright (c) 2026 Ahaviss. All Rights Reserved.
 *
 * This file is part of the Banking-App project.
 * Unauthorized copying, modification, or distribution of this file,
 * via any medium is strictly prohibited.
 */

package com.ahaviss.testutilities;

import com.ahaviss.utilities.ProjectUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;

public class TestUtils {
    public static ProjectUtils mockInput (String input) {
        ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(bais));
        return new ProjectUtils(br);
    }
    public static boolean clearDirectory (String folderPath) {
        File file = new File(folderPath);
        if (!file.exists() || !file.isDirectory()) {
            System.out.println(folderPath + " doesn't exist or is not a directory");
            return false;
        }
        File[] files = file.listFiles();
        if (files == null) {
            System.out.println("Restricted access to " + folderPath +", or directory has no files.");
            return false;
        }
        boolean successful = true;
        for (File f : files) {
            if (f.isDirectory()) {if (!clearDirectory(f.getAbsolutePath())) return false;}
            boolean del = f.delete();
            if (!del) {
                System.out.println("Unable to delete file: " + f.getAbsolutePath());
                successful = false;
            }
        }
        return successful;
    }
}
