package operations;

import controller.PersonalCinematografController;
import model.PersonalCinematograf;

import java.sql.SQLException;
import java.util.Scanner;

public class PersonalCinematografOperations {

    private static PersonalCinematografController personalCinematografController = PersonalCinematografController.getInstance();

    public static void personalCinematografOperations() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Te afli in fereastra de logare personal cinema");
        System.out.println("Introduceti comanda");

        while(true){
            String cmd = sc.nextLine();

            switch (cmd){
                case "logare":
                    String username = sc.nextLine();
                    String parola = sc.nextLine();
                    boolean in = personalCinematografController.loginUser(username,parola);
                    if(in){
                        System.out.println("Deschidem o fereasra pentru personal cinema");
                        RezervariUseriOperations.rezervariUseriOperationsPersonalCinema();
                    }else{
                        System.out.println("Username-ul sau parola gresita!");
                    }
                    break;

                case "signup":
                    System.out.println("Deschidem o fereasra pentru inregistrare personal cinema");
                    String username1 = sc.nextLine();
                    String parola1 = sc.nextLine();
                    PersonalCinematograf personalCinematograf = new PersonalCinematograf(1,username1,parola1);
                    personalCinematografController.addPersonalCinematograf(personalCinematograf);
                    break;

                case "exit": System.out.println("se inchide aplicatia");
                    System.exit(0);
            }
        }
    }
}
