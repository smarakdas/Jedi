package com.example.android.starwars.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.starwars.ModelClass.MovieModel;
import com.example.android.starwars.ModelClass.PeopleModel;
import com.example.android.starwars.Adapter.MovieAdapter;
import com.example.android.starwars.MySingleton;
import com.example.android.starwars.Adapter.PeopleAdapter;
import com.example.android.starwars.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Handler handler = new Handler();
    private TextView view1,view2,view3, view4,view5;
    RelativeLayout optionLayout, outputLayout;
    Button people,movie;
    String str =null;
    private ProgressDialog pd = null;
    ArrayList<MovieModel> movieList = new ArrayList<>();
    ArrayList<PeopleModel> peopleList= new ArrayList<>();
    FragmentTransaction ft;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager movieLayoutManager;
    MovieAdapter movieAdapter;
    PeopleAdapter peopleAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);


        //Initialize view
        view1=(TextView)findViewById(R.id.textView1);
        view2=(TextView)findViewById(R.id.textView2);
        view3=(TextView)findViewById(R.id.textView3);
        view4 =(TextView)findViewById(R.id.textView4);
        view5 =(TextView)findViewById(R.id.textView5);
        people=(Button)findViewById(R.id.people_button);
        movie=(Button)findViewById(R.id.film_button);
        optionLayout =(RelativeLayout)findViewById(R.id.button_relative);
        outputLayout =(RelativeLayout)findViewById(R.id.textView_relative);

        handler.postDelayed(new ViewUpdater("Hi", view1), 1000);
        handler.postDelayed(new ViewUpdater("Welcome to star wars bot", view2), 3000);
        handler.postDelayed(new ViewUpdater("What would you like to know about", view3), 5000);
        handler.postDelayed(new ButtonUpdater(movie,people), 5000);

        recyclerView = (RecyclerView) findViewById(R.id.movie_recyclerView);
        movieLayoutManager =  new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        movieAdapter = new MovieAdapter(this,movieList);
        peopleAdapter= new PeopleAdapter(this,peopleList);

        recyclerView.setLayoutManager(movieLayoutManager);




    }

    public void setAdapter(MovieAdapter adapter, PeopleAdapter peopleAdapter)
    {
        if(str.equals("movie"))
        {
            movieAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(movieAdapter);

        }
        else if(str.equals("people"))
        {
            peopleAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(peopleAdapter);
        }

    }

    private class ViewUpdater implements Runnable{
        private String mString;
        private TextView mView;

        public ViewUpdater(String string, TextView view){
            mString = string;
            mView = view;
        }

        @Override
        public void run() {
            mView.setVisibility(View.VISIBLE);
            mView.setText(mString);
        }
    }

    private class ButtonUpdater implements Runnable{
        private Button people,movie;

        public ButtonUpdater(Button movie, Button people){
            this.movie = movie;
            this.people = people;
        }

        @Override
        public void run() {
              //movie.setVisibility(View.VISIBLE);
           // people.setVisibility(View.VISIBLE);
            optionLayout.setVisibility(View.VISIBLE);
            movie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view4.setVisibility(View.VISIBLE);
                view4.setText("movie");
                    str ="movie";
                    view5.setVisibility(View.VISIBLE);
                    view5.setText("Which movie you would like to know about?");
                    getData(str);


                }
            });

            people.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   view4.setVisibility(View.VISIBLE);
                    view4.setText("people");
                    str ="people";
                    view5.setVisibility(View.VISIBLE);
                    view5.setText("Which people you would like to know about?");
                    getData(str);

                }
            });

        }
    }

     void getData(String data)
    {
        optionLayout.setVisibility(View.GONE);
        outputLayout.setVisibility(View.VISIBLE);

        if(data.equals("movie"))
            downloadMovieData("http://swapi.co/api/films/");
        else if(data.equals("people"))
            downloadPeopleData("http://swapi.co/api/people/");

    }

    public void downloadMovieData(String url) {


        MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        pd.show();

        System.out.println("####coming here");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println("####response" + response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject obj = jsonArray.getJSONObject(i);

                        String movieName = obj.getString("title");
                        String urlName=obj.getString("url");

                        MovieModel movieModel = new MovieModel();
                        movieModel.setTitle(movieName);


                        movieList.add(movieModel);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setAdapter(movieAdapter,peopleAdapter);
                        pd.dismiss();
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    public void downloadPeopleData(String url) {


        MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        pd.show();

        System.out.println("####coming here");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println("####response" + response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject obj = jsonArray.getJSONObject(i);

                        String peopleName = obj.getString("name");


                        PeopleModel peopleModel = new PeopleModel();
                        peopleModel.setPeopleName(peopleName);

                        peopleList.add(peopleModel);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setAdapter(movieAdapter,peopleAdapter);
                        pd.dismiss();
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
