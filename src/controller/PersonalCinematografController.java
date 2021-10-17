package controller;

import dao.PersonalCinematografDao;
import model.PersonalCinematograf;

import java.util.List;
import java.util.Optional;

public class PersonalCinematografController {

    private PersonalCinematografDao personalCinematografDao;

    private PersonalCinematografController(){
        personalCinematografDao = new PersonalCinematografDao(ConnectionController.getInstance().getConnection());
    }

    private static final class SingletonHolder{
        private static final PersonalCinematografController instance = new PersonalCinematografController();
    }

    public static PersonalCinematografController getInstance(){
        return PersonalCinematografController.SingletonHolder.instance;
    }

    public List<PersonalCinematograf> getAllPersonalCinematograf(){
        return personalCinematografDao.getAllPersonalCinematograf();
    }

    public boolean deletePersonalCinematograf(String username){
        return personalCinematografDao.deletePersonalCinematograf(username);
    }

    public Optional<PersonalCinematograf> getPersonalCinematografByUsername(String username){
        return personalCinematografDao.getPersonalCinematografByUsername(username);
    }

    public boolean addPersonalCinematograf(PersonalCinematograf personalCinematograf){
        if(!getPersonalCinematografByUsername(personalCinematograf.getUsername()).isPresent()){
            System.out.println("S-a adaugat un personal cinematograf");
            return personalCinematografDao.addPersonalCinematograf(personalCinematograf);
        }else{
            System.out.println("Personalul cinematograf se afla deja in baza de date");
            return false;
        }
    }

    public boolean loginUser(String username, String parola){
        Optional<PersonalCinematograf> op = getPersonalCinematografByUsername(username);

        if(op.isPresent()){
            PersonalCinematograf pc = op.get();
            return pc.getPassword().equals(parola);
        }

        return false;
    }
}
