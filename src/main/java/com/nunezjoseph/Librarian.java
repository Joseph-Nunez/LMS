/**
 * Joseph Nunez
 * CEN 3024C - Software Development 1
 * June 16, 2026
 * Librarian.java
 * This class assigns available task to the
 * librarian to properly run the LMS software.
 */


package com.nunezjoseph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Librarian {
    private int workerID;
    private String workerName;
    private ArrayList<Patron> patrons; // Array of total patrons used this session.
    private ArrayList<Patron> newPatrons;
    private Scanner scan;
    private String filePath;

    // Checks for duplicate ID numbers before assigning to patron
    private boolean idExists(int id) {
        for (Patron patron : patrons) {
            if (patron.getID() == id) {
                return true;  // Duplicate found
            }
        }
        return false;  // No duplicate
    }

    public Librarian(int ID, String name) {
        this.workerID = ID; // Gathers the ID of the user
        this.workerName = name; // Gathers name of user
        this.patrons = new ArrayList<>(); // Creates an array of all patrons new and imported
        this.newPatrons = new ArrayList<>(); // Holds only patrons modified this session
        this.scan = new Scanner(System.in); // Allows input from the CLI
    }

    // Adds patrons to database
    public void AddPatron() {


        int ID = ThreadLocalRandom.current().nextInt(1000000, 10000000); // Creates a unique ID for new patrons
        while (idExists(ID)) { // Sorts through already set patrons to guarentee no duplicate IDs
            System.out.println("ID conflict detected, regenerating..."); // Duplicate warning
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) { // Error handling for sleep method
                Thread.currentThread().interrupt();
            }
            ID = ThreadLocalRandom.current().nextInt(1000000, 10000000); // Assigns new ID if the original is taken then loops again to verify it is unique
        }
        // Gathers patron name
        System.out.print("Patron's first and last name: ");
        String name = scan.nextLine();

        // Gathers patron's home address
        System.out.print("Enter user's full address: ");
        String address = scan.nextLine();

        // Sets amount owed to zero due to being a new patron
        double owedAmount = 0;


        Patron newPatron = new Patron(ID, name, address, owedAmount); //Creates the new patron account
        patrons.add(newPatron); // Adds new patron to array of existing Patrons
        newPatrons.add(newPatron); // Holds current patron in a list to append to the file
        System.out.println("New Patron:" + name + "\n\n");
    }

    // Outputs all user's in patrons array
    public void ListPatrons(){
        for (Patron patron : patrons){
            System.out.println("ID: " + patron.getID() + " | Name: " + patron.getName() + " | Address: " + patron.getAddress() + " | Amount owed: " + patron.getOwedAmount());
        }
    }

    // Allows user to upload a file and add patrons
    public void LoadPatrons() {
        System.out.print("Enter the path to the patron data file: ");
        this.filePath = scan.nextLine(); // Gathers user input of file path and stores for saving purposes

        try {
            File file = new File(filePath);
            Scanner fileScanner = new Scanner(file); // Reads file

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split("-"); // Seperates the file lines by dashes

                int id = Integer.parseInt(data[0].trim()); //Turns the ID portion of string to integer and seperates the 0-index data from string
                String name = data[1].trim(); // Pulls the 1 index data from the string
                String address = data[2].trim(); // Pulls the 2-index data from string
                double owedAmount = Double.parseDouble(data[3].trim()); // Pulls the 3-index data from string

                if (idExists(id)) { // Checks if any IDs in the txt file are duplicate, there is a change in ID
                    int dupeID = id;
                    System.out.println("WARNING: Duplicate ID " + id + " for " + name + " —> reassigning.\n\n "); // Warning of duplicate ID
                    try {
                        // Pause execution for 5 seconds
                        Thread.sleep(5000);
                        while (idExists(id)) {
                            id = ThreadLocalRandom.current().nextInt(1000000, 10000000); // Assigns new ID
                            System.out.println("INFO: Duplicate ID " + dupeID + " for " + name + " has been changed to —> " + id + "\n\n ");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                Patron newPatron = new Patron(id, name, address, owedAmount); // Creates patron account
                patrons.add(newPatron); // Adds patron account to array of other patrons
            }

            fileScanner.close();
            System.out.println("Patrons loaded successfully!\n\n");

        } catch (FileNotFoundException e) { // Error for unavailable file in path
            System.out.println("File not found. Please check the path and try again.\n\n");
        }
    }

    // Removes desired patron
    public void RemovePatron(){
        System.out.println("Enter the 7-digit unique ID of user to be deleted: ");
        int ID = scan.nextInt();
        scan.nextLine();

        Patron removePatron = null; // Creates a temporary patron variable that will hold the desired patron account
        for (Patron patron : patrons){
            if (patron.getID() == ID){ // Finds the patron via ID and sets to a variable to single out account
                removePatron = patron;
                break;
            }
        }

        if (removePatron != null){ // Only runs if the patron ID is found
            patrons.remove(removePatron); // Removes desired patron from array
            if (newPatrons.contains(removePatron)){
                newPatrons.remove(removePatron);
            }
            System.out.println("Patron " + removePatron.getName() + " has been removed.\n\n"); // Sends notification to user about modification
        }else{
            System.out.println("No patron found.\n\n");
        }

    }

    // Checks information on a single patron
    public void CheckPatronInfo(){
        System.out.println("Enter the 7-digit unique ID to view their account: ");
        int ID = scan.nextInt(); // Gathers ID for the patron
        scan.nextLine();

        Patron desiredPatron = null;
        for (Patron patron : patrons){
            if (patron.getID() == ID){
                desiredPatron = patron;
                break;
            }
        }
        if (desiredPatron != null){ // Prints out the info of patron if the ID is found in Array
            System.out.println("ID: " + desiredPatron.getID() + " | Name: " + desiredPatron.getName() + " | Address: " + desiredPatron.getAddress() + " | Amount owed: " + desiredPatron.getOwedAmount() + "\n");
        }else{
            System.out.println("No patron found.\n\n");
        }
    }

    // Saves newly added patrons to a txt file
    public void SavePatrons() {

        if (filePath == null){ // Checks if user entered a patron list in the beginning and if not, adds it now to save new info

            System.out.println("There is no set file. Please load a file now: ");
            this.filePath = scan.nextLine();
        }
        try {
            FileWriter writer = new FileWriter(filePath, true); // Grabs the file that was entered

                for (Patron patron : newPatrons) {
                    // Formats the array to the same format as txt files
                    writer.write(patron.getID() + "-" +
                            patron.getName() + "-" +
                            patron.getAddress() + "-" +
                            patron.getOwedAmount() + "\n");
                }

                writer.close();
                System.out.println("Patrons saved to file successfully!");

        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
