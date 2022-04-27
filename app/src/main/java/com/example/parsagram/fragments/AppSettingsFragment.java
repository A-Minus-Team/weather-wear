package com.example.parsagram.fragments;

import android.content.Intent;
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
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class AppSettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Button btnEdit;

    private static final String TAG = "AppSettingsFragment";

    //zipcode variable
    private Button btnUpdate;
    private Button btnLogout;
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

        btnEdit = view.findViewById(R.id.btnEditProfile);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toEditProfile();
            }
        });

        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
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
                uploadZipcode(zipcode);
            }

        });
        //end

    }

    //This only uploads zipcode, no assigned user, and doesn't update
    private void uploadZipcode(String zipcode) {
        /*
        ParseQuery<Zipcode> query = new ParseQuery<Zipcode>(Zipcode.class);

        ParseUser user = ParseUser.getCurrentUser();
        String username = user.get("user").toString();
        */

        ParseObject postZipcode = new ParseObject("userProfile");
        postZipcode.put("zipcode", zipcode);
        //postZipcode.put(username, user);
        postZipcode.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Toast.makeText(getActivity(), "Zipcode Updated", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getActivity(), e.getMessage().toString(), Toast.LENGTH_LONG).show();

                }
                tvZipcode.setText("");
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