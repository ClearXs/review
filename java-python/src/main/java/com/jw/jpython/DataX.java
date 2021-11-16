package com.jw.jpython;

import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataX {

    public static void main(String[] args) throws IOException, InterruptedException {
//        String[] command = new String[] {"python", "D:\\tools\\datax\\datax\\bin\\datax.py"};
//        String[] command = new String[] {"python", "D:\\python\\learn\\elementary\\HelloWorld.py"};
        Process process = Runtime.getRuntime().exec("cmd /c python datax.py D:\\tools\\datax\\datax\\bin\\job\\mysql-read-console.json", null, new File("D:\\tools\\datax\\datax\\bin"));
        BufferedReader in = new BufferedReader(new InputStreamReader(
                process.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
        in.close();
        process.waitFor();
    }
}
