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
    public static final String KEY_COLOR = "color";
    public static final String KEY_LENGTH = "size";
    public static final String KEY_THICKNESS = "thickness";


    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }
    public void setImage(ParseFile image) {
        put(KEY_IMAGE,image);
    }
    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user) {
        put(KEY_USER,user);
    }
    public Date getCreatedAt() {
        return getDate(KEY_CREATED_AT);
    }
    public void setCreatedAt(ParseFile createdAt) { put(KEY_CREATED_AT,createdAt); }
    public String getColor() {
        return getString(KEY_COLOR);
    }
    public void setColor(String color) {
        put(KEY_COLOR,color);
    }
    public String getLength() {
        return getString(KEY_LENGTH);
    }
    public void setLength(String length) {
        put(KEY_LENGTH,length);
    }
    public String getThickness() {
        return getString(KEY_THICKNESS);
    }
    public void setThickness(String thickness) {
        put(KEY_THICKNESS,thickness);
    }
}
