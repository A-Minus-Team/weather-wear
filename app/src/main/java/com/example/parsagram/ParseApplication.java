package com.example.parsagram;

import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("2i3Y498sXxdSJSTtpcJOeqcTEUrbG5AG1jGhYpRy")
                .clientKey("GyzfMKy7hXFnRSud4ULsgAWB8aQ3bzyMEbbQTkFf")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
