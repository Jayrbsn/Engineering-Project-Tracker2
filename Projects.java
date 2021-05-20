package sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.text.ParseException;

//Resource: https://www.w3resource.com/java-tutorial/string/string_equalsignorecase.php
//Resource: https://github.com/DanielleBussy/Capstone-Project-6/tree/master/PoisedDatabaseProject/src
//Resource: https://stackoverflow.com/questions/7552660/java-convert-float-to-string-and-string-to-float
public class Projects {
	
	//Create method to view incomplete projects
	//This works exactly the same as the method above only it reads from a different file
	public void viewActiveProjects(Statement statement) throws SQLException {
		
		System.out.println("\nPlease view all incomplete projects below: \n");
		
		ResultSet activeresults = statement.executeQuery("SELECT * FROM projects WHERE complete = 'No'");
		
		// All incomplete projects are displayed using a table iterator.
		while (activeresults.next()) {
			System.out.println( 
					 "Project Number: " + activeresults.getString("number")
		                + "\nProject Name: " + activeresults.getString("name") 
		                + "\nBuilding Type: " + activeresults.getString("buildType")        
		                + "\nPhysical Address: " + activeresults.getString("buildAddress") 
		                + "\nERF Number: " + activeresults.getString("erf") 
		                + "\nTotal Fee: R" + activeresults.getString("totalFee") 
		                + "\nAmount Paid: R" + activeresults.getString("totalPaid")  
		                + "\nDeadline: " + activeresults.getString("deadline") 
		                + "\nClient: " + activeresults.getString("client") 
		                + "\nComplete: " + activeresults.getString("complete") 
		                + "\n"
					);
		}
	}
	
	//Create method to add a project to the database
	//Create a variety of scanners and variables to take user input for the details of the new project
	//Use print statements to prompt user input
	public void createProject(Statement statement) throws SQLException {
			
		Scanner numberscan = new Scanner(System.in);
		System.out.println("\nPlease add a new project number: "); 
		String number = numberscan.nextLine();
			
		Scanner namescan = new Scanner(System.in);
		System.out.println("\nPlease add a new project name: ");
		String name = namescan.nextLine(); 
			
		Scanner typescan = new Scanner(System.in);
		System.out.println("\nPlease add a building type: ");
		String buildType = typescan.nextLine();
			
		Scanner buildscan = new Scanner(System.in);
		System.out.println("\nPlease add a address for the project: ");
		String buildAddress = buildscan.nextLine();
			
		Scanner erfscan = new Scanner(System.in);
		System.out.println("\nPlease add an ERF number: ");
		String erf = erfscan.nextLine();
			
		Scanner feescan = new Scanner(System.in);
		System.out.println("\nPlease add a total fee for the project R: ");
		String totalFee = feescan.nextLine();
			
		Scanner totalscan = new Scanner(System.in);
		System.out.println("\nPlease add the current amount paid for the project R: ");
		String totalPaid = totalscan.nextLine();
			
		Scanner deadlinescan = new Scanner(System.in);
		System.out.println("Please add a deadline for the project (e.g. 2 August 2021): ");
		String deadline = deadlinescan.nextLine();
			
		Scanner clientscan = new Scanner(System.in);
		System.out.println("Please add a client for the project: ");
		String client = clientscan.nextLine();
			
		String complete = "No";
			
		//Use INSERT INTO sql instruction to add row to the projects table base on user input
		statement.executeUpdate("INSERT INTO projects VALUES (" + number + ", " + "'" + name + "'" + ", " + "'" 
	        + buildType + "'" + ", " + "'" + buildAddress + "'" + ", " + "'" + erf + "'" + ", " + totalFee + ", " + totalPaid + ", " + 
	        "'" + deadline + "'" + ", " + "'" + client + "'" + ", " + "'" + complete + "'" + ");"
	        );
			
		//User is notified of their successful addition to the database
		System.out.println("\nYour new project was successfully added\n"); 
				
	}
	
	//Create method to allow user to change the due date and/or total amount paid
	public void editProject(Statement statement) throws SQLException {
		
		// The user is prompted to enter a project number to edit
		Scanner projectscan = new Scanner(System.in);
		System.out.println("Please enter the number of the project you wish to update: \n");
		String project = projectscan.nextLine();
		
		//Edit options are displayed for user to choose between
		Scanner choicescan = new Scanner(System.in);
		System.out.println("Would you like to:" + 
				"\na) Edit the project due date or" +
				"\nb) Edit the total amount paid of the fee to date?" + 
				"\nEnter 'a' or 'b'");
		String menuChoice = choicescan.nextLine();
		
		//If user enters 'a' then they are prompted to enter the new due date
		if (menuChoice.equals("a") || menuChoice.equals("A")) {
			
			//Scanner object takes in new due date to be written to the database
			Scanner newDateScan = new Scanner(System.in);
			System.out.println("Please enter a new project deadline (e.g. 4 December 2021: ");
			String newDate = newDateScan.nextLine(); 
			
			//Write changes to database with UPDATE instruction
			statement.executeUpdate(
                    "UPDATE projects SET Deadline = '" + newDate + "'" + " WHERE number = " + project
                );
			
			//Notify user that change was successful
			System.out.println("\nYour project info has been successfully updated\n");
			
		} 
		
		//If user enters 'b' they are asked to enter the new value of the total amount paid by client
		else if (menuChoice.equals("b") || menuChoice.equals("B")) {
			
			//Scanner object takes in new total amount paid
			Scanner newTotalScan = new Scanner(System.in);
			System.out.println("Please enter a new total amount paid R: ");
			String newTotal = newTotalScan.nextLine();
			
			//Write update to database using UPDATE instruction
			statement.executeUpdate(
                    "UPDATE projects SET totalPaid = " + newTotal + " WHERE number = " + project
                );
			
			//Notify user that the change was successful
			System.out.println("\nYour project info has been successfully updated\n");
			
		}
	}
	
	//Create method to finalize a project
	public void finaliseProject(Statement statement) throws SQLException {
		
		//The user is prompted to enter the project number of the project they wish to finalize
		Scanner projectscan = new Scanner(System.in);
		System.out.println("Please enter the number of the project that you wish to finalise: ");
		String project = projectscan.nextLine();
		
		//Initialize totalFee and totalPaid variables using SELECT instruction to find info from the table
		ResultSet finalResults = statement.executeQuery("SELECT totalFee, totalPaid, client, name FROM projects WHERE number = " + project);
		String totalFee;
		String totalPaid;
		String client;
		String name;
		
		
		//Iterate through the row to find the values we are looking for
		while (finalResults.next()) {
			totalFee = finalResults.getString("totalFee"); 
			totalPaid = finalResults.getString("totalPaid");
			client = finalResults.getString("client");
			name = finalResults.getString("name");
			
			//Convert string datatype into floats for subtraction later on
			float fee = Float.parseFloat(totalFee);
			float paid = Float.parseFloat(totalPaid);
		
			//If the project has been paid in full no invoice needs to be generated as per instructions
			if (fee == paid) {
				System.out.println("This project has been paid in full");
				
				//Set complete variable table to 'yes' using UPDATE instruction
				statement.executeUpdate(
	            		 "UPDATE projects SET complete = 'Yes' WHERE number = " + project
		                );
				
				//Notify user that finalisation was successful
				System.out.println("Your project has been successfully finalised");
				
			
			//If there is still an amount outstanding on the project, an invoice will be generated
			//Display summary of amounts due to user
			} else {
				System.out.println("\nInvoice\n");
				System.out.println("Invoice To: "+client);
				System.out.println("Project in Question: "+name);
				System.out.println("Amount Outstanding: R" + (fee - paid));
				System.out.println("\nKindly settle your account as soon as possible\n");
				
				// The project is then marked as finalised by writing 'Yes' to the finalise column in the table.
				statement.executeUpdate(
	            		 "UPDATE projects SET complete = 'Yes' WHERE number = " + project
		                );
				
				// A successful message is displayed and the user is able to view the updated project list.
				System.out.println("Your project has been successfully finalised");
			}break;
		}
	}
	
	//Create method to veiw old projects from archives
	//This is the exact same code as the viewActiveProjects code, except find projects where complete = 'yes'
	public void viewCompleteProjects(Statement statement) throws SQLException {
			
			System.out.println("\nPlease view all archived projects below: \n");
			
			//This is the only necessary change for this method
			ResultSet activeresults = statement.executeQuery("SELECT * FROM projects WHERE complete = 'Yes'");
			
			// All previously complete projects are displayed using a table iterator.
			while (activeresults.next()) {
				System.out.println( 
						 "Project Number: " + activeresults.getString("number")
			                + "\nProject Name: " + activeresults.getString("name") 
			                + "\nBuilding Type: " + activeresults.getString("buildType")        
			                + "\nPhysical Address: " + activeresults.getString("buildAddress") 
			                + "\nERF Number: " + activeresults.getString("erf") 
			                + "\nTotal Fee: R" + activeresults.getString("totalFee") 
			                + "\nAmount Paid: R" + activeresults.getString("totalPaid")  
			                + "\nDeadline: " + activeresults.getString("deadline") 
			                + "\nClient: " + activeresults.getString("client") 
			                + "\nComplete: " + activeresults.getString("complete") 
			                + "\n"
						);
		}
	}
	
	//Create method to view overdue projects
	public void checkDeadline(Statement statement) throws SQLException, ParseException {
		
		//Initialize boolean value that will be used later to decide what to display to user
		//Initialize an array called index into which we will insert the due date after splitting it
		//Initialize array from 1 - 12 to represent months of the year
		//Create a string array for the same thing
		//Initialize monthNumber variable that will be determined by later for loop
		boolean overdue = false;
		String[] index;
		int[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		String[] monthsofYear = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		int monthNumber = 0;
		
		//Use SELECT instruction to find incomplete projects
		ResultSet results = statement.executeQuery("SELECT Deadline FROM projects WHERE complete = 'No'");
		
		
		// Iterating through the deadline dates in the incomplete projects to check if they are overdue
		while (results.next()) {
			
			//Now to break down the deadlines to something more useful:
			//The deadline date in the project is stored in the string variable deadline
			//The date is then split and added to the index array we created earlier
			//The value at index[0] will be the day which is converted into an int using parseInt
			//The value at index [1] will be the month, first called monthInfo
			//The a substring is created that takes the first few characters of the monthInfo string
			//The value at index [2] will be the year, which is then converted into an int using parseInt
			String deadline = results.getString("deadline");
			index = deadline.split(" ");
			int day = Integer.parseInt(index[0]);
			String monthInfo = index[1];
			String month = (monthInfo.substring(0,3));
			int year = Integer.parseInt(index[2]);
			
			//Create for loop that goes through the monthsofYear array until it finds a match for the month variable derived at line 336
			//This match will be found at an index that is an exact match of the index of the months array
			//monthNumber is then assigned the same value as the value of the index at the two arrays
			for (int i = 0; i < monthsofYear.length ; i++) {	
				if (month.equalsIgnoreCase(monthsofYear[i])) {
					monthNumber = months[i];
					
				}
			}
			
			//Get todays date
			String today = "" + java.time.LocalDate.now();
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			//Create objects that store todays date and the deadline date of the specified project from the database
			Date current = date.parse(today);
			Date due = date.parse(year + "-" + monthNumber + "-" + day);
			
			//If todays date is past the deadline the project is displayed to the user
			if (current.compareTo(due) > 0) {
				overdue = true;
				
				//Print statement for display to user
				//Retrieve project info from the database for display based on the deadline using SELECT instruction
				System.out.println("\nOverdue Projects: \n");
				ResultSet overdueResults = statement.executeQuery("SELECT * from projects WHERE deadline = '" + deadline + "'");
				
				//Iterate through project info and display
				while (overdueResults.next()) {
		    		System.out.println(
		    				 "Project Number: " + overdueResults.getInt("number")
		    	                + "\nProject Name: " + overdueResults.getString("name") 
		    	                + "\nBuilding Type: " + overdueResults.getString("buildType")        
		    	                + "\nPhysical Address: " + overdueResults.getString("buildAddress") 
		    	                + "\nERF Number: " + overdueResults.getString("erf") 
		    	                + "\nTotal Fee: R" + overdueResults.getFloat("totalFee") 
		    	                + "\nAmount Paid: " + overdueResults.getFloat("totalPaid")  
		    	                + "\nDeadline: " + overdueResults.getString("deadline") 
		    	                + "\nClient: " + overdueResults.getString("client")  
		    	                + "\n"
		            );
				} break;
					
			//If todays date is before the due date, overdue boolean remains false
			} else {
				overdue = false;
			} 
		}
	}
}
		
	
 

