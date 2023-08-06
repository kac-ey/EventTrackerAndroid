package com.zybooks.eventtracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ModelViewHolder> {

    Context context;
    ArrayList<EventModel> eventModelArrayList = new ArrayList<>();
    SQLiteDatabase db;

    public MyAdapter(Context context, int recyclerview, ArrayList<EventModel> eventModelArrayList, SQLiteDatabase db) {
        this.context = context;
        this.eventModelArrayList = eventModelArrayList;
        this.db = db;
    }

    @NonNull
    @Override
    public MyAdapter.ModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview, null);
        return new ModelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ModelViewHolder holder, int position) {
        final EventModel eventModel = eventModelArrayList.get(position);
        holder.title.setText(eventModel.getTitle());
        holder.date.setText(eventModel.getDate());
        holder.startTime.setText(eventModel.getStartTime());
        holder.endTime.setText(eventModel.getEndTime());
    }

    @Override
    public int getItemCount() {
        return eventModelArrayList.size();
    }

    public class ModelViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        Button date, startTime, endTime;

        public ModelViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.eventTitle);
            date = (Button) itemView.findViewById(R.id.dateButton);
            startTime = (Button) itemView.findViewById(R.id.startTimeButton);
            endTime = (Button) itemView.findViewById(R.id.endTimeButton);
        }
    }
}
