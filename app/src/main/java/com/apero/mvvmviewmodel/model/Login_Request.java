package com.apero.mvvmviewmodel.model;

/**
 * Created by hasai on 01/02/17.
 */

public class Login_Request {

    public int client_id;
    public String client_secret;
    public String email;
    public String password;
    public String device_id;
    public String push_token;

    public Login_Request(int client_id, String client_secret, String email, String password, String device_id, String push_token)
    {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.email = email;
        this.password = password;
        this.device_id = device_id;
        this.push_token = push_token;
    }


}
