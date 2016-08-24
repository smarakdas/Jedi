package com.example.android.starwars.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.starwars.ModelClass.MessageModel;
import com.example.android.starwars.R;

import java.util.ArrayList;

/**
 * Created by sanu on 24-Aug-16.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    Context context = null;
    ArrayList<MessageModel> chatList = null;
    private final int SENT_MESSAGE = 0, RECEIVED_MESSAGE = 1;

    public ChatAdapter(Context context, ArrayList<MessageModel> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @Override
    public int getItemViewType(int position) {
        if (chatList.get(position).getSender()) {
            return SENT_MESSAGE;
        } else {
            return RECEIVED_MESSAGE;
        }
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        //Based on view type decide which type of view to supply with viewHolder
        switch (viewType) {
            case SENT_MESSAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_message, parent, false);
                break;

            case RECEIVED_MESSAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.received_message, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, int position) {
        MessageModel model = chatList.get(position);
        holder.texttosend.setText(model.getMessage());
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    /*
    * Here we have kept ID's of all the child row elements same.
    * But we can also create to different viewHolder classes
    * for different child rows.
    */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView texttosend;

        public ViewHolder(View itemView) {
            super(itemView);
            texttosend = (TextView) itemView.findViewById(R.id.message);
        }
    }
}
