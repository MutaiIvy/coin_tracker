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

import com.example.expensetrackersystem.DatabaseHandler;
import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.model.incomeModel;

import java.util.Calendar;
import java.util.List;

public class incomeAdapter2 extends RecyclerView.Adapter<incomeAdapter2.viewholder> {

    private final Context context;
    private final List<incomeModel> incomeModelList;

    private final DatabaseHandler databaseHandler;

    public incomeAdapter2(Context context, List<incomeModel> incomeModelList, DatabaseHandler databaseHandler) {
        this.context = context;
        this.incomeModelList = incomeModelList;
        this.databaseHandler = databaseHandler;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_income_item2, parent, false);
        return new viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        incomeModel model = incomeModelList.get(position);
        holder.tv_incomeAmount.setText("KSH " + model.getAmount());
        holder.tv_incomeType.setText(model.getType());
        holder.tv_incomeNote.setText(model.getNote());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(model.getDate()));
        String formattedDate = DateFormat.format("dd/MM/yyyy", calendar).toString();

        holder.tv_incomeDate.setText(formattedDate);

        holder.itemView.setOnClickListener(v -> showIncomeDialog(context, model));
    }

    public void showIncomeDialog(Context context, incomeModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final View customLayout = LayoutInflater.from(context).inflate(R.layout.income_add_litem, null);
        EditText et_income = customLayout.findViewById(R.id.et_incomeAmount);
        EditText et_type = customLayout.findViewById(R.id.et_incomeType);
        EditText et_note = customLayout.findViewById(R.id.et_incomeNote);

        et_income.setText(model.getAmount());
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
            String amount = et_income.getText().toString();
            String type = et_type.getText().toString();
            String note = et_note.getText().toString();
            long date = System.currentTimeMillis();

            if (amount.isEmpty()) {
                et_income.setError("Empty amount");
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
        return incomeModelList.size();
    }

    static class viewholder extends RecyclerView.ViewHolder {
        private final TextView tv_incomeDate;
        private final TextView tv_incomeType;
        private final TextView tv_incomeNote;
        private final TextView tv_incomeAmount;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            tv_incomeDate = itemView.findViewById(R.id.tv_incomeDate);
            tv_incomeType = itemView.findViewById(R.id.tv_incomeType);
            tv_incomeNote = itemView.findViewById(R.id.tv_incomeNote);
            tv_incomeAmount = itemView.findViewById(R.id.tv_incomeAmount);

        }
    }
}
