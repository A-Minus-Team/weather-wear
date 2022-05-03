package com.example.parsagram.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("userProfile")
public class Zipcode extends ParseObject {

    public static final String KEY_ZIPCODE = "zipcode";
    public static final String KEY_USER = "user";


    public String getZipcode() {return getString(KEY_ZIPCODE);}
    public void setZipcode(String zipcode) {
        put(KEY_ZIPCODE, zipcode);
    }
    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user) {
        put(KEY_USER,user);
    }

}

