package com.nunezjoseph;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Librarian librarian = new Librarian(9232, "Janet Smith");
        Scanner scan = new Scanner(System.in);

        int txtConfirmation = 1;
        while(txtConfirmation == 1) {
            System.out.println("Would you like to enter patrons via txt file? y or n\n");
            String answer = scan.nextLine();
            switch(answer){
                case "y" -> {
                    librarian.LoadPatrons();
                    librarian.ListPatrons();
                    txtConfirmation = 0;
                }

                case "n" -> txtConfirmation = 0;
                default -> System.out.println("Please enter 'y' or 'n'");
            }
        }

        int truth = 1;
        while (truth == 1) {
            System.out.println("Please selct the number for the action you desire: \n1: Add a patron\n2: Remove a patron\n3: Check a patron's information\n4: List all patron's\n\n0: Exit\n\n");
            int choice = scan.nextInt();
            switch(choice){
                case 1 -> librarian.AddPatron();
               // case 2 -> librarian.RemovePatron();
               // case 3 -> librarian.CheckPatron();
                case 4 -> librarian.ListPatrons();
                case 0 -> truth = 0;
            }


//        librarian.AddPatron();
//        librarian.AddPatron();
//        librarian.ListPatrons();
        }

    }
}
