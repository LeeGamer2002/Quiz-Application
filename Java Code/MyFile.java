package com.aspiresys;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//Used File Handling
public class MyFile{
	public String[] readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Java Programs\\AdminDetails.txt"))) {
        	String[] details = new String[2];
            details[0] = reader.readLine();
			details[1]= reader.readLine();
            return details;
        } catch (IOException exception) {
            System.err.println("Error reading credentials from file: " + exception.getMessage());
            return null;
        }
    }
	
	public String[] readQuery() {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Java Programs\\JDBC Files.txt"))) {
        	String[] details = new String[6];
            details[0] = reader.readLine();
			details[1]= reader.readLine();
			details[2]= reader.readLine();
			details[3]= reader.readLine();
			details[4]= reader.readLine();
			details[5]= reader.readLine();
            return details;
        } catch (IOException exception) {
            System.err.println("Error reading credentials from file: " + exception.getMessage());
            return null;
        }
    }
}