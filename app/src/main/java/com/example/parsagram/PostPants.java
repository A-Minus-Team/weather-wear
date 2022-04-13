package com.example.parsagram;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("postPants")
public class PostPants extends ParseObject {

    public static final String KEY_IMAGE = "pantsImage";
    public static final String KEY_USER = "userPants";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_COLOR = "Color";

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }
    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public Date getCreatedAt() {
        return getDate(KEY_CREATED_AT);
    }

    public String getColor() {
        return getString(KEY_USER);
    }
}
