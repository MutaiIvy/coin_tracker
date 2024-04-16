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

import com.example.expensetrackersystem.DatabaseHandlerGoals;
import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.model.goalsModel;

import java.util.Calendar;
import java.util.List;

public class setgoalsAdapter2 extends RecyclerView.Adapter<setgoalsAdapter2.viewholder> {

    private final Context context;
    private final List<goalsModel> goalsModelList;

    private final DatabaseHandlerGoals databaseHandler;

    public setgoalsAdapter2(Context context, List<goalsModel> goalsModelList, DatabaseHandlerGoals databaseHandler) {
        this.context = context;
        this.goalsModelList = goalsModelList;
        this.databaseHandler = databaseHandler;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_set_goals2, parent, false);
        return new viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        goalsModel model = goalsModelList.get(position);
        holder.tv_goalAmount.setText("KSH " + model.getAmount());
        holder.tv_goalType.setText(model.getType());
        holder.tv_goalNote.setText(model.getNote());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(model.getDate()));
        String formattedDate = DateFormat.format("dd/MM/yyyy", calendar).toString();

        holder.tv_goalDate.setText(formattedDate);

        holder.itemView.setOnClickListener(v -> showGoalDialog(context, model));
    }

    public void showGoalDialog(Context context, goalsModel model) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final View customLayout = LayoutInflater.from(context).inflate(R.layout.goals_add_item, null);
        EditText et_goal = customLayout.findViewById(R.id.et_goalsAmount);
        EditText et_type = customLayout.findViewById(R.id.et_goalsType);
        EditText et_note = customLayout.findViewById(R.id.et_goalsNote);

        et_goal.setText(model.getAmount());
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
            String amount = et_goal.getText().toString();
            String type = et_type.getText().toString();
            String note = et_note.getText().toString();
            long date = System.currentTimeMillis();

            if (amount.isEmpty()) {
                et_goal.setError("Empty amount");
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
        return goalsModelList.size();
    }

    static class viewholder extends RecyclerView.ViewHolder {
        private final TextView tv_goalDate;
        private final TextView tv_goalType;
        private final TextView tv_goalNote;
        private final TextView tv_goalAmount;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            tv_goalDate = itemView.findViewById(R.id.tv_goalDate);
            tv_goalType = itemView.findViewById(R.id.tv_goalType);
            tv_goalNote = itemView.findViewById(R.id.tv_goalNote);
            tv_goalAmount = itemView.findViewById(R.id.tv_goalAmount);

        }
    }
}