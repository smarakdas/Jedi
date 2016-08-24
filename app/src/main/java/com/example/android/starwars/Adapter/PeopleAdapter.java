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

import com.example.android.starwars.Fragment.PeopleOutput;
import com.example.android.starwars.ModelClass.PeopleModel;
import com.example.android.starwars.R;

import java.util.ArrayList;

/**
 * Created by sanu on 24-Aug-16.
 */
public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.myViewHolder> {

    Context context;
    ArrayList<PeopleModel> list;
    FragmentTransaction ft;

    public PeopleAdapter(Context context, ArrayList<PeopleModel> list)
    {
        this.context=context;
        this.list=list;
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
        holder.name.setText(list.get(position).getPeopleName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("people",list.get(holder.getAdapterPosition()).getPeopleName());
                Fragment peopleOutput = new PeopleOutput();
                peopleOutput.setArguments(bundle);
                ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.startPage,peopleOutput);
                ft.addToBackStack(peopleOutput.getTag());
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
