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

import com.example.expensetrackersystem.DatabaseHandlerExpense;
import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.model.expenseModel;

import java.util.Calendar;
import java.util.List;

public class expenseAdapter2 extends RecyclerView.Adapter<expenseAdapter2.viewholder> {
    private final Context context;
    private final List<expenseModel> expenseModelList;
    private final DatabaseHandlerExpense databaseHandler;

    public expenseAdapter2(Context context, List<expenseModel> expenseModelList, DatabaseHandlerExpense databaseHandler) {
        this.context = context;
        this.expenseModelList = expenseModelList;
        this.databaseHandler = databaseHandler;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_expense_item2, parent, false);
        return new viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        expenseModel model = expenseModelList.get(position);
        holder.tv_expenseAmount.setText("KSH "+model.getAmount());
        holder.tv_expenseType.setText(model.getType());
        holder.tv_expenseNote.setText(model.getNote());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(model.getDate()));
        String formattedDate = DateFormat.format("dd/MM/yyyy", calendar).toString();

        holder.tv_expenseDate.setText(formattedDate);

        holder.itemView.setOnClickListener(v -> showExpenseDialog(context, model));
    }

    public void showExpenseDialog(Context context, expenseModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final View customLayout = LayoutInflater.from(context).inflate(R.layout.expense_add_item, null);
        EditText et_expense = customLayout.findViewById(R.id.et_expenseAmount);
        EditText et_type = customLayout.findViewById(R.id.et_expenseType);
        EditText et_note = customLayout.findViewById(R.id.et_expenseNote);

        et_expense.setText(model.getAmount());
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
            String amount = et_expense.getText().toString();
            String type = et_type.getText().toString();
            String note = et_note.getText().toString();
            long date = System.currentTimeMillis();

            if (amount.isEmpty()) {
                et_expense.setError("Empty amount");
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
        return expenseModelList.size();
    }

    static class viewholder extends RecyclerView.ViewHolder {
        private final TextView tv_expenseDate;
        private final TextView tv_expenseType;
        private final TextView tv_expenseNote;
        private final TextView tv_expenseAmount;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            tv_expenseDate = itemView.findViewById(R.id.tv_expenseDate);
            tv_expenseType = itemView.findViewById(R.id.tv_expenseType);
            tv_expenseNote = itemView.findViewById(R.id.tv_expenseNote);
            tv_expenseAmount = itemView.findViewById(R.id.tv_expenseAmount);

        }
    }
}
