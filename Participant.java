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
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public Participant() {
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			connection=DriverManager.getConnection("jdbc:mysql://localhost/ForQuiz","root","Aspire@12345");
		} 
    	catch (ClassNotFoundException | SQLException exception) {
			System.out.println("Unable to connect");
		}
    }
	
    Scanner scanner=new Scanner(System.in);
    public void register() throws SQLException{
    	System.out.println("--------------Participant Registration--------------");
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
			System.err.println("Not registered");
			System.out.println();
		}
        System.out.println("Registered Successfully Now Login!");
        System.out.println();
    }
    
    public boolean login() {
    	try {
    		System.out.println("--------------Participant Login--------------");
            System.out.print("Enter your username: ");
            String userName = scanner.nextLine();
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            participantName=userName;
            String query = "SELECT * FROM Registration WHERE Participant_Name = '" + userName + "' AND Participant_password = '" + password+"';";
            statement=connection.createStatement();
            resultSet = statement.executeQuery(query);
            return resultSet.next(); // If there is a matching record, login is successful
        } 
    	catch (SQLException exception) {
            System.err.println("Invalid user name or password");
            return false;
        }
    }
    
    public void menuParticipant() throws SQLException {
    	if (login()) {
    		int choice;
    		do {
    			System.out.println("----- WELCOME PARTICIPANT 🪂 -----");
    			System.out.println("1. Start Quiz 👍");
    			System.out.println("2. Logout 👋");
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
                        // Display quiz questions (similar to the previous examples)
                    	String question = resultSet.getString(1);
                        String option1 = resultSet.getString(2);
                        String option2 = resultSet.getString(3);
                        String option3 = resultSet.getString(4);
                        String option4 = resultSet.getString(5);
                        int correctOption = resultSet.getInt(6);
                        // Display the question and options
                        System.out.println("Question: " + question);
                        System.out.println("Options: ");
                        System.out.println("1. " + option1);
                        System.out.println("2. " + option2);
                        System.out.println("3. " + option3);
                        System.out.println("4. " + option4);
                        // Get the participant's answer
                        System.out.print("Enter your answer (1, 2, 3, or 4): ");
                        int participantAnswer = scanner.nextInt();
                        // Check if the answer is correct
                        if (participantAnswer==correctOption) {
                            System.out.println("Correct!");
                            score++;
                        }
                        else {
                            System.out.println("Incorrect. The correct answer is: " + correctOption);
                        }
                        totalQuestion++;
                        System.out.println();
                    }
                    // Display the final score
                    System.out.println("Your final score: " + score +"/"+totalQuestion);
                    System.out.println("--------------------------------------");
                    String participantquery="INSERT INTO Score(Participant_Name, Total_Score) VALUES('"+participantName+"',"+score+");";
                    try {
            			preparedStatement=connection.prepareStatement(participantquery);
            			preparedStatement.executeUpdate();
            		} 
                    catch (SQLException exception) {
            			System.err.println("Sorry! Score not saved");
            		}
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
            System.err.println("Invalid username or password!");
            menuParticipant();
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