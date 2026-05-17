package com.ahaviss.testutilities;

import com.ahaviss.utilities.ProjectUtils;
import org.jspecify.annotations.NonNull;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public class TestUtils {
    public static void mockInput (@NonNull String input) {
        ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(bais));
        ProjectUtils.setReader(br);
    }
}
