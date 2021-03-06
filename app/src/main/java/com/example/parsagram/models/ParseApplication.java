package com.example.parsagram.models;

import com.example.parsagram.models.PostPants;
import com.example.parsagram.models.PostShirt;
import com.example.parsagram.models.Zipcode;
import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(PostShirt.class);
        ParseObject.registerSubclass(PostPants.class);
        ParseObject.registerSubclass(Zipcode.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("B36loCuH4fFyf4JwLKDJ5PiGaxHuijayaFmlRYPg")
                .clientKey("yIi1oJ9ZNp9CUchFVQj4LodneWq9ozmfd4FeFzgF")
                .server("https://parseapi.back4app.com")
                .build()
        );


    }
}
