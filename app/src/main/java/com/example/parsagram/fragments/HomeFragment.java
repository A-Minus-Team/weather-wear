package com.example.parsagram.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.parsagram.models.PostPants;
import com.example.parsagram.adapters.PostPantsAdapter;
import com.example.parsagram.models.PostShirt;
import com.example.parsagram.adapters.PostShirtAdapter;
import com.example.parsagram.R;
import com.example.parsagram.WeatherActivity;
import com.example.parsagram.models.Zipcode;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

//import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


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
    private TextView tvTemperature;
    private TextView tvDescription;
    private JSONArray daily;
    private String weatherKey = "df4e0b5f52ecc79b45178eb254a901eb";
    private String zipcodeKey = "LoTK4jQ-CyKwOXAGfsoo1bbxVu1MgQiSuZttF6yXD88";
    private List<Zipcode> zipcode;

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
        super.onViewCreated(view, savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        rvShirts = view.findViewById(R.id.rvShirts);
        rvPants = view.findViewById(R.id.rvPants);
        tvTemperature = view.findViewById(R.id.tvTemperature);
        tvDescription = view.findViewById(R.id.tvDescription);
        allShirts = new ArrayList<>();
        allPants = new ArrayList<>();
        zipcode = new ArrayList<>();
        adapterShirts = new PostShirtAdapter(getContext(), allShirts);
        adapterPants = new PostPantsAdapter(getContext(), allPants);

        rvShirts.setAdapter(adapterShirts);
        rvPants.setAdapter(adapterPants);

        rvShirts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvPants.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Get zipcode of user
        queryZipcode();

        // Backlog: 7 day temperature
        /*tvTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sevenDay(v);
            }
        });*/

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

    protected void queryZipcode() {
        ParseQuery<Zipcode> query = ParseQuery.getQuery(Zipcode.class);
        query.setLimit(1);
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<Zipcode>() {
            @Override
            public void done(List<Zipcode> zipcodes, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Query error", e);
                    return;
                }
                for (Zipcode zip:zipcodes) {
                    Log.i(TAG, "zips " + zip.getZipcode());
                }
                // Get latitude and longitude
                String[] latLong = latLong(zipcodes.get(0).getZipcode());
                Log.i(TAG, latLong[0] + " : " + latLong[1]);

                // Then get weather for lat and long
                String[] weather = currentWeather(latLong[0], latLong[1]);
                String temperature = weather[0] + " °F";
                String description = weather[1];
                tvTemperature.setText(temperature);
                tvDescription.setText(description);

            }
        });

    }


    protected void queryShirts() {
        ParseQuery<PostShirt> query = ParseQuery.getQuery(PostShirt.class);
        query.setLimit(20);
        query.whereEqualTo("userShirt", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<PostShirt>() {
            @Override
            public void done(List<PostShirt> shirts, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Query error", e);
                    return;
                }
                allShirts.addAll(shirts);
                adapterShirts.notifyDataSetChanged();
            }
        });
    }

    protected void queryPants() {
        ParseQuery<PostPants> query = ParseQuery.getQuery(PostPants.class);
        query.setLimit(20);
        query.whereEqualTo("userPants", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<PostPants>() {
            @Override
            public void done(List<PostPants> pants, ParseException e) {
                if(e != null) {
                    Log.e(TAG, "Query error", e);
                    return;
                }
                allPants.addAll(pants);
                adapterPants.notifyDataSetChanged();
            }
        });
    }

    // To get current weather data
    public String[] currentWeather(String lat, String lng) {
        //inline will store the JSON data streamed in string format
        String inline = "";
        String description = "";
        String temperature = "";
        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lng + "&units=imperial&exclude=minutely,hourly&appid=" + weatherKey);
            //Parse URL into HttpURLConnection in order to open the connection in order to get the JSON data
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //Set the request to GET or POST as per the requirements
            conn.setRequestMethod("GET");
            //Use the connect method to create the connection bridge
            conn.connect();
            //Get the response status of the Rest API
            int responsecode = conn.getResponseCode();
            Log.i(TAG, "Response code is: " + responsecode);

            //Iterating condition to if response code is not 200 then throw a runtime exception
            //else continue the actual process of getting the JSON data
            if(responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " +responsecode);
            else
            {
                //Scanner functionality will read the JSON data from the stream
                Scanner sc = new Scanner(url.openStream());
                while(sc.hasNext())
                {
                    inline+=sc.nextLine();
                }
                Log.i(TAG, inline);
                //Close the stream when reading the data has been finished
                sc.close();
            }

            //JSONParser reads the data from string object and break each data into key value pairs
            JSONParser parse = new JSONParser();
            //Type cast the parsed json data in json object
            JSONObject jobj = (JSONObject) parse.parse(inline);
            JSONObject current = (JSONObject) jobj.get("current");
            daily = (JSONArray) jobj.get("daily");
            JSONArray weatherArr = (JSONArray) current.get("weather");
            JSONObject weather = (JSONObject) weatherArr.get(0);

            temperature = current.get("temp").toString();
            description = (String) weather.get("description");

            Log.i(TAG, description + " " + temperature);
        }
        catch(Exception e) {
            Log.e(TAG, "Weather", e);
        }
        return new String[] {temperature, description};
    }

    public String[] latLong(String zipcode) {
        //https://geocode.search.hereapi.com/v1/geocode?apiKey=LoTK4jQ-CyKwOXAGfsoo1bbxVu1MgQiSuZttF6yXD88&q=07010,%20usa
        String baseUrl = "https://geocode.search.hereapi.com/v1/geocode?apiKey=";
        String query = "&q=" + zipcode + ",%20usa";
        String request = baseUrl + zipcodeKey + query;

        String inline = "";
        String lat = "";
        String lng = "";
        try {
            URL url = new URL(request);
            //Parse URL into HttpURLConnection in order to open the connection in order to get the JSON data
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //Set the request to GET or POST as per the requirements
            conn.setRequestMethod("GET");
            //Use the connect method to create the connection bridge
            conn.connect();
            //Get the response status of the Rest API
            int responsecode = conn.getResponseCode();
            Log.i(TAG, "Response code is: " + responsecode);

            //Iterating condition to if response code is not 200 then throw a runtime exception
            //else continue the actual process of getting the JSON data
            if (responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            else {
                //Scanner functionality will read the JSON data from the stream
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                Log.i(TAG, inline);
                //Close the stream when reading the data has been finished
                sc.close();
            }

            //JSONParser reads the data from string object and break each data into key value pairs
            JSONParser parse = new JSONParser();
            //Type caste the parsed json data in json object
            JSONObject jobj = (JSONObject) parse.parse(inline);
            JSONArray items = (JSONArray) jobj.get("items");
            JSONObject item = (JSONObject)  items.get(0);
            JSONObject latLong = (JSONObject) item.get("position");
            lat = latLong.get("lat").toString();
            lng = latLong.get("lng").toString();
        } catch(Exception e) {
            Log.e(TAG, "Weather", e);
        }
        return new String[] {lat, lng};
    }

    public void sevenDay(View view)
    {
        String[] tempDay = new String[7];
        String[] tempMin = new String[7];
        String[] tempMax = new String[7];
        String[] weatherDescription = new String[7];

        // Parse and build daily weather data
        Iterator i = daily.iterator();
        int j = 0;
        while (i.hasNext() && j < 7) {
            JSONObject day = (JSONObject) i.next();
            JSONObject tempObj = (JSONObject) day.get("temp");
            tempDay[j] = tempObj.get("day").toString();
            tempMin[j] = tempObj.get("min").toString();
            tempMax[j] = tempObj.get("max").toString();
            JSONObject weather = (JSONObject) ((JSONArray) day.get("weather")).get(0);
            weatherDescription[j] = weather.get("description").toString();
            j++;
        }

        Intent in = new Intent();
        in.setClass(this.getContext(), WeatherActivity.class);
        //in.putExtra("daily", tempDay);
        startActivity(in);
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
                        "Black"
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