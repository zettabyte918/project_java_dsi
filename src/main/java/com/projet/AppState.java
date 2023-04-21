package com.projet;

import com.projet.models.User;

public class AppState {
    private static AppState instance;
    private User user;
    private boolean loggedIn;

    private AppState() {
        // private constructor to prevent instantiation from outside
    }

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
