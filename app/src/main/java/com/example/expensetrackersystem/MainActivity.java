package com.example.expensetrackersystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.expensetrackersystem.fragments.Dashboard;
import com.example.expensetrackersystem.fragments.Expense;
import com.example.expensetrackersystem.fragments.Income;
import com.example.expensetrackersystem.fragments.createbudget;
import com.example.expensetrackersystem.fragments.setgoals;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private final Dashboard dashboardFragment = new Dashboard();
    private final Expense expenseFragment = new Expense();
    private final Income incomeFragment = new Income();
    private final createbudget createbudgetFragment = new createbudget();
    private final setgoals setgoalsFragment = new setgoals();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flFragment, dashboardFragment);
        transaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.income) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, incomeFragment);
            transaction.commit();
            return true;

        } else if (itemId == R.id.dashboard) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, dashboardFragment);
            transaction.commit();
            return true;

        } else if (itemId == R.id.expense) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, expenseFragment);
            transaction.commit();
            return true;

        } else if (itemId == R.id.createbudget) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, createbudgetFragment);
            transaction.commit();
            return true;

        } else if (itemId == R.id.setgoals) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, setgoalsFragment);
            transaction.commit();
            return true;
        }
        return false;
    }
}
