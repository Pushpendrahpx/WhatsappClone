package com.shareable.whatsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class OurData{
    public static String[] title = new String[]{
            "pushepndrahpx",
            "rahul",
            "pushepndrahpx",
            "rahul",
            "pushepndrahpx",
            "rahul",
            "pushepndrahpx",
            "rahul",
            "pushepndrahpx",
            "rahul",
            "pushepndrahpx",
            "rahul",
            "pushepndrahpx",
            "rahul",
            "pushepndrahpx",
            "rahul"
    };

    public static int[] picturePath = new int[]{
            R.drawable.ic_baseline_message_24,
            R.drawable.ic_baseline_add_24,
            R.drawable.ic_baseline_message_24,
            R.drawable.ic_baseline_add_24,
            R.drawable.ic_baseline_message_24,
            R.drawable.ic_baseline_add_24,
            R.drawable.ic_baseline_message_24,
            R.drawable.ic_baseline_add_24,
            R.drawable.ic_baseline_message_24,
            R.drawable.ic_baseline_add_24,
            R.drawable.ic_baseline_message_24,
            R.drawable.ic_baseline_add_24,
            R.drawable.ic_baseline_message_24,
            R.drawable.ic_baseline_add_24,
            R.drawable.ic_baseline_message_24,
            R.drawable.ic_baseline_add_24
    };


}


public class ChatListAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_chat_list_item,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ListViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return OurData.title.length;
    }

    private class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView;
        private TextView mTextView;

        private ListViewHolder(View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.chatListViewImage);
            mTextView = itemView.findViewById(R.id.chatListViewUserName);

            itemView.setOnClickListener(this);


        }

        public void bindView(int position){
            mTextView.setText(OurData.title[position]);
            mImageView.setImageResource(OurData.picturePath[position]);
        }

        public void onClick(View view){

        }


    }
}
