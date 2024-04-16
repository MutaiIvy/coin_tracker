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
import com.example.expensetrackersystem.model.createbudgetModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class createbudgetAdapter extends RecyclerView.Adapter<createbudgetAdapter.viewholder> {
    private final Context context;
    private List<createbudgetModel> createbudgetModelList = new ArrayList<>();

    public createbudgetAdapter(Context context, List<createbudgetModel> createbudgetModelList) {
        this.context = context;
        this.createbudgetModelList = createbudgetModelList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_create_budget, parent, false);
        return new viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        createbudgetModel model = createbudgetModelList.get(position);
        holder.tv_budgetAmount.setText("KSH "+model.getAmount());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(model.getDate()));
        String formattedDate = DateFormat.format("dd/MM/yyyy", calendar).toString();

        holder.tv_budgetDate.setText(formattedDate);
        holder.tv_budgetJob.setText(model.getType());
    }

    @Override
    public int getItemCount() {
        return createbudgetModelList.size();
    }

    static class viewholder extends RecyclerView.ViewHolder {
        TextView tv_budgetJob, tv_budgetAmount, tv_budgetDate;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            tv_budgetAmount = itemView.findViewById(R.id.tv_budgetAmount);
            tv_budgetJob = itemView.findViewById(R.id.tv_budgetJob);
            tv_budgetDate = itemView.findViewById(R.id.tv_budgetDate);
        }
    }
}