package com.ras.pustakgrah.Models;

public class userModel  {
    String profilepic,email_id,password,username,user_id,last_msg;

    public userModel(String profilepic, String email_id, String password,String user_id, String username, String last_msg) {
        this.profilepic = profilepic;
        this.email_id = email_id;
        this.password = password;
        this.username = username;
        this.last_msg = last_msg;
        this.user_id = user_id;
    }
    public userModel()
    {}

    public userModel(String username,String email_id,String password)

    {
        this.username = username;
        this.email_id = email_id;
        this.password = password;
    }
    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLast_msg() {
        return last_msg;
    }

    public void setLast_msg(String last_msg) {
        this.last_msg = last_msg;
    }
}
