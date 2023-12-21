package com.aspiresys;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//Child class inherits parent class
public class Admin implements UserInterface{
	//Used access specifier to declare attributes
    private String adminName;
    private String adminPassword;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public Admin() {
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost/ForQuiz","root","Aspire@12345");
		} 
    	catch (ClassNotFoundException | SQLException exception) {
			System.out.println("Unable to connect");
			System.out.println();
		}		
    }
    
    Scanner scanner = new Scanner(System.in);
    
    //method to add questions to quiz
    public void addQuestion() {    	
    	try {
			String query="insert into Questions values(?,?,?,?,?,?);";
			preparedStatement=connection.prepareStatement(query);
			System.out.print("How many questions you want to add? ");
			int questionCount=scanner.nextInt();
			scanner.nextLine();
			if(questionCount>0) {
				for(int questionIndex=0;questionIndex<questionCount;questionIndex++) {
					System.out.print("Question "+(questionIndex+1)+": ");
					String question=scanner.nextLine();
					preparedStatement.setString(1, question);
					System.out.print("How many options you want to add? ");
					int optionCount=scanner.nextInt();
					scanner.nextLine();
					for(int optionIndex=0;optionIndex<optionCount;optionIndex++) {
						System.out.print("Option "+(optionIndex+1)+": ");
						String option=scanner.nextLine();
						
						preparedStatement.setString((optionIndex+2), option);
					}
					System.out.print("Correct Answer: ");
					int correctAnswer=scanner.nextInt();
					scanner.nextLine();
					preparedStatement.setInt(6, correctAnswer);
					preparedStatement.executeUpdate();
					System.out.println((questionIndex+1)+" question added!");
					System.out.println();
				}
			}
			else {
				System.err.println("No question is added");
			}
		}
    	catch (SQLException exception) {
			System.err.println("Wrong input! Give correct input");
			System.out.println();
		}
    }
    
    public void viewQuestion() {
    	try {
    		statement=connection.createStatement();
    		resultSet=statement.executeQuery("select Question,Option_1,Option_2,Option_3,Option_4,Correct_option from Questions;");
    		int count=0;
    		System.out.println("\t*****AVAILABLE QUESTIONS *****");
    		while(resultSet.next()) {
    			count=count+1;
    			System.out.println("Question-"+count+". "+resultSet.getString(1)+"\nOption-1."+
    										  resultSet.getString(2)+"\nOption-2."+
    										  resultSet.getString(3)+"\nOption-3."+
    										  resultSet.getString(4)+"\nOption-4."+
    										  resultSet.getString(5)+"\nCorrect Option is: "+
    										  resultSet.getInt(6)+"\n");
    		}
    		if(count>0) {
    			System.out.println(count+" question available");
    			System.out.println();
    		}
    		else {
    			System.err.println("No question available! Please add some questions before you view! ");
    			System.out.println();
    		}
		} 
    	catch (SQLException exception) {
    		System.out.println("Exception here");
    		System.out.println();
		}
    }
    
    public void deleteQuestion() throws SQLException {
    	statement=connection.createStatement();
    	statement.execute("truncate table Questions;");
		System.out.println("All questions are deleted");
		System.out.println();
    }
    
    //method for administrator to login first to add quiz questions
    public void login() throws SQLException {
    	System.out.println("--------------Admin Login--------------");
        String filePath = "C:\\Java Programs\\AdminDetails.txt"; 
        String[] details = readFile(filePath);
        
        if (details != null && details.length == 2) {
            String storedName = details[0];
            String storedPassword = details[1];
            System.out.print("Enter admin username: ");
            adminName = scanner.nextLine();            
            System.out.print("Enter admin password: ");
            adminPassword = scanner.nextLine();
            int choice;
            if(adminName.equals(storedName) && adminPassword.equals(storedPassword)) {
            	do{
            		System.out.println("<-- Hi ✋ "+adminName+"! What you want to do -->");
            		System.out.println("1. Add questions ➕");
            		System.out.println("2. View questions 👁️");
            		System.out.println("3. Delete questions 💣");
            		System.out.println("4. Logout 👋");
            		System.out.print("Enter choice: ");
            		choice = scanner.nextInt();
                    scanner.nextLine();
                    switch(choice) {
                    case 1:
                    	addQuestion();
                    	break;
                    case 2:
                    	viewQuestion();
                    	break;
                    case 3:
                    	deleteQuestion();
                    	break;
                    case 4:
                        System.out.println("------Logout Successful! Thank you "+adminName+" 👋 ------");
                        System.out.println();
                    	break;
                    default:
                    	System.err.println("Give correct menu choice");
                        System.out.println();
                    	break;
                    }
            	}
            	while(choice!=4);
            }
            else {
            	System.err.println("Incorrect name or password!");
                System.out.println();
                login();
            }
        } 
        else {
            System.err.println("Error reading credentials from file.");
        }
    }
    
    private String[] readFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        	String[] details = new String[2];
            details[0] = reader.readLine();
			details[1]= reader.readLine();
            return details;
        } catch (IOException e) {
            System.err.println("Error reading credentials from file: " + e.getMessage());
            return null;
        }
    }
    
    public void closeConnection() {
    	try {
    		if(connection!=null) {
    			connection.close();
    		}
    	}
    	catch(SQLException exception){
    		System.err.println("Connection is not closed");
    	}
    }
}