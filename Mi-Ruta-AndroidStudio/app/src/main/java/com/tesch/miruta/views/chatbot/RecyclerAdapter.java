package com.tesch.miruta.views.chatbot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tesch.miruta.R;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MessageViewHolder> {
    private ArrayList<MessageResult> messages;
    private OnMessageClickListener onMessageClickListener;

    public RecyclerAdapter(ArrayList<MessageResult> messages, OnMessageClickListener onMessageClickListener) {
        this.messages = messages;
        this.onMessageClickListener = onMessageClickListener;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout sentLayout;
        private LinearLayout receivedLayout;
        private TextView sentText;
        private TextView receivedText;

        public MessageViewHolder(final View itemView) {
            super(itemView);
            sentLayout = itemView.findViewById(R.id.sentLayout);
            receivedLayout = itemView.findViewById(R.id.receivedLayout);
            sentText = itemView.findViewById(R.id.sentTextView);
            receivedText = itemView.findViewById(R.id.receivedTextView);

            receivedText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMessageClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onMessageClickListener.onMessageClick(messages.get(position).getMessage());
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_message, parent, false);
        return new MessageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MessageViewHolder holder, int position) {
        String message = messages.get(position).getMessage();
        boolean type = messages.get(position).getType();

        if (type) {
            //If a message is sent
            holder.sentLayout.setVisibility(LinearLayout.VISIBLE);
            holder.sentText.setText(message);
            // Set visibility as GONE to remove the space taken up
            holder.receivedLayout.setVisibility(LinearLayout.GONE);
        } else {
            //Message is received
            holder.receivedLayout.setVisibility(LinearLayout.VISIBLE);
            holder.receivedText.setText(message);
            // Set visibility as GONE to remove the space taken up
            holder.sentLayout.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public interface OnMessageClickListener {
        void onMessageClick(String message);
    }
}
