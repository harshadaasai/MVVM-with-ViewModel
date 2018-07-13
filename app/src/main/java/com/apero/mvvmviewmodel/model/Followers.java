package com.apero.mvvmviewmodel.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Followers implements Serializable{

    @SerializedName("fullname")
    public String fullname;
    @SerializedName("user_type")
    public String user_type;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
