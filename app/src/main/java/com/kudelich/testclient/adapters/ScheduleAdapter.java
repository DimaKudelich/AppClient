package com.kudelich.testclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kudelich.testclient.R;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.CustomViewHolder> {
    Context context;
    String[][]classes;

    public ScheduleAdapter(Context context,String[][]classes){
        this.context = context;
        this.classes = classes;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_for_schedule,parent,false);
        return new ScheduleAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.dayOfWeek.setText(classes[position][0]);
        holder.subject.setText(classes[position][1]);
        holder.type.setText(classes[position][2]);
        holder.classroom.setText(classes[position][3]);
        holder.startTime.setText(classes[position][5]+" -");
        holder.endTime.setText(classes[position][4]);
    }

    @Override
    public int getItemCount() {
        return classes.length;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout scheduleRow;
        TextView dayOfWeek;
        TextView subject;
        TextView type;
        TextView classroom;
        TextView startTime;
        TextView endTime;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            scheduleRow = itemView.findViewById(R.id.schedule_row);
            dayOfWeek = itemView.findViewById(R.id.dayOfWeek);
            subject = itemView.findViewById(R.id.subject);
            type = itemView.findViewById(R.id.type);
            classroom = itemView.findViewById(R.id.classroom);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
        }
    }
}