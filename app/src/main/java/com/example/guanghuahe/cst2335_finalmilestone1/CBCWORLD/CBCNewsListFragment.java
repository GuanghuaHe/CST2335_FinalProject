package com.example.guanghuahe.cst2335_finalmilestone1.CBCWORLD;

//source:https://www.programcreek.com/java-api-examples/?code=frank-tan/XYZReader/XYZReader-master/XYZReader/src/main/java/com/example/xyzreader/ui/ArticleDetailFragment.java
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CBCNewsListFragment extends Fragment {

    private static final String TAG = "CBCNewsListFragment";
    private static final String KEY_NEWS = "news";

    private RecyclerView mNewsRecyclerView;
    private CardAdapter mAdapter;
    private CBCRSSParser mRSSParser;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String baseURL="https://www.cbc.ca/cmlink/rss-world";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (isNetworkAvailable()) {
            Log.v("HTTP", "Network available");

            if (savedInstanceState != null){
                ArrayList<CBCArticle> newNews = savedInstanceState.getParcelableArrayList(KEY_NEWS);
                CBCNews.get(getActivity()).setArticles(newNews);
            } else {
                new DownloadWebpageTask().execute();
            }

        } else {
            Toast.makeText(getActivity(), "Network not available", Toast.LENGTH_SHORT).show();
            Log.v("HTTP", "Network NOT available");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_news_list,container,false);
        mNewsRecyclerView = (RecyclerView) view.findViewById(R.id.news_recycler_view);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRSSParser = new CBCRSSParser();


         //networkAvailable

        //
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }

            void refreshItems(){
                if(isNetworkAvailable()) {
                    new DownloadWebpageTask().execute();// Load items
                }
                // Load complete
                onItemsLoadComplete();
            }
            void onItemsLoadComplete(){
                updateUI();
                // Update the adapter and notify data set changed
                // Stop refresh animation
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        updateUI();
        return view;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        Log.d("isNetworkAvailable", "network not available");
        return false;
    } // isNetwork

    /**
     * used to download the webpage using the baseURL
     */
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            CBCNews.get(getActivity()).clearArticles();
            try {
                downloadUrl(baseURL);
                return "Web page retrieved";
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        /**
         * displays the results of the AsyncTask
         * @param result
         */
        @Override
        protected void onPostExecute(String result) {
            Log.i("onPostExecute", result);
            updateUI();
        }
    }


    /**
     * downloadURL establishes an HttpUrlConnection given a URL and retrieves the web page content as
     * an InputStream and converts it to a string and returns the value
     * closes the inputStream after it's done
     * conn is closeable
     * @param myurl
     * @throws IOException
     */
    private void downloadUrl(String myurl) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            int response = conn.getResponseCode();
            Log.i("HTTP: downloadUrl: ", "The response is: " + response);
            is = conn.getInputStream();

            mRSSParser.parseXML(getActivity(),is);


        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mNewsTextView;
        private ImageView mImageView;
        private CBCArticle mArticle;


        public CardHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_card, parent, false));
            itemView.setOnClickListener(this);

            mNewsTextView = (TextView) itemView.findViewById(R.id.news_text_view);
            mImageView = (ImageView) itemView.findViewById(R.id.image_view);
        }

        @Override
        public void onClick(View view){
            Intent intent = CBCArticleDetailActivity.newIntent(getActivity(), mArticle.getId());
            startActivity(intent);
        }

        public void bind(CBCArticle article){
            mArticle = article;
            updateImage();
            updateHeadline();
        }

        /**
         * Sets the headLone in the TextView when the button is pressed
         */
        private void updateHeadline() {
            String news = mArticle.getHeadline();
            mNewsTextView.setText(news);
        }

        /**
         * Sets the new image in the ImageView when the user presses
         */
        private void updateImage() {

            String imageName = mArticle.getImageUrl(); //get the image url

            Picasso.with(getContext())
                    .load(imageName)
                    .error(R.drawable.errorimage)
                    .into(mImageView);
        }
    }

    private class CardAdapter extends RecyclerView.Adapter<CardHolder>{
        private List<CBCArticle> mArticles;

        public CardAdapter(List<CBCArticle> articles){
            mArticles = articles;
        }

        public void setArticles(List<CBCArticle> articles){
            mArticles = articles;
        }

        @Override
        public CardHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new CardHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(CardHolder holder, int position){
            CBCArticle article = mArticles.get(position);
            holder.bind(article);
        }

        @Override
        public int getItemCount(){
            return mArticles.size();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CBCNews news = CBCNews.get(getActivity());
        List<CBCArticle> articles = news.getArticles();

        if (mAdapter == null) {
            mAdapter = new CardAdapter(articles);
            mNewsRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setArticles(articles);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.home:
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putParcelableArrayList(KEY_NEWS,CBCNews.get(getActivity()).getArticles());
    }
}
