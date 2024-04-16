package com.example.expensetrackersystem;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetrackersystem.model.goalsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class PieChartGoals extends AppCompatActivity {

    private final List<String> xData = new ArrayList<>();

    ArrayList pieEntries;

    private DatabaseHandlerGoals databaseHandlerGoals;

    HashMap<String, String> map;

    PieChartView pieChartView;
    List<SliceValue> pieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart_goals);

        pieChartView = findViewById(R.id.chart);

        databaseHandlerGoals = new DatabaseHandlerGoals(PieChartGoals.this);

        addData();
        getEntries();


        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("Goals").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);

    }

    private void addData() {
        List<goalsModel> goalsModelList = databaseHandlerGoals.getAllIncome();

        for (goalsModel model : goalsModelList) {
            xData.add(model.getType());
        }

        map = new HashMap<>();
        for (goalsModel model : goalsModelList) {
            int amount = Integer.parseInt(model.getAmount());
            if (map.containsKey(model.getType())) {
                int a = Integer.parseInt(Objects.requireNonNull(map.get(model.getType())));
                map.put(model.getType(), String.valueOf(a + amount));
            } else {
                map.put(model.getType(), model.getAmount());
            }
        }
    }

    private void getEntries() {
        pieEntries = new ArrayList<>();
        int i = 0;

        pieData = new ArrayList<>();

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.MAGENTA);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        colors.add(Color.GREEN);

        for (String type : xData) {
            pieData.add(new SliceValue(Float.parseFloat(Objects.requireNonNull(map.get(type))), colors.get(i % 5)).setLabel(type));
            i++;
        }
    }

}
