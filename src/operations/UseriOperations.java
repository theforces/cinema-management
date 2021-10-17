package operations;

import controller.UseriController;
import model.Useri;
import operations.RezervariUseriOperations;

import java.sql.SQLException;
import java.util.Scanner;

public class UseriOperations {

        private static UseriController userController = UseriController.getInstance();

        public static void userOperations() throws SQLException {
            Scanner sc = new Scanner(System.in);
            System.out.println("Te afli in fereastra de logare utilizator");
            System.out.println("Introduceti comanda");

            while(true){
                String cmd = sc.nextLine();

                switch (cmd){
                    case "logare":
                        String username = sc.nextLine();
                        String parola = sc.nextLine();
                        boolean in = userController.loginUser(username,parola);
                        if(in){
                            System.out.println("Deschidem o fereasra pentru rezervare");
                            RezervariUseriOperations.rezervariUseriOperations(username);
                        }else{
                            System.out.println("Username-ul sau parola gresita!");
                        }
                        break;

                    case "signup":
                        System.out.println("Deschidem o fereastra pentru inregistrare utilizator");
                        String username1 = sc.nextLine();
                        String parola1 = sc.nextLine();
                        Useri user = new Useri(1,username1,parola1);
                        userController.addUser(user);
                        break;

                    case "exit": System.out.println("se inchide aplicatia");
                        System.exit(0);
                }
            }
        }
}
