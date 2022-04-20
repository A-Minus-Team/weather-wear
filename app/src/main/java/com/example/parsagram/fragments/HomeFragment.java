package com.example.parsagram.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parsagram.PostPants;
import com.example.parsagram.PostPantsAdapter;
import com.example.parsagram.PostShirt;
import com.example.parsagram.PostShirtAdapter;
import com.example.parsagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HomeFragment extends Fragment {

    public static final String TAG = "HomeFragment";
    protected PostShirtAdapter adapterShirts;
    protected PostPantsAdapter adapterPants;
    private RecyclerView rvShirts;
    private RecyclerView rvPants;
    private SwipeRefreshLayout swipeContainerShirts;
    private SwipeRefreshLayout swipeContainerPants;
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

        rvShirts = view.findViewById(R.id.rvShirts);
        rvPants = view.findViewById(R.id.rvPants);
        allShirts = new ArrayList<>();
        allPants = new ArrayList<>();
        adapterShirts = new PostShirtAdapter(getContext(), allShirts);
        adapterPants = new PostPantsAdapter(getContext(), allPants);



        rvShirts.setAdapter(adapterShirts);
        rvPants.setAdapter(adapterPants);


        rvShirts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvPants.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        queryShirts();
        queryPants();

        swipeContainerShirts = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerShirts);
        swipeContainerPants = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainerPants);
        // Setup refresh listener which triggers new data loading
        swipeContainerShirts.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                // Remember to CLEAR OUT old items before appending in the new ones
                adapterShirts.clear();
                // ...the data has come back, add new items to your adapterShirts...
                queryShirts();


                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainerShirts.setRefreshing(false);
            }
        });
        swipeContainerPants.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                // Remember to CLEAR OUT old items before appending in the new ones
                adapterShirts.clear();
                // ...the data has come back, add new items to your adapterShirts...
                queryShirts();


                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainerPants.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainerShirts.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

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


    //The start of Color Recommending Algorithm

    //generate a random number to determine a random color
    public int generateRandomNum(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;

    }


    /*
        Idea for the future, if we can use a for loop to go through each item and find the color available from the user
        we can try to get the color, put it into a list of string

        String[] arr = {Blue, Blue, Red, Red}
        int N = arr.size();

        Set<String> DistString = new HashSet<String>();

        // Traverse the array
        for(int i = 0; i < N; i++)
        {

            // If current string not
            // present into the set
            if (!DistString.contains(arr.get(i)))
            {

                // Insert current string
                // into the set
                DistString.add(arr.get(i));
            }
        }

        This should give us unique colors available. Then we will use

        max = DistString.size()
        into generateRandomNum(min, max), min always 1
     */

    //determining random color
    public String randomColor (int ranNum){
        String color;
        switch (ranNum) {
            case 1:  color = "Red";
                break;
            case 2:  color = "Orange";
                break;
            case 3:  color = "Yellow";
                break;
            case 4:  color = "Green";
                break;
            case 5:  color = "Blue";
                break;
            case 6:  color = "Purple";
                break;
            case 7:  color = "Black";
                break;
            case 8:  color = "Brown";
                break;
            case 9:  color = "White";
                break;
            default: color = "";
                break;
        }
        return color;
    }

    //takes the random color, and pairs it with best matched color
    public static String[] colorPairList (String currColor){
        String[] arr = {};
        String color;
        switch (currColor) {
            case "Blue":
                arr = new String[] {
                        "Red",
                        "Yellow",
                        "White",
                        "Black",
                };
                break;
            case "Red":
                arr = new String[] {
                    "Blue",
                    "White",
                    "Black"
                };
                break;
            case "Yellow":
                arr = new String[] {
                        "Blue",
                        "White",
                        "Black",
                        "Green"
                };
                break;
            case "Green":
                arr = new String[] {
                        "Orange",
                        "White",
                        "Black",
                        "Purple"
                };
                break;
            case "Purple":
                arr = new String[] {
                        "Orange",
                        "White",
                        "Black",
                        "Green"
                };
                break;
            case "White":
                arr = new String[] {
                        "Blue",
                        "Black"
                };
                break;
            case "Orange":
                arr = new String[] {
                        "Blue",
                        "Black",
                        "Green",
                        "White"
                };
                break;
            case "Black":
                arr = new String[] {
                        "White"
                };
                break;
            case "Brown":
                arr = new String[] {
                        "White",
                        "Black"
                };
                break;
            default: color = "White";
                break;
        }
        return arr;
    }

    //grabs the list of best possible colors and pick one of them at random
    public String generateColorPair(String[] arr){
        int rnd = new Random().nextInt(arr.length);
        return arr[rnd];
    }

    //end of color recommending algorithm


}