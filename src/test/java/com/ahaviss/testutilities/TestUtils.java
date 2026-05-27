package com.ahaviss.testutilities;

import com.ahaviss.utilities.ProjectUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public class TestUtils {
    public static ProjectUtils mockInput (String input) {
        ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(bais));
        return new ProjectUtils(br);
    }
}
