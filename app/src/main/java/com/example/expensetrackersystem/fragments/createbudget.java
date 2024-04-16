package com.example.expensetrackersystem.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackersystem.DatabaseHandlercreatebudget;
import com.example.expensetrackersystem.PieChartBudget;
import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.adapter.createbudgetAdapter2;
import com.example.expensetrackersystem.model.createbudgetModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link createbudget#newInstance} factory method to
 * create an instance of this fragment.
 */
public class createbudget extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public createbudget() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment createbudget.
     */
    // TODO: Rename and change types and number of parameters
    public static createbudget newInstance(String param1, String param2) {
        createbudget fragment = new createbudget();
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

    private TextView tvBudget;
    private RecyclerView rv_budget;
    private List<createbudgetModel> createbudgetModelList = new ArrayList<>();
    private DatabaseHandlercreatebudget databaseHandlercreatebudget;
    private String totalBudget;

    private ImageView iv_budgetPie;

    private createbudgetAdapter2 createbudgetAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_createbudget, container, false);

        init(view);

        iv_budgetPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PieChartBudget.class));
            }
        });

        fillBudget();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void fillBudget() {
        createbudgetModelList = databaseHandlercreatebudget.getAllIncome();
        int total = 0;
        for (createbudgetModel model : createbudgetModelList) {
            total += Integer.parseInt(model.getAmount());
        }
        totalBudget = String.valueOf(total);
        tvBudget.setText("KSH " + totalBudget);

        createbudgetAdapter = new createbudgetAdapter2(getContext(), createbudgetModelList, databaseHandlercreatebudget);
        rv_budget.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_budget.setHasFixedSize(true);

        rv_budget.setAdapter(createbudgetAdapter);
    }

    private void init(View view) {
        tvBudget = view.findViewById(R.id.tvBudget);
        rv_budget = view.findViewById(R.id.rv_budget);
        iv_budgetPie = view.findViewById(R.id.iv_budgetPie);
        databaseHandlercreatebudget = new DatabaseHandlercreatebudget(getContext());
    }
}