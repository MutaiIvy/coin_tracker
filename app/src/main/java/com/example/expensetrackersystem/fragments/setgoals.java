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

import com.example.expensetrackersystem.DatabaseHandlerGoals;
import com.example.expensetrackersystem.PieChartGoals;
import com.example.expensetrackersystem.R;
import com.example.expensetrackersystem.adapter.setgoalsAdapter2;
import com.example.expensetrackersystem.model.goalsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link setgoals#newInstance} factory method to
 * create an instance of this fragment.
 */
public class setgoals extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public setgoals() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment setgoals.
     */
    // TODO: Rename and change types and number of parameters
    public static setgoals newInstance(String param1, String param2) {
        setgoals fragment = new setgoals();
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

    private TextView tv_goal;
    private RecyclerView rv_goal;
    private List<goalsModel> goalsModelList = new ArrayList<>();
    private DatabaseHandlerGoals databaseHandlerGoals;
    private String totalGoals;

    private setgoalsAdapter2 setgoalsAdapter;

    private ImageView iv_goalPie;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setgoals, container, false);

        init(view);

        iv_goalPie.setOnClickListener(v -> startActivity(new Intent(getContext(), PieChartGoals.class)));

        fillGoals();

        return view;
    }

    @SuppressLint("SetTextI18n")
    public void fillGoals() {
        goalsModelList = databaseHandlerGoals.getAllIncome();
        int total = 0;
        for (goalsModel model : goalsModelList) {
            total += Integer.parseInt(model.getAmount());
        }
        totalGoals = String.valueOf(total);
        tv_goal.setText("KSH " + totalGoals);

        setgoalsAdapter = new setgoalsAdapter2(getContext(), goalsModelList, databaseHandlerGoals);
        rv_goal.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_goal.setHasFixedSize(true);

        rv_goal.setAdapter(setgoalsAdapter);
    }

    private void init(View view) {
        tv_goal = view.findViewById(R.id.tv_goal);
        rv_goal = view.findViewById(R.id.rv_goal);
        iv_goalPie = view.findViewById(R.id.iv_goalPie);
        databaseHandlerGoals = new DatabaseHandlerGoals(getContext());
    }

}