package com.example.expensetrackersystem.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.model.goalsModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class setgoalsAdapter extends RecyclerView.Adapter<setgoalsAdapter.viewholder> {
    private final Context context;
    private List<goalsModel> goalsModelList = new ArrayList<>();

    public setgoalsAdapter(Context context, List<goalsModel> goalsModelList) {
        this.context = context;
        this.goalsModelList = goalsModelList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_set_goals, parent, false);
        return new viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        goalsModel model = goalsModelList.get(position);
        holder.tv_goalAmount.setText("KSH "+model.getAmount());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(model.getDate()));
        String formattedDate = DateFormat.format("dd/MM/yyyy", calendar).toString();

        holder.tv_goalDate.setText(formattedDate);
        holder.tv_goalJob.setText(model.getType());
    }

    @Override
    public int getItemCount() {
        return goalsModelList.size();
    }

    static class viewholder extends RecyclerView.ViewHolder {
        TextView tv_goalJob, tv_goalAmount, tv_goalDate;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            tv_goalAmount = itemView.findViewById(R.id.tv_goalAmount);
            tv_goalJob = itemView.findViewById(R.id.tv_goalJob);
            tv_goalDate = itemView.findViewById(R.id.tv_goalDate);
        }
    }
}