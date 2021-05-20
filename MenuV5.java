package sql;

//Make required imports
import java.util.Scanner;
import java.io.IOException;
import java.sql.*;
import java.util.Date; 
import java.text.ParseException; 
import java.text.SimpleDateFormat;

public class MenuV5 {
	
	public static void main (String args[]) throws IOException, ParseException {
		
		//Options at main menu displayed to user
		while (true) {
			System.out.println("\nPlease choose an action from the menu below: "
					+ "\na) View Active Projects"
					+ "\nb) Add Project"
					+ "\nc) Edit Project"
					+ "\nd) Finalize a Project"
					+ "\ne) View Complete Projects"
					+ "\nf) View Overdue Projects"
					+ "\ng) Exit program");
		
		
			//Create scanner object to take in user menu choice
			Scanner input = new Scanner(System.in);
			String userChoice = input.nextLine();
		
			//If user enters g the program ends
			if (userChoice.equals("g") || userChoice.equals("G")){
				System.out.println("Bye");
				break;
			}
			
			//Try catch block for sql exceptions
			try {
				
				//Connect to database
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false", "Jay", "test");
				
				//Create a direct line to the database for running our queries
				Statement statement = connection.createStatement();
				ResultSet results;
				int rowsAffected;
				
				//If user enters a they can view all incomplete/ongoing projects
				//Calls viewActiveProjects method from Projects class
				if (userChoice.equals("a") || userChoice.equals("A")) {
					Projects object = new Projects();
					object.viewActiveProjects(statement);
				}
				
				//If user enters 'b' they can add a project
				else if (userChoice.equals("b") || userChoice.equals("B")) {
					Projects object = new Projects();
					object.createProject(statement);
				}
				
				//If user enters 'c' they can change the due date or total amount paid by client for a project 
				else if (userChoice.equals("c") || userChoice.equals("C")) {
					Projects object = new Projects();
					object.editProject(statement);
				}
				
				//If user enters 'd' they will be able to finalize a project to remove it from the active list to the completed list
				else if (userChoice.equals("d") || userChoice.equals("D")){
					Projects object = new Projects();
					object.finaliseProject(statement);
				}
				//If user enters 'e' they can view old projects
				//Calls viewCompleteProjects method from Projects class
				else if (userChoice.equals("e") || userChoice.equals("E")) {
					Projects object = new Projects();
					object.viewCompleteProjects(statement);
				}
				
				else if (userChoice.equals("f") || userChoice.equals("F")) {
					Projects object = new Projects();
					object.checkDeadline(statement);
				}
				
			}catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
	}
}
