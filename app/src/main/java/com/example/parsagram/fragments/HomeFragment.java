package com.example.parsagram.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.parsagram.LoginActivity;
import com.example.parsagram.Post;
import com.example.parsagram.PostAdapter;
import com.example.parsagram.PostPants;
import com.example.parsagram.PostShirt;
import com.example.parsagram.PostShirtAdapter;
import com.example.parsagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    protected PostShirtAdapter adapter;
    private RecyclerView rvPosts;
    private SwipeRefreshLayout swipeContainer;
    protected List<PostShirt> allShirts;
    protected List<PostPants> allPants;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        rvPosts = view.findViewById(R.id.rvPosts);
        allShirts = new ArrayList<>();
        allPants = new ArrayList<>();
        adapter = new PostShirtAdapter(getContext(), allShirts);

        rvPosts.setAdapter(adapter);

        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        queryShirts();

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                // Remember to CLEAR OUT old items before appending in the new ones
                adapter.clear();
                // ...the data has come back, add new items to your adapter...
                queryShirts();
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    protected void queryShirts() {
        ParseQuery<PostShirt> query = ParseQuery.getQuery(PostShirt.class);
        query.setLimit(20);
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
                adapter.notifyDataSetChanged();
            }
        });
    }

    protected void queryPants() {
        ParseQuery<PostPants> query = ParseQuery.getQuery(PostPants.class);
        query.setLimit(20);
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
                adapter.notifyDataSetChanged();
            }
        });
    }



}