package com.rayhan.taskmaster.model;

public class users {
    private int userid;
    private String username;
    private String password;

    public users(int userid, String username, String password){
        this.userid = userid;
        this.username = username;
        this.password = password;
    }
    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return userid;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }


}