package com.aspiresys;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Participant{
	private String participantName;
    private String participantPassword;
    private String url;
    private String databaseUser;
    private String databasePassword;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    Scanner scanner=new Scanner(System.in);
    MyFile myFile=new MyFile();
    
    public Participant() {
    	try {
    		String[] details = myFile.readQuery();
    		url=details[0];
    		databaseUser=details[1];
    		databasePassword=details[2];
			connection=DriverManager.getConnection(url,databaseUser,databasePassword);
		} 
    	catch (SQLException exception) {
			System.out.println("Unable to connect");
		}
    }
	
    //method to register for participant
    public void register() {
    	System.out.println("====================================================");
    	System.out.println("=             Participant Registration             ="); 
    	System.out.println("====================================================");
        System.out.print("Enter your username: ");
        participantName = scanner.nextLine();
        System.out.print("Enter your password(Remember it): ");
        participantPassword = scanner.nextLine();
        String query="INSERT INTO Registration(Participant_Name, Participant_password) VALUES('"+participantName+"','"+participantPassword+"');";
        try {
			preparedStatement=connection.prepareStatement(query);
			preparedStatement.executeUpdate();
		} 
        catch (SQLException exception) {
			System.out.println("Not registered");
			System.out.println();
		}
        System.out.println("Registered Successfully Now Login!");
        System.out.println();
    }
    
    //method to login after registration
    public boolean login() {
    	try {
    		System.out.println("=============================================");
        	System.out.println("=             Participant Login             ="); 
        	System.out.println("=============================================");
            System.out.print("Enter your username: ");
            String userName = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            participantName=userName;
            String query = "SELECT * FROM Registration WHERE Participant_Name = '" + userName + "' AND Participant_password = '" + password+"';";
            statement=connection.createStatement();
            resultSet = statement.executeQuery(query);
            return resultSet.next();
        } 
    	catch (SQLException exception) {
            System.out.println("Invalid user name or password");
            return false;
        }
    }
    
    //method for participant to take their quiz and view their score
    public void menuParticipant() {
    	try {
    		if (login()) {
        		int choice;
        		do {
        			System.out.println("<-- Hi "+participantName+"! What you want to do -->");
        			System.out.println("1. Start Quiz");
        			System.out.println("2. Logout");
        			System.out.print("Enter choice: ");
        			choice = scanner.nextInt();
        			scanner.nextLine();
        			int totalQuestion=0;
        			switch(choice) {
        			case 1:
        				int score=0;
                        String query = "SELECT * FROM Questions";
                        preparedStatement = connection.prepareStatement(query);
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()) {
                        	String question = resultSet.getString(1);
                            String option1 = resultSet.getString(2);
                            String option2 = resultSet.getString(3);
                            String option3 = resultSet.getString(4);
                            String option4 = resultSet.getString(5);
                            int correctOption = resultSet.getInt(6);
                            System.out.println("Question: " + question);
                            System.out.println("Options: ");
                            System.out.println("1. " + option1);
                            System.out.println("2. " + option2);
                            System.out.println("3. " + option3);
                            System.out.println("4. " + option4);
                            System.out.print("Enter your answer (1, 2, 3, or 4): ");
                            int participantAnswer = scanner.nextInt();
                            if (participantAnswer==correctOption) {
                                System.out.println("Correct Answer! Keep it up");
                                score++;
                            }
                            else {
                                System.out.println("Incorrect. The correct answer is: " + correctOption);
                            }
                            totalQuestion++;
                            System.out.println();
                        }
                        System.out.println("Your final score: " + score +"/"+totalQuestion);
                        System.out.println("--------------------------------------");
                        String participantquery="INSERT INTO Score(Participant_Name, Total_Score) VALUES('"+participantName+"',"+score+");";
               			preparedStatement=connection.prepareStatement(participantquery);
               			preparedStatement.executeUpdate(); 
                        System.out.println("Your Score is added");
                        System.out.println();
                    	break;
        			case 2:
        				System.out.println("Thank you for participating");
        				break;
        			}
        		}
        		while (choice != 2);
            } 
        	else {
                System.out.println("Invalid username or password!");
                menuParticipant();
            }
    	}
    	catch(SQLException exception) {
    		System.out.println("Some SQL exceptions happened");
    	}
    }
}