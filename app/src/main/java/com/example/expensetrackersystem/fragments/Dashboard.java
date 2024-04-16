package com.example.expensetrackersystem.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackersystem.DatabaseHandler;
import com.example.expensetrackersystem.DatabaseHandlerExpense;
import com.example.expensetrackersystem.DatabaseHandlercreatebudget;
import com.example.expensetrackersystem.DatabaseHandlerGoals;
import com.example.expensetrackersystem.R;

import com.example.expensetrackersystem.adapter.createbudgetAdapter;
import com.example.expensetrackersystem.adapter.expenseAdapter;
import com.example.expensetrackersystem.adapter.incomeAdapter;
import com.example.expensetrackersystem.adapter.setgoalsAdapter;
import com.example.expensetrackersystem.model.expenseModel;
import com.example.expensetrackersystem.model.incomeModel;
import com.example.expensetrackersystem.model.goalsModel;
import com.example.expensetrackersystem.model.createbudgetModel;
import com.example.expensetrackersystem.tips;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Dashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static Dashboard newInstance(String param1, String param2) {
        Dashboard fragment = new Dashboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView rv_income, rv_expense, rv_budget, rv_goal;
    private TextView tv_income, tv_expense, tv_budget, tv_goals;

    FloatingActionButton mAddFab, mAddIncomeFab, mAddExpenseFab, mAddBudgetFab, mAddGoalFab, mAddTipsFab;
    TextView addIncomeText, addExpenseText, addBudgetText, addGoalText, wantTipsText;
    Boolean isAllFabsVisible;

    private incomeAdapter incomeAdapter;
    private expenseAdapter expenseAdapter;
    private createbudgetAdapter createbudgetAdapter;
    private setgoalsAdapter setgoalsAdapter;

    private List<incomeModel> incomeModelList = new ArrayList<>();
    private List<expenseModel> expenseModelList = new ArrayList<>();
    private List<createbudgetModel> createbudgetModelList = new ArrayList<>();
    private List<goalsModel> goalsModelList = new ArrayList<>();

    private DatabaseHandler databaseHandler;
    private DatabaseHandlerExpense databaseHandlerExpense;
    private DatabaseHandlercreatebudget databaseHandlercreatebudget;
    private DatabaseHandlerGoals databaseHandlerGoals;

    private String totalIncome, totalExpense, totalBudget, totalGoals;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        init(view);

        //TextView tvTip = view.findViewById(R.id.want_tips_text);
        //tvTip.setOnClickListener(v -> {
        //if (getActivity() != null) {
        // Start the tips activity
        //Intent intent = new Intent(getActivity(), tips.class);
        //startActivity(intent);
        // }
        //});

        databaseHandler = new DatabaseHandler(getContext());
        databaseHandlerExpense = new DatabaseHandlerExpense(getContext());
        databaseHandlercreatebudget = new DatabaseHandlercreatebudget(getContext());
        databaseHandlerGoals = new DatabaseHandlerGoals(getContext());
        fillIncomeModel();
        fillExpenseModel();
        fillBudgetModel();
        fillGoalsModel();

        mAddFab.setOnClickListener(view1 -> {
            if (!isAllFabsVisible) {
                mAddIncomeFab.show();
                mAddExpenseFab.show();
                mAddBudgetFab.show();
                mAddGoalFab.show();
                mAddTipsFab.show();
                addExpenseText.setVisibility(View.VISIBLE);
                addIncomeText.setVisibility(View.VISIBLE);
                addBudgetText.setVisibility(View.VISIBLE);
                addGoalText.setVisibility(View.VISIBLE);
                wantTipsText.setVisibility(View. VISIBLE);

                isAllFabsVisible = true;
            } else {
                mAddIncomeFab.hide();
                mAddExpenseFab.hide();
                mAddBudgetFab.hide();
                mAddGoalFab.hide();
                addExpenseText.setVisibility(View.GONE);
                addIncomeText.setVisibility(View.GONE);
                addBudgetText.setVisibility(View.GONE);
                addGoalText.setVisibility(View.GONE);
                wantTipsText.setVisibility(View.GONE);

                isAllFabsVisible = false;
            }
        });

        mAddIncomeFab.setOnClickListener(view12 -> showIncomeDialog());

        mAddExpenseFab.setOnClickListener(view13 -> showExpenseDialog());

        mAddBudgetFab.setOnClickListener(view14 -> showBudgetDialog());

        mAddGoalFab.setOnClickListener(view15 -> showGoalDialog());

        mAddTipsFab.setOnClickListener(view16 -> {
            Context context = getContext();
            if (context != null) {
                Intent intent = new Intent(context, tips.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void fillExpenseModel() {
        expenseModelList = databaseHandlerExpense.getAllIncome();

        int total = 0;
        for (expenseModel model : expenseModelList) {
            total += Integer.parseInt(model.getAmount());
        }
        totalExpense = String.valueOf(total);
        tv_expense.setText("KSH " + totalExpense);

        expenseAdapter = new expenseAdapter(getContext(), expenseModelList);
        rv_expense.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_expense.setHasFixedSize(true);

        rv_expense.setAdapter(expenseAdapter);
    }

    @SuppressLint("SetTextI18n")
    private void fillIncomeModel() {
        incomeModelList = databaseHandler.getAllIncome();

        int total = 0;
        for (incomeModel model : incomeModelList) {
            total += Integer.parseInt(model.getAmount());
        }
        totalIncome = String.valueOf(total);
        tv_income.setText("KSH " + totalIncome);

        incomeAdapter = new incomeAdapter(getContext(), incomeModelList);
        rv_income.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_income.setHasFixedSize(true);

        rv_income.setAdapter(incomeAdapter);

    }

    @SuppressLint("SetTextI18n")
    private void fillBudgetModel() {
        createbudgetModelList = databaseHandlercreatebudget.getAllIncome();

        int total = 0;
        for (createbudgetModel model : createbudgetModelList) {
            total += Integer.parseInt(model.getAmount());
        }
        totalBudget = String.valueOf(total);
        tv_budget.setText("KSH " + totalBudget);

        createbudgetAdapter = new createbudgetAdapter(getContext(), createbudgetModelList);
        rv_budget.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_budget.setHasFixedSize(true);

        rv_budget.setAdapter(createbudgetAdapter);

    }

    @SuppressLint("SetTextI18n")
    private void fillGoalsModel() {
        goalsModelList = databaseHandlerGoals.getAllIncome();

        int total = 0;
        for (goalsModel model : goalsModelList) {
            total += Integer.parseInt(model.getAmount());
        }
        totalGoals = String.valueOf(total);
        tv_goals.setText("KSH " + totalGoals);

        setgoalsAdapter = new setgoalsAdapter(getContext(), goalsModelList);
        rv_goal.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_goal.setHasFixedSize(true);

        rv_goal.setAdapter(setgoalsAdapter);

    }

    @SuppressLint("SetTextI18n")
    private void showIncomeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final View customLayout = getLayoutInflater().inflate(R.layout.income_add_litem, null);
        EditText et_income = customLayout.findViewById(R.id.et_incomeAmount);
        EditText et_type = customLayout.findViewById(R.id.et_incomeType);
        EditText et_note = customLayout.findViewById(R.id.et_incomeNote);

        Button btn_save = customLayout.findViewById(R.id.btn_save);
        Button btn_cancel = customLayout.findViewById(R.id.btn_cancel);

        builder.setView(customLayout);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

        btn_cancel.setOnClickListener(v -> alertDialog.dismiss());

        btn_save.setOnClickListener(v -> {
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
                databaseHandler.addData(amount, type, note, String.valueOf(date));
                alertDialog.dismiss();
                fillIncomeModel();
            }

        });

    }

    private void showExpenseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final View customLayout = getLayoutInflater().inflate(R.layout.expense_add_item, null);
        EditText et_expense = customLayout.findViewById(R.id.et_expenseAmount);
        EditText et_type = customLayout.findViewById(R.id.et_expenseType);
        EditText et_note = customLayout.findViewById(R.id.et_expenseNote);

        Button btn_save = customLayout.findViewById(R.id.btn_save);
        Button btn_cancel = customLayout.findViewById(R.id.btn_cancel);

        builder.setView(customLayout);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

        btn_cancel.setOnClickListener(v -> alertDialog.dismiss());

        btn_save.setOnClickListener(v -> {
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
                int expenseAmount = Integer.parseInt(amount);
                int currentIncome = Integer.parseInt(totalIncome);
                int remainingIncome = currentIncome - expenseAmount;

                // Ensure total income becomes negative if expense exceeds income
                if (remainingIncome < 0) {
                    totalIncome = String.valueOf(-Math.abs(remainingIncome));
                } else {
                    totalIncome = String.valueOf(remainingIncome);
                }

                // Update the total income TextView
                tv_income.setText("KSH " + totalIncome);

                // Add expense to database
                databaseHandlerExpense.addData(amount, type, note, String.valueOf(date));
                alertDialog.dismiss();
                fillExpenseModel();
            }
        });
    }


        private void showBudgetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final View customLayout = getLayoutInflater().inflate(R.layout.budget_add_item, null);
        EditText et_budget = customLayout.findViewById(R.id.et_budgetAmount);
        EditText et_type = customLayout.findViewById(R.id.et_budgetType);
        EditText et_note = customLayout.findViewById(R.id.et_budgetNote);

        Button btn_save = customLayout.findViewById(R.id.btn_save);
        Button btn_cancel = customLayout.findViewById(R.id.btn_cancel);

        builder.setView(customLayout);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

        btn_cancel.setOnClickListener(v -> alertDialog.dismiss());

        btn_save.setOnClickListener(v -> {
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
                databaseHandlercreatebudget.addData(amount, type, note, String.valueOf(date));
                alertDialog.dismiss();
                fillBudgetModel();
            }

        });

    }

    private void showGoalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final View customLayout = getLayoutInflater().inflate(R.layout.goals_add_item, null);
        EditText et_goal = customLayout.findViewById(R.id.et_goalsAmount);
        EditText et_type = customLayout.findViewById(R.id.et_goalsType);
        EditText et_note = customLayout.findViewById(R.id.et_goalsNote);

        Button btn_save = customLayout.findViewById(R.id.btn_save);
        Button btn_cancel = customLayout.findViewById(R.id.btn_cancel);

        builder.setView(customLayout);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

        btn_cancel.setOnClickListener(v -> alertDialog.dismiss());

        btn_save.setOnClickListener(v -> {
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
                databaseHandlerGoals.addData(amount, type, note, String.valueOf(date));
                alertDialog.dismiss();
                fillGoalsModel();
            }

        });
    }

    private void init(View root) {
        rv_income = root.findViewById(R.id.rv_income);
        rv_expense = root.findViewById(R.id.rv_expense);
        rv_budget = root.findViewById(R.id.rv_budget);
        rv_goal = root.findViewById(R.id.rv_goal);

        tv_income = root.findViewById(R.id.tv_income);
        tv_expense = root.findViewById(R.id.tv_expense);
        tv_budget = root.findViewById(R.id.tv_budget);
        tv_goals = root.findViewById(R.id.tv_goals);

        tv_income.setText("KSH 11000");
        tv_expense.setText("KSH 8000");
        tv_budget .setText("KSH 8000");
        tv_goals.setText("KSH 8000");

        mAddFab = root.findViewById(R.id.add_fab);
        mAddIncomeFab = root.findViewById(R.id.add_income_fab);
        mAddExpenseFab = root.findViewById(R.id.add_expense_fab);
        mAddBudgetFab = root.findViewById(R.id.add_budget_fab);
        mAddGoalFab = root.findViewById(R.id.add_goal_fab);
        mAddTipsFab = root.findViewById(R.id.add_tips_fab);

        addIncomeText = root.findViewById(R.id.add_income_text);
        addExpenseText = root.findViewById(R.id.add_expense_text);
        addBudgetText = root.findViewById(R.id.add_budget_text);
        addGoalText = root.findViewById(R.id.add_goal_text);
        wantTipsText = root.findViewById(R.id.want_tips_text);

        mAddIncomeFab.setVisibility(View.GONE);
        mAddExpenseFab.setVisibility(View.GONE);
        mAddBudgetFab.setVisibility(View.GONE);
        mAddGoalFab.setVisibility(View.GONE);
        mAddTipsFab.setVisibility(View.GONE);
        addIncomeText.setVisibility(View.GONE);
        addExpenseText.setVisibility(View.GONE);
        addBudgetText.setVisibility(View.GONE);
        addGoalText.setVisibility(View.GONE);
        wantTipsText.setVisibility(View.GONE);

        isAllFabsVisible = false;

    }
}
