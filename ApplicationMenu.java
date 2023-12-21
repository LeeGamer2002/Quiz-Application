package com.aspiresys;

import java.sql.SQLException;
import java.util.Scanner;

public class ApplicationMenu{
	Scanner scanner=new Scanner(System.in);
	public void openApplication() throws SQLException{
		Admin admin=new Admin();
		Participant participant=new Participant();
		boolean check=true;
		while(check==true){
			System.out.println("********** WELCOME **********");
			System.out.println("Who are you?");
			System.out.println("1. Admin 😃");
			System.out.println("2. Participant 😃");
			System.out.println("3. Logout 👋");
			System.out.println("Enter your choice: ");
			System.out.println();
			int choice=scanner.nextInt();
			switch(choice){
			case 1:
				admin.login();
				check=false;
				break;
			case 2:
				int participantChoice;
				do {
					System.out.println("Welcome Participant 😁 Register before starting quiz");
					System.out.println("1. Participant Register 👈");
					System.out.println("2. Participant Login 👈");
					System.out.println("Enter your choice: ");
					participantChoice=scanner.nextInt();
					switch(participantChoice) {
					case 1:
						participant.register();
						break;
					case 2:
						participant.menuParticipant();
						break;
					default:
						System.err.println("Give correct choice");
						break;
					}
				}
				
				while(participantChoice!=2);
				check=false;
				break;
			case 3:
				System.out.println("Thank you!");
				check=false;
				admin.closeConnection();
				participant.closeConnection();
				break;
			default:
				System.err.println("Give correct choice");
				break;
			}
		}
	}
}