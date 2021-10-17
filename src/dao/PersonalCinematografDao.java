package dao;

import model.PersonalCinematograf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonalCinematografDao {

    Scanner sc = new Scanner(System.in);

    private Connection con;

    private PreparedStatement addPersonalCinematograf;
    private PreparedStatement findAllPersonalCinematograf;
    private PreparedStatement findPersonalCinematografByUsername;
    private PreparedStatement deletePersonalCinematograf;

    public PersonalCinematografDao(Connection con){
        this.con = con;

        try {
            addPersonalCinematograf = con.prepareStatement("INSERT INTO personalcinematograf VALUES(null,? ,? )");
            findAllPersonalCinematograf = con.prepareStatement("SELECT * FROM personalcinematograf");
            findPersonalCinematografByUsername = con.prepareStatement("SELECT * FROM personalcinematograf WHERE username = ?");
            deletePersonalCinematograf = con.prepareStatement("DELETE FROM personalcinematograf WHERE username = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addPersonalCinematograf(PersonalCinematograf personalCinematograf){
        try {
            addPersonalCinematograf.setString(1, personalCinematograf.getUsername());
            while(!Password_Validation(personalCinematograf.getPassword())){
                System.out.println("Parola trebuie să conţină cel putin o cifră și un caracter special");
                String parola1 = sc.nextLine();
                personalCinematograf.setPassword(parola1);
            }
            addPersonalCinematograf.setString(2, personalCinematograf.getPassword());
            return addPersonalCinematograf.executeUpdate() != 0;
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

    public List<PersonalCinematograf> getAllPersonalCinematograf(){
        ArrayList<PersonalCinematograf> list = new ArrayList<>();

        try {
            ResultSet rs = findAllPersonalCinematograf.executeQuery();
            while(rs.next()){
                PersonalCinematograf u = new PersonalCinematograf( rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"));
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Optional<PersonalCinematograf> getPersonalCinematografByUsername(String username){

        try {
            findPersonalCinematografByUsername.setString(1, username);
            ResultSet rs = findPersonalCinematografByUsername.executeQuery();
            if(rs.next()){
                return Optional.of(new PersonalCinematograf(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean deletePersonalCinematograf(String username){
        try {
            deletePersonalCinematograf.setString(1, username);
            return deletePersonalCinematograf.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
