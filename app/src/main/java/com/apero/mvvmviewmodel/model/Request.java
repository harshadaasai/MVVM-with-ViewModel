package com.apero.mvvmviewmodel.model;

public class Request {
    public int client_id;
    public String client_secret;
    public String country_code;
    public String mobile_number;
    public Boolean get_generated_otp;


    public Request(int client_id, String client_secret, String country_code, String mobile_number, Boolean get_generated_otp)
    {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.country_code = country_code;
        this.mobile_number = mobile_number;
        this.get_generated_otp = get_generated_otp;

    }
}