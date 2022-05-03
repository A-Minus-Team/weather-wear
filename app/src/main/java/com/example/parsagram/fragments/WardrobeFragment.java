package com.example.parsagram.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parsagram.models.PostPants;
import com.example.parsagram.models.PostShirt;
import com.example.parsagram.R;
import com.example.parsagram.adapters.ViewPantsAdapter;
import com.example.parsagram.adapters.ViewShirtAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class WardrobeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = "WardrobeFragment";
    private Button btnShirt;
    private Button btnPants;
    private RecyclerView rvShirts;
    private RecyclerView rvPants;
    protected List<PostShirt> allShirts;
    protected List<PostPants> allPants;
    protected ViewShirtAdapter adapterShirts;
    protected ViewPantsAdapter adapterPants;

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

        rvShirts = view.findViewById(R.id.rvShirts);
        rvPants = view.findViewById(R.id.rvPant);
        allShirts = new ArrayList<>();
        allPants = new ArrayList<>();
        adapterShirts = new ViewShirtAdapter(getContext(), allShirts);
        adapterPants = new ViewPantsAdapter(getContext(), allPants);
        rvShirts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPants.setLayoutManager(new LinearLayoutManager(getContext()));

        rvShirts.setAdapter(adapterShirts);
        rvPants.setAdapter(adapterPants);

        queryShirts();
        queryPants();
    }

    protected void queryShirts() {
        ParseQuery<PostShirt> query = ParseQuery.getQuery(PostShirt.class);
        query.setLimit(20);
        query.include(PostShirt.KEY_USER); // how does this work?
        query.findInBackground(new FindCallback<PostShirt>() {
            @Override
            public void done(List<PostShirt> shirts, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Query error", e);
                    return;
                }
                for(PostShirt shirt:shirts) {
                    Log.i(TAG, "Post: " + shirt.getColor());
                }
                allShirts.addAll(shirts);
                adapterShirts.notifyDataSetChanged();
            }
        });
    }

    protected void queryPants() {
        ParseQuery<PostPants> query = ParseQuery.getQuery(PostPants.class);
        query.setLimit(20);
        query.include(PostShirt.KEY_USER); // how does this work?
        query.findInBackground(new FindCallback<PostPants>() {
            @Override
            public void done(List<PostPants> pants, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Query error", e);
                    return;
                }
                for(PostPants pant:pants) {
                    Log.i(TAG, "Post: " + pant.getColor());
                }
                allPants.addAll(pants);
                adapterPants.notifyDataSetChanged();
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
        Fragment fragment = new PantsFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}