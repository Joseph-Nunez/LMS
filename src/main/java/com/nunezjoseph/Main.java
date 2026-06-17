package com.nunezjoseph;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Librarian librarian = new Librarian(9232, "Janet Smith"); // Allows access to the librarian class which allows access to the Patron class
        Scanner scan = new Scanner(System.in);

        int txtConfirmation = 1; // Valued at 1 to process a change in truth value
        while(txtConfirmation == 1) { // Scanning to see if user would like to upload a txt file with patrons
            System.out.println("Would you like to enter patrons via txt file? y or n\n");
            String answer = scan.nextLine();
            switch(answer){
                case "y" -> {
                    librarian.LoadPatrons(); // Function to utilize txt file filled with patrons
                    librarian.ListPatrons();
                    txtConfirmation = 0;
                }

                case "n" -> txtConfirmation = 0;
                default -> System.out.println("Please enter 'y' or 'n'");
            }
        }

        int truth = 1;
        while (truth == 1) { // Loops through the menu until the user inputs 0 to exit
            System.out.println("Please select the number for the action you desire: \n1: Add a patron\n2: Remove a patron\n3: Check a patron's information\n4: List all patron's\n5: Load Patrons from a txt file\n\n0: Exit\n\n");
            var choice = scan.nextLine();
            switch(choice){
                case "1" -> {
                    librarian.AddPatron();
                    librarian.ListPatrons();
                }
                case "2" -> {
                    librarian.RemovePatron();
                    librarian.ListPatrons();
                }
                case "3" -> librarian.CheckPatronInfo();

                case "4" -> {
                    librarian.ListPatrons();
                }
                case "5" -> {
                    librarian.LoadPatrons();
                    librarian.ListPatrons();
                }
                case "0" -> {
                    librarian.ListPatrons();
                    librarian.SavePatrons();
                    truth = 0;
                }
                default -> System.out.println("\nPlease enter a valid selection.\n\n"); // Catches any input that is not an option on the list
            }
        }

    }
}
