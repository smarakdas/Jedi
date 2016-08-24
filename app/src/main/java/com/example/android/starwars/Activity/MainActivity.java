package com.example.android.starwars.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.starwars.Adapter.ChatAdapter;
import com.example.android.starwars.Adapter.MovieAdapter;
import com.example.android.starwars.Adapter.PeopleAdapter;
import com.example.android.starwars.ModelClass.MessageModel;
import com.example.android.starwars.ModelClass.MovieModel;
import com.example.android.starwars.ModelClass.PeopleModel;
import com.example.android.starwars.MySingleton;
import com.example.android.starwars.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static int testValue=0;
    int count;
    private Handler handler = new Handler();

    RelativeLayout optionLayout, outputLayout;
    Button people,movie;
    String str =null;
    private ProgressDialog pd = null;
    ArrayList<MovieModel> movieList = new ArrayList<>();
    ArrayList<PeopleModel> peopleList= new ArrayList<>();
    FragmentTransaction ft;

    ArrayList<MessageModel> chatlist = null;
    ChatAdapter adapter = null;
    RecyclerView chat_list = null;

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
        pd.setTitle("Loading");



        people=(Button)findViewById(R.id.people_button);
        movie=(Button)findViewById(R.id.film_button);
        optionLayout =(RelativeLayout)findViewById(R.id.button_relative);
        outputLayout =(RelativeLayout)findViewById(R.id.textView_relative);



        chat_list = (RecyclerView) findViewById(R.id.chat_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(2000);
        chat_list.setItemAnimator(animator);

        chat_list.setLayoutManager(layoutManager);
        chatlist = new ArrayList<>();
        adapter = new ChatAdapter(this, chatlist);
        chat_list.setAdapter(adapter);
        chat_list.scrollToPosition(count);



        recyclerView = (RecyclerView) findViewById(R.id.movie_recyclerView);
        movieLayoutManager =  new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        movieAdapter = new MovieAdapter(this,movieList);
        peopleAdapter= new PeopleAdapter(this,peopleList);

        recyclerView.setLayoutManager(movieLayoutManager);

        addWelcome();


    }

    @Override
    public void onBackPressed() {
        if(MainActivity.testValue==1)
        {
            handler.postDelayed(new ViewUpdater("What else would you like to know about?", false),100);
            recyclerView.setVisibility(View.GONE);
            handler.postDelayed(new ButtonUpdater(), 1000);
            MainActivity.testValue=0;

        }
        super.onBackPressed();
    }



    private void addWelcome() {

        //hi msg
        handler.postDelayed(new ViewUpdater("Hi", false), 1000);



        //welcome to star wars bot
        handler.postDelayed(new ViewUpdater("welcome to star wars bot", false), 3000);


        //What would you like to know about?
        handler.postDelayed(new ViewUpdater("What would you like to know about?", false),5000);

        //buton into focus
        handler.postDelayed(new ButtonUpdater(), 6000);


    }
    private void addMsg(String msg, boolean isSender)
    {
         count = adapter.getItemCount();
        MessageModel model = new MessageModel();
        model.setMessage(msg);
        model.setSender(isSender);
        chatlist.add(model);
        adapter.notifyItemInserted(count);
        chat_list.scrollToPosition(count);
    }



    public void setAdapter(MovieAdapter adapter, PeopleAdapter peopleAdapter)
    {
        if(str.equals("movie"))
        {
            recyclerView.setVisibility(View.VISIBLE);
            movieAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(movieAdapter);

        }
        else if(str.equals("people"))
        {
            recyclerView.setVisibility(View.VISIBLE);
           peopleAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(peopleAdapter);
        }

    }

    private class ViewUpdater implements Runnable{
        private String mString;
        private boolean isSender;

        public ViewUpdater(String string,boolean isSender){
            mString = string;
            this.isSender=isSender;

        }

        @Override
        public void run() {
            addMsg(mString,isSender);
        }
    }

    private class ButtonUpdater implements Runnable{


        public ButtonUpdater(){

        }

        @Override
        public void run() {
            System.out.println("####optionalayout coming here");
            optionLayout.setVisibility(View.VISIBLE);
            movie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    addMsg("movie",true);
                    str ="movie";
                    getData(str);
                    handler.postDelayed(new ViewUpdater("Which movie you would like to know about?", false),2000);

                }
            });

            people.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addMsg("people",true);
                    str ="people";
                    getData(str);
                    handler.postDelayed(new ViewUpdater("Which people you would like to know about?", false),2000);

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
        movieList.clear();

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
        peopleList.clear();

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
