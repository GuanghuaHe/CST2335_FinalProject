package com.example.guanghuahe.cst2335_finalmilestone1.CBCNews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.database.NewsReaderDbHelper;
import com.example.guanghuahe.cst2335_finalmilestone1.R;

public class StatisticsActivity extends AppCompatActivity {

    TextView statisticTextView;

    NewsReaderDbHelper newsReaderDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbc_statistics);
        initData();
        initViews();
    }

    private void initViews() {
        statisticTextView = findViewById(R.id.statistics);
        statisticTextView.setText(getStatisticText());
    }

    String getStatisticText(){
        String result = "";
        result += "Number of articles saved : " + newsReaderDbHelper.getStoryCount() + "\n";
        return  result;
    }

    private void initData() {
        newsReaderDbHelper = new NewsReaderDbHelper(StatisticsActivity.this);
    }

    @Override
    protected void onDestroy() {
        newsReaderDbHelper.close();
        super.onDestroy();
    }
}
