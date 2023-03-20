package com.example.alejandro_luna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    ArrayList<String> exercises;

    // data is passed into the constructor
    RecyclerViewAdapter(Context context, ArrayList<String> exercises) {
        this.mInflater = LayoutInflater.from(context);
        this.exercises = exercises;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int
            position) {
        holder.rowName.setText(exercises.get(position));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return exercises.size();
    }

    // stores and recycles views as they are scrolled offscreen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView rowName;
        ImageView rowImage;

        ViewHolder(View itemView) {
            super(itemView);
            rowName = itemView.findViewById(R.id.exercise);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return exercises.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
