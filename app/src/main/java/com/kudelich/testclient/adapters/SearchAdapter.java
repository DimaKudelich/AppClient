package com.kudelich.testclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kudelich.testclient.R;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CustomViewHolder> {
    String objects[];
    Context context;
    String putAddress;
    Class aClass;
    long[]allId;

    public SearchAdapter(Context context, String objects[], String putAddress, long[]allId, Class aClass) {
        this.context = context;
        this.objects = objects;
        this.putAddress = putAddress;
        this.aClass = aClass;
        this.allId = allId;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_for_schedule_search,parent,false);
        return new SearchAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        holder.textView.setText(objects[position]);
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, aClass);
                intent.putExtra(putAddress, allId[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.length;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ConstraintLayout mainLayout;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.listItem);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
