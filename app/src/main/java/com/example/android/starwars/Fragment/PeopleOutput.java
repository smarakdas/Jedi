package com.example.android.starwars.Fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.starwars.MySingleton;
import com.example.android.starwars.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class PeopleOutput extends Fragment {


    private ProgressDialog pd = null;
    View view;
    public TextView actorNameTextview;
    public TextView actorHeightTextview,urlTextView;
    String peopleName =null;
    String name,height,urlName;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_people_output, container, false);
        pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        Bundle bundle=this.getArguments();
        peopleName = bundle.getString("people");
        downloadMovieData("http://swapi.co/api/people/");
        actorNameTextview = (TextView)view.findViewById(R.id.peopleResult_Name);
        actorHeightTextview= (TextView)view.findViewById(R.id.peopleResult_height);
        urlTextView= (TextView)view.findViewById(R.id.peopleResult_url);
        return view;

    }
    public void downloadMovieData(String url) {


        MySingleton.getInstance(getActivity().getApplicationContext()).getRequestQueue();
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
                        if(obj.getString("name").equals(peopleName))
                        {
                            name = obj.getString("name");
                            height=obj.getString("height");
                            urlName=obj.getString("url");


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        actorNameTextview.setText(name);
                        actorHeightTextview.setText(height);
                        urlTextView.setText(urlName);
                        pd.dismiss();
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);
    }

}
