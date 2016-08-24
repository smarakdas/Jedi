package com.example.android.starwars.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.starwars.Fragment.MovieOutput;
import com.example.android.starwars.ModelClass.MovieModel;
import com.example.android.starwars.R;

import java.util.ArrayList;

/**
 * Created by sanu on 24-Aug-16.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.myViewHolder> {

    Context context;
    ArrayList<MovieModel> list;
    FragmentTransaction ft;

    public  MovieAdapter(Context context, ArrayList<MovieModel> list)
    {
        this.context=context;
        this.list=list;
        System.out.println("####size of list"+list.size());
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemView = inflater.inflate(R.layout.listcardview, parent, false);
        myViewHolder holder = new myViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {
        holder.name.setText(list.get(position).getTitle());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("movie",list.get(holder.getAdapterPosition()).getTitle());
                Fragment movieOutput = new MovieOutput();
                movieOutput.setArguments(bundle);
                ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.startPage,movieOutput);
                ft.addToBackStack(movieOutput.getTag());
                ft.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        public TextView name;



        public myViewHolder(View itemView) {
            super(itemView);


            name = (TextView) itemView.findViewById(R.id.name_textView);

        }
    }
}
