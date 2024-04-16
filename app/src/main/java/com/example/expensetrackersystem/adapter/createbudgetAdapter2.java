package com.example.expensetrackersystem.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackersystem.DatabaseHandlercreatebudget;
import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.model.createbudgetModel;

import java.util.Calendar;
import java.util.List;

public class createbudgetAdapter2 extends RecyclerView.Adapter<createbudgetAdapter2.viewholder> {
    private final Context context;
    private final List<createbudgetModel> createbudgetModelList;
    private final DatabaseHandlercreatebudget databaseHandler;

    public createbudgetAdapter2(Context context, List<createbudgetModel> createbudgetModelList, DatabaseHandlercreatebudget databaseHandler) {
        this.context = context;
        this.createbudgetModelList = createbudgetModelList;
        this.databaseHandler = databaseHandler;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_create_budget2, parent, false);
        return new viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        createbudgetModel model = createbudgetModelList.get(position);
        holder.tv_budgetAmount.setText("KSH "+model.getAmount());
        holder.tv_budgetType.setText(model.getType());
        holder.tv_budgetNote.setText(model.getNote());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(model.getDate()));
        String formattedDate = DateFormat.format("dd/MM/yyyy", calendar).toString();

        holder.tv_budgetDate.setText(formattedDate);

        holder.itemView.setOnClickListener(v -> showBudgetDialog(context, model));
    }

    public void showBudgetDialog(Context context, createbudgetModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final View customLayout = LayoutInflater.from(context).inflate(R.layout.budget_add_item, null);
        EditText et_budget = customLayout.findViewById(R.id.et_budgetAmount);
        EditText et_type = customLayout.findViewById(R.id.et_budgetType);
        EditText et_note = customLayout.findViewById(R.id.et_budgetNote);

        et_budget.setText(model.getAmount());
        et_type.setText(model.getType());
        et_note.setText(model.getNote());

        Button btn_save = customLayout.findViewById(R.id.btn_save);
        Button btn_cancel = customLayout.findViewById(R.id.btn_cancel);

        builder.setView(customLayout);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

        btn_cancel.setOnClickListener(v -> alertDialog.dismiss());

        btn_save.setOnClickListener(v -> {
            String id = model.getId();
            String amount = et_budget.getText().toString();
            String type = et_type.getText().toString();
            String note = et_note.getText().toString();
            long date = System.currentTimeMillis();

            if (amount.isEmpty()) {
                et_budget.setError("Empty amount");
            } else if (type.isEmpty()) {
                et_type.setError("Empty Type");
            } else if (note.isEmpty()) {
                et_note.setError("Empty note");
            } else {
                databaseHandler.update(id, amount, type, note, String.valueOf(date));
                alertDialog.dismiss();
            }

        });

    }

    @Override
    public int getItemCount() {
        return createbudgetModelList.size();
    }

    static class viewholder extends RecyclerView.ViewHolder {
        private final TextView tv_budgetDate;
        private final TextView tv_budgetType;
        private final TextView tv_budgetNote;
        private final TextView tv_budgetAmount;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            tv_budgetDate = itemView.findViewById(R.id.tv_budgetDate);
            tv_budgetType = itemView.findViewById(R.id.tv_budgetType);
            tv_budgetNote = itemView.findViewById(R.id.tv_budgetNote);
            tv_budgetAmount = itemView.findViewById(R.id.tv_budgetAmount);

        }
    }
}