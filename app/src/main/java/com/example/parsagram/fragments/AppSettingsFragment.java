package com.example.parsagram.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parsagram.LoginActivity;
import com.example.parsagram.R;
import com.example.parsagram.models.Zipcode;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class AppSettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Button btnEdit;

    private static final String TAG = "AppSettingsFragment";

    //zipcode variable
    private Button btnUpdate;
    private Button btnLogout;
    private Button btnAbout;
    private Button btnSupport;
    private EditText tvZipcode;
    //end

    public AppSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_settings, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        btnAbout = view.findViewById(R.id.btnAbout);

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/A-Minus-Team/weather-wear#readme"));
                startActivity(browserIntent);
            }
        });

        btnSupport = view.findViewById(R.id.btnSupport);

        btnSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "bed6@njit.edu" });
                email.putExtra(Intent.EXTRA_SUBJECT, "Support Query");
                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });

        //zipcode code
        tvZipcode = view.findViewById(R.id.tvZipcode);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick update zipcode");
                String zipcode = tvZipcode.getText().toString();

                updateZipcode(zipcode);
            }

        });
        //end

    }

    //This only code not only updates but also uploads zipcodes
    private void updateZipcode(String zipcode) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("userProfile");
        ParseUser currentUser = ParseUser.getCurrentUser();

        query.whereEqualTo("user", currentUser);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) { //updates zipcode if query exist
                    ParseObject update = objects.get(0);
                    update.put("zipcode", zipcode);
                    update.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                Toast.makeText(getActivity(), "Zipcode Updated", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    tvZipcode.setText("");
                }
                else{ //uploads zipcode if the query doesn't exist
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    Zipcode uploadZipcode = new Zipcode();
                    uploadZipcode.setZipcode(zipcode);
                    uploadZipcode.setUser(currentUser);
                    uploadZipcode.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                Toast.makeText(getActivity(), "Zipcode Uploaded", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    tvZipcode.setText("");
                }
            }
        });

    }
    //end


    private void logOut() {
        ParseUser.logOut();
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    private void toEditProfile() {
        Fragment fragment = new EditProfileFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}