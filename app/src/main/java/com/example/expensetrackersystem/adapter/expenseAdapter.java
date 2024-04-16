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
import com.example.expensetrackersystem.model.expenseModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class expenseAdapter extends RecyclerView.Adapter<expenseAdapter.viewholder> {
    private final Context context;
    private List<expenseModel> expenseModelList = new ArrayList<>();

    public expenseAdapter(Context context, List<expenseModel> expenseModelList) {
        this.context = context;
        this.expenseModelList = expenseModelList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_expense_item, parent, false);
        return new viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        expenseModel model = expenseModelList.get(position);
        holder.tv_expenseAmount.setText("KSH "+model.getAmount());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(model.getDate()));
        String formattedDate = DateFormat.format("dd/MM/yyyy", calendar).toString();

        holder.tv_expenseDate.setText(formattedDate);
        holder.tv_expenseJob.setText(model.getType());
    }

    @Override
    public int getItemCount() {
        return expenseModelList.size();
    }

    static class viewholder extends RecyclerView.ViewHolder {
        TextView tv_expenseJob, tv_expenseAmount, tv_expenseDate;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            tv_expenseAmount = itemView.findViewById(R.id.tv_expenseAmount);
            tv_expenseJob = itemView.findViewById(R.id.tv_expenseJob);
            tv_expenseDate = itemView.findViewById(R.id.tv_expenseDate);
        }
    }
}
