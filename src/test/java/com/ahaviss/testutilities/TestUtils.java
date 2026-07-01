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
