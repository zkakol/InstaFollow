package model;

import utils.InstagramUtil;

public class User {

    private static String username;
    private static String password;
    private boolean isLoggedIn;
    private InstagramUtil iu = null;

    public User(String user, String pass) {
        username = user;
        password = pass;

        iu = new InstagramUtil();
        isLoggedIn = iu.login(this);
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }

}
