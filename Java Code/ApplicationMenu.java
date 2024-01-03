package com.aspiresys;

import java.util.Scanner;

public class ApplicationMenu{
	Scanner scanner=new Scanner(System.in);
	Admin admin=new Admin();
	Participant participant=new Participant();
	
	//method for starting menu
	public void openApplication(){
		boolean check=true;
		while(check==true){
			System.out.println("----Yo----");
			System.out.println("Who are you?");
			System.out.println("1. Admin");
			System.out.println("2. Participant");
			System.out.println("3. Logout");
			System.out.print("Enter your choice: ");
			int choice=scanner.nextInt();
			switch(choice){
			case 1:
				admin.login();
				check=false;
				break;
			case 2:
				int participantChoice;
				do {
					System.out.println("Welcome Participant Register before starting quiz");
					System.out.println("1. Participant Register");
					System.out.println("2. Participant Login");
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
						System.out.println("Give correct choice");
						break;
					}
				}
				while(participantChoice!=2);
				check=false;
				break;
			case 3:
				System.out.println("Thank you! Have a Nice Day");
				check=false;
				admin.closeConnection();
				break;
			default:
				System.out.println("Give correct choice");
				break;
			}
		}
	}
}