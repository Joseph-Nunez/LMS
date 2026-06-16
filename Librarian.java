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

public class Librarian {
    private int workerID;
    private String workerName;
    private ArrayList<Patron> patrons;
    private Scanner scan;

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
        this.patrons = new ArrayList<>(); // Creates an array of patrons
        this.scan = new Scanner(System.in); // Allows input from the CLI
    }

    public void AddPatron() {

        // Creates a unique ID for new patrons
        int ID = ThreadLocalRandom.current().nextInt(1000000, 10000000);
        while (idExists(ID)) {
            System.out.println("ID conflict detected, regenerating...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            ID = ThreadLocalRandom.current().nextInt(1000000, 10000000);
        }
        // Gathers patron name
        System.out.print("Patron's first and last name: ");
        String name = scan.nextLine();

        // Gathers patron's home address
        System.out.print("Enter user's full address: ");
        String address = scan.nextLine();

        // Sets amount owed to zero due to being a new patron
        double owedAmount = 0;

        //Creates the new patron account
        Patron newPatron = new Patron(ID, name, address, owedAmount);
        patrons.add(newPatron);
        System.out.println("New Patron:" + name);
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
        String filePath = scan.nextLine(); // Gathers user input of file path

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

                if (idExists(id)) {
                    int dupeID = id;
                    System.out.println("WARNING: Duplicate ID " + id + " for " + name + " —> reassigning.\n\n ");
                    try {
                        // Pause execution for 5 seconds
                        Thread.sleep(5000);
                        while (idExists(id)) {
                            id = ThreadLocalRandom.current().nextInt(1000000, 10000000);
                            System.out.println("INFO: Duplicate ID " + dupeID + " for " + name + " has been changed to —> " + id + "\n\n ");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                Patron newPatron = new Patron(id, name, address, owedAmount);
                patrons.add(newPatron);
            }

            fileScanner.close();
            System.out.println("Patrons loaded successfully!");

        } catch (FileNotFoundException e) { // Error for unavailable file in path
            System.out.println("File not found. Please check the path and try again.");
        }
    }
}
