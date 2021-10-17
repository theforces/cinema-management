package controller;

import dao.RezervariUseriDao;
import model.ReservariUseri;

import java.util.List;

public class RezervariUseriController {

    private RezervariUseriDao rezervariUseriDao;

    private RezervariUseriController(){
        rezervariUseriDao = new RezervariUseriDao(ConnectionController.getInstance().getConnection());
    }

    private static final class SingletonHolder{
        private static final RezervariUseriController instance = new RezervariUseriController();
    }

    public static RezervariUseriController getInstance(){
        return RezervariUseriController.SingletonHolder.instance;
    }

    public List<ReservariUseri> findAllRezervari(){
        return rezervariUseriDao.findAllRezervari();
    }

    public boolean deleteRezervareByUsername(String username){
        return rezervariUseriDao.deleteRezervareByUsername(username);
    }

    public List<ReservariUseri> getRezervaretByUsername(String username){
        return rezervariUseriDao.getRezervaretByUsername(username);
    }

    public boolean addRezervare(ReservariUseri reservariUseri){
        System.out.println("S-a introdus o rezervare");
        return rezervariUseriDao.addRezervare(reservariUseri);

    }

}
