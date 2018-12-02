package com.example.guanghuahe.cst2335_finalmilestone1.CBCNews;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.database.NewsReaderDbHelper;
import com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.rssparser.Story;
import com.example.guanghuahe.cst2335_finalmilestone1.R;


public class DetailActivity extends AppCompatActivity {

    static String DETAIL_STORY_EXTRA = "detail_story:extra";
    /**
     * Database helper class
     */
    NewsReaderDbHelper newsReaderDbHelper;
    /**
     * current preview story
     */
    Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbc_detail);
        initData();
        initViews();
    }

    void initData(){
        newsReaderDbHelper = new NewsReaderDbHelper(this);
        story = getIntent().getParcelableExtra(DETAIL_STORY_EXTRA);
    }

    void initViews(){
        Spanned description ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            description = Html.fromHtml(story.getDescription(), Html.FROM_HTML_MODE_COMPACT);
        } else {
            description = Html.fromHtml(story.getDescription());
        }
        Locale.setDefault(Locale.getDefault());
        Date date = story.getPubDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        final String pubDateString = sdf.format(date);
        final String categories = story.getCategories().toString().substring(1,story.getCategories().toString().length()-1);
        ((TextView) findViewById(R.id.title)).setText(story.getTitle());
        ((TextView) findViewById(R.id.description)).setText(description);
       // WebView webview = ((WebView) findViewById(R.id.description));
        //webview.getSettings().setJavaScriptEnabled(true);
        //webview.loadDataWithBaseURL("", story.getDescription(), "text/html", "UTF-8", "");
        ((TextView) findViewById(R.id.pubDate)).setText(pubDateString);
        ((TextView) findViewById(R.id.categories)).setText(categories);
        findViewById(R.id.link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWebBrowser(story.getLink());
            }
        });
    }

    @Override
    protected void onDestroy() {
        newsReaderDbHelper.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cbc_detail_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                newsReaderDbHelper.insertStory(story);
                Toast.makeText(this, "save  story", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }


    /**
     * Call method should go to a web page Intent with the link text sent in the Intent.
     *
     * @param  url  an absolute URL giving the base location of story
     */

    void startWebBrowser(String  url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }




}
