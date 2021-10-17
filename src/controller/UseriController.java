package controller;

import dao.UseriDao;
import model.Useri;

import java.util.List;
import java.util.Optional;


public class UseriController {

    private UseriDao userDao;

    private UseriController(){
        userDao = new UseriDao(ConnectionController.getInstance().getConnection());
    }

    private static final class SingletonHolder{
        private static final UseriController instance = new UseriController();
    }

    public static UseriController getInstance(){
        return SingletonHolder.instance;
    }

    public List<Useri> getAllUsers(){
        return userDao.getAllUsers();
    }

    public boolean deleteUser(String username){
        return userDao.deleteUser(username);
    }

    public Optional<Useri> getUserByUsername(String username){
        return userDao.getUserByUsername(username);
    }

    public boolean addUser(Useri user){
        if(!getUserByUsername(user.getUsername()).isPresent()){
            System.out.println("S-a adaugat un utilizator");
            return userDao.addUser(user);
        }else{
            System.out.println("User-ul se afla deja in baza de date");
            return false;
        }
    }

    public boolean loginUser(String username, String parola){
        Optional<Useri> op = getUserByUsername(username);

        if(op.isPresent()){
            Useri u = op.get();
            return u.getPassword().equals(parola);
        }

        return false;
    }
}
