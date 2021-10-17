package main;

import operations.PersonalCinematografOperations;
import operations.UseriOperations;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Esti utilizator sau personal cinema? : ");
            String cmd = scanner.nextLine();
            switch (cmd) {
                case "utilizator":  System.out.println("S-a selectat utilizator");
                                    UseriOperations.userOperations();
                                    break;
                case "personal cinema" : System.out.println("S-a selectat personal cinema");
                                         PersonalCinematografOperations.personalCinematografOperations();
                                         break;
                case "exit": System.out.println("se inchide aplicatia");
                    System.exit(0);
            }

        }

    }
}
