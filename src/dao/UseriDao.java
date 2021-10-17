package dao;

import model.Useri;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UseriDao {

    Scanner sc = new Scanner(System.in);

    private Connection con;

    private PreparedStatement addUser;
    private PreparedStatement findAllUsers;
    private PreparedStatement findUserByUsername;
    private PreparedStatement deleteUser;

    public UseriDao(Connection con){
        this.con = con;

        try {
            addUser = con.prepareStatement("INSERT INTO useri VALUES(null,? ,? )");
            findAllUsers = con.prepareStatement("SELECT * FROM useri");
            findUserByUsername = con.prepareStatement("SELECT * FROM useri WHERE username = ?");
            deleteUser = con.prepareStatement("DELETE FROM useri WHERE username = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(Useri user){
        try {
            addUser.setString(1, user.getUsername());
            while(!Password_Validation(user.getPassword())){
                System.out.println("Parola trebuie să conţină cel putin o cifră și un caracter special");
                String parola1 = sc.nextLine();
                user.setPassword(parola1);
            }
            addUser.setString(2, user.getPassword());
            return addUser.executeUpdate() != 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean Password_Validation(String password)
    {

        if(password.length()>=4)
        {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");


            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;

    }

    public List<Useri> getAllUsers(){
        ArrayList<Useri> list = new ArrayList<>();

        try {
            ResultSet rs = findAllUsers.executeQuery();
            while(rs.next()){
                Useri u = new Useri( rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"));
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Optional<Useri> getUserByUsername(String username){

        try {
            findUserByUsername.setString(1, username);
            ResultSet rs = findUserByUsername.executeQuery();
            if(rs.next()){
                return Optional.of(new Useri(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean deleteUser(String username){
        try {
            deleteUser.setString(1, username);
            return deleteUser.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
