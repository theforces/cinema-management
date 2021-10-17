package operations;

import controller.ConnectionController;
import controller.*;
import model.ReservariUseri;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class RezervariUseriOperations {

    private static RezervariUseriController rezervariUseriController = RezervariUseriController.getInstance();

    private static UseriController useriController = UseriController.getInstance();

    public static void rezervariUseriOperations(String nume) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Schimbare pe pagina de rezervare");
        while(true) {
            String cmd = sc.nextLine();

            switch (cmd) {
                case "rezervare":
                    String url = "jdbc:mysql://localhost:3306/examenjavap";
                    Connection con = ConnectionController.getInstance().getConnection();
                    PreparedStatement findNumberOfUsers = con.prepareStatement("SELECT COUNT(*) from useri");
                    ResultSet resultSet = findNumberOfUsers.executeQuery();
                    int numberUsers=0;
                    int numberSala = 0;
                    while(resultSet.next()) {
                        numberUsers = resultSet.getInt(1);
                    }

                    System.out.println("Introduceti datele rezervarii");
                    int id = 0;
                    System.out.println("Username:");
                    String username = sc.nextLine();
                    System.out.println("Sala:");
                    int sala = Integer.parseInt(sc.nextLine());
                    ArrayList<String> filme = new ArrayList<>(5);
                    filme.add("Alb");
                    filme.add("Verde");
                    filme.add("Rosu");
                    filme.add("Albastru");
                    filme.add("Negru");
                    System.out.println("Filmele disponibile sunt: ");
                    filme.forEach(System.out::println);
                    System.out.println("Film:");
                    String film = sc.nextLine();
                    if(!filme.contains(film)){
                        System.out.println("S-a ales un film care nu este in lista");
                        break;
                    }
                    System.out.println("Data rezervare:");
                    Date dataRezervarii = Date.valueOf(sc.nextLine());

                    PreparedStatement findSala = con.prepareStatement("SELECT count(*) from rezervariuseri where sala = ? and data = ?");
                    findSala.setInt(1, sala);
                    findSala.setDate(2,dataRezervarii);

                    ResultSet resultSetSala = findSala.executeQuery();
                    while(resultSetSala.next()) {
                        numberSala = resultSetSala.getInt(1);
                    }
                    if(numberSala > 1) {
                        System.out.println("Sala plina!");
                        break;
                    }
                    LocalDate currentDate = LocalDate.now();
                    if(dataRezervarii.compareTo(java.sql.Date.valueOf(currentDate)) < 0) {
                        System.out.println("Data la care se face rezervarea nu trebuie sa fie mai mica decat data curenta");
                        break;
                    }
                    ReservariUseri reservariUseri = new ReservariUseri(
                            id, username, sala, film, dataRezervarii, numberUsers + 1);
                    rezervariUseriController.addRezervare(reservariUseri);
                    break;

                case "afisareRezervari":
                    Scanner scanner1 = new Scanner(System.in);
                    String s1 = scanner1.nextLine();
                    System.out.println("Rezervarile userului sunt:");
                    rezervariUseriController.getRezervaretByUsername(s1)
                            .forEach(System.out::println);
                    break;

                case "stergeRezervare":
                    Scanner scanner = new Scanner(System.in);
                    String s = scanner.nextLine();
                    rezervariUseriController.deleteRezervareByUsername(s);
                    System.out.println("S-a sters o rezervare");
                    break;

                case "signout":
                    UseriOperations.userOperations();
                    break;
            }
        }
    }

    public static void rezervariUseriOperationsPersonalCinema() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Schimbare pe pagina de personal");

        while(true) {
            String cmd = sc.nextLine();

            switch (cmd) {
                case "verificareCapacitate":
                    System.out.println("Sala:");
                    int sala = Integer.parseInt(sc.nextLine());
                    Connection con = ConnectionController.getInstance().getConnection();
                    PreparedStatement findSala = con.prepareStatement("SELECT count(*) from rezervariuseri where sala = ?");
                    findSala.setInt(1, sala);
                    ResultSet resultSetSala = findSala.executeQuery();
                    int numberSala = 0;
                    while(resultSetSala.next()) {
                        numberSala = resultSetSala.getInt(1);
                    }
                    int locuriLibere = 20 - numberSala;

                    PreparedStatement findUseri = con.prepareStatement("SELECT username from rezervariuseri where sala = ?");
                    findUseri.setInt(1, sala);
                    ResultSet resultSetUseri = findUseri.executeQuery();
                    ArrayList<String> useri = new ArrayList<>();
                    while(resultSetUseri.next()) {
                        useri.add(resultSetUseri.getString(1));
                    }

                    System.out.println("Userii pentru sala " + sala + " sunt: ");
                    useri.forEach(System.out::println);

                    System.out.println("Numarul locurilor libere este " + locuriLibere);
                    break;

                case "stergereRezervare":
                    Scanner scanner = new Scanner(System.in);
                    String s = scanner.nextLine();
                    rezervariUseriController.deleteRezervareByUsername(s);
                    System.out.println("S-a sters o rezervare");
                    break;

                case "stergeUtilizator":
                    Scanner scanner1 = new Scanner(System.in);
                    String user = scanner1.nextLine();
                    useriController.deleteUser(user);
                    System.out.println("S-a sters un utilizator");
                    break;

                case "verificareRezervari":
                    Scanner scanner2 = new Scanner(System.in);
                    String user1 = scanner2.nextLine();
                    System.out.println("Rezervarile userului sunt:");
                    rezervariUseriController.getRezervaretByUsername(user1)
                            .forEach(System.out::println);
                    break;

                case "signOut":
                    PersonalCinematografOperations.personalCinematografOperations();
                    break;
            }
        }
    }
}
