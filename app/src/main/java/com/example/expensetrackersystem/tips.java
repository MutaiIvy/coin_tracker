package com.example.expensetrackersystem;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.expensetrackersystem.adapter.CustomAdapter;

import java.util.List;
import java.util.ArrayList;

public class tips extends AppCompatActivity {

    private GridView gridView;
    private List<tipItems> tipItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        gridView = findViewById(R.id.gridViewId);
        tipItems = generatetipItems();

        CustomAdapter adapter = new CustomAdapter(this, tipItems);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = tipItems.get(position).getUrl();
                if (isValidUrl(url)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            }
        });
    }

    private List<tipItems> generatetipItems() {
        List<tipItems> items = new ArrayList<>();
        String[] financialTips = getResources().getStringArray(R.array.financial_tips);
        String[] tipsDescription = getResources().getStringArray(R.array.tips_description);

        String[] urls = getResources().getStringArray(R.array.tips_description);

        int[] imgArray = {R.drawable.imagee1, R.drawable.imagee2, R.drawable.imagee3, R.drawable.imagee4, R.drawable.imagee5, R.drawable.podcast1, R.drawable.podcast2,
                R.drawable.yt1, R.drawable.yt2, R.drawable.yt3, R.drawable.yt4};

        for (int i = 0; i < financialTips.length; i++) {
            items.add(new tipItems(imgArray[i], urls[i]));
        }

        return items;
    }

    private boolean isValidUrl(String url) {
        return url != null && (url.startsWith("http://") || url.startsWith("https://"));
    }
}