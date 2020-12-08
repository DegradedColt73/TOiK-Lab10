package com.example.demo.repository;

import com.example.demo.lockedAccountException;
import com.example.demo.model.User;
import com.example.demo.rest.UserApiController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private final Map<Integer, User> usersDatabase;
    private static final Logger LOG = LoggerFactory.getLogger(UserApiController.class);

    public UserRepository() {
        usersDatabase = new HashMap<>();

        usersDatabase.put(1, new User("cracker", "cracker1234", true, 0));
        usersDatabase.put(2, new User("marry", "marietta!#09", true, 0));
        usersDatabase.put(3, new User("silver", "$silver$", true, 0));
    }

    public boolean checkLogin(final String login, final String password) throws lockedAccountException {
        Integer key = 0;
        for(Map.Entry<Integer, User> entry : usersDatabase.entrySet()){
            if(entry.getValue().getLogin().equals(login)){
                key = entry.getKey();
                break;
            }
        }
        if (key == 0){
            return false;
        }
        if(!usersDatabase.get(key).isActive()){
            throw new lockedAccountException();
        }
        if(usersDatabase.get(key).getPassword().equals(password)){
            if(usersDatabase.get(key).isActive()){
                usersDatabase.get(key).setIncorrectLoginCounter(0);
                return true;
            }
            else{
                throw new lockedAccountException();
            }
        }
        else{
            usersDatabase.get(key).setIncorrectLoginCounter(usersDatabase.get(key).getIncorrectLoginCounter() + 1);
            if(usersDatabase.get(key).getIncorrectLoginCounter() >= 3){
                usersDatabase.get(key).setActive(false);
            }
        }

        return false;
    }
}
