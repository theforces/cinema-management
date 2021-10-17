package dao;

import model.ReservariUseri;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RezervariUseriDao {

    private Connection con;

    private PreparedStatement addRezervare;
    private PreparedStatement findAllRezervari;
    private PreparedStatement findRezervaretByUsername;
    private PreparedStatement deleteRezervareByUsername;

    public  RezervariUseriDao(Connection con){
        this.con = con;

        try {
            addRezervare = con.prepareStatement("INSERT INTO rezervariuseri VALUES(null, ?,?,?,?,?)");
            findAllRezervari = con.prepareStatement("SELECT * FROM rezervariuseri");
            findRezervaretByUsername = con.prepareStatement("SELECT * FROM rezervariuseri WHERE username = ?");
            deleteRezervareByUsername = con.prepareStatement("DELETE from rezervariuseri where username = ?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addRezervare(ReservariUseri reservariUseri){
        try {
            addRezervare.setString(1, reservariUseri.getUsername());
            addRezervare.setInt(2, reservariUseri.getSala());
            addRezervare.setString(3, reservariUseri.getFilm());
            addRezervare.setDate(4, reservariUseri.getData());
            addRezervare.setInt(5, reservariUseri.getIdUser());

            return addRezervare.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ReservariUseri> findAllRezervari(){
        ArrayList<ReservariUseri> lista = new ArrayList<>();

        try {
            ResultSet rs = findAllRezervari.executeQuery();
            while(rs.next()){
                ReservariUseri reservariUseri = new ReservariUseri(rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("sala"),
                        rs.getString("film"),
                        rs.getDate("data"),
                        rs.getInt("idUser") );

                lista.add(reservariUseri);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public List<ReservariUseri> getRezervaretByUsername(String username){
        ArrayList<ReservariUseri> reservariUseri = new ArrayList<>();
        try {
            findRezervaretByUsername.setString(1, username);
            ResultSet rs = findRezervaretByUsername.executeQuery();

            while(rs.next()){
                ReservariUseri reservari =  new ReservariUseri(rs.getInt("id"),
                       rs.getString("username"),
                       rs.getInt("sala"),
                       rs.getString("film"),
                       rs.getDate("data"),
                       rs.getInt("idUser") ) ;
                reservariUseri.add(reservari);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservariUseri;
    }

    public boolean deleteRezervareByUsername(String username) {
        try {
            deleteRezervareByUsername.setString(1, username);
            return deleteRezervareByUsername.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
