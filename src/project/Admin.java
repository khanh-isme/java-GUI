package project;

import java.util.List;
import java.util.*;



public class Admin extends User {
    private List<User> users;

    public Admin(String username, String password) {
        super(username, password);

    }
    public void addlistUser(List<User> users) {
        this.users=users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void showUser(List<User>users) {
        for(User user : users) {
            System.out.println("name "+ user.getUsername());
            System.out.println("pass: " +user.getPassword());
        }
    }



    // Getters and Setters
}
