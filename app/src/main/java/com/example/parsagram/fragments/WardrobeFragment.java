package com.example.parsagram.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parsagram.R;

public class WardrobeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Button btnShirt;
    private Button btnPants;

    final Fragment shirtFragment = new ShirtFragment();

    public WardrobeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wardrobe, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnShirt = view.findViewById(R.id.btnShirt);
        btnPants = view.findViewById(R.id.btnPants);

        btnShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toShirtUpload();
            }
        });

        btnPants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPantsUpload();
            }
        });
    }


    private void toShirtUpload() {
        Fragment fragment = new ShirtFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    private void toPantsUpload() {
        Fragment fragment = new ShirtFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}