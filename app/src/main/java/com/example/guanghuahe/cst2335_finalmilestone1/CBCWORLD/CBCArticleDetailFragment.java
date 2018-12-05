package com.example.guanghuahe.cst2335_finalmilestone1.CBCWORLD;
//source: https://www.programcreek.com/java-api-examples/?code=frank-tan/XYZReader/XYZReader-master/XYZReader/src/main/java/com/example/xyzreader/ui/ArticleDetailFragment.java
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guanghuahe.cst2335_finalmilestone1.R;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class CBCArticleDetailFragment extends Fragment {

    /**
     * Constant variable ARG_CARD_ID
     */
    private static final String ARG_CARD_ID = "card_id";

    //variables
    private ImageView mImageView;
    private TextView mNewsTextView;
    private Button mMoreButton;
    private CBCArticle mArticle;

    /**
     * param cardId
     * return fragment
     */
    public static CBCArticleDetailFragment newInstance(UUID cardId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CARD_ID, cardId);
        CBCArticleDetailFragment fragment = new CBCArticleDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID cardId = (UUID) getArguments().getSerializable(ARG_CARD_ID);
        mArticle = CBCNews.get(getActivity()).getArticle(cardId);
    }


    @Override
    public void onPause(){
        super.onPause();
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_card, container, false);

        setView(v);
        showDescription();
        updateImage();
        onClickMore();

        return v;
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

    /** setView inflates the widges NewsTexwView, ImageView, and moreButton
     * inflates the widgets
     * param view v
     */
    private void setView(View v) {
        mNewsTextView = (TextView) v.findViewById(R.id.news_text_view);
        mImageView = (ImageView) v.findViewById(R.id.image_view);
        mMoreButton = (Button) v.findViewById(R.id.more_button);

    }

    private void onClickMore(){
        mMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    Intent intent = CBCWebViewActivity.newIntent(getActivity(),mArticle.getNewsUrl());
                    startActivity(intent);
                    Log.v("HTTP", "Network available");

                } else {
                    Toast.makeText(getActivity(),"Not available",Toast.LENGTH_SHORT).show();
                    Log.v("HTTP", "Network NOT available");
                }

            }
        });
    }

    /**
     * if no network is available networkInfo will be null
     * otherwise check if we are connected
     * @return boolean
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        Log.d("isNetworkAvailable", "network not available");
        return false;
    } // isNetwork

    /**
     * Sets the answer in the TextView when the button is pressed
     * gets the paragraph of the descripton
     */
    private void showDescription() {
        String description = mArticle.getDescription();
        mNewsTextView.setText(description);
    }

    /**
     * Sets the new image in the ImageView when the user presses and gets the image url
     */
    private void updateImage() {
        String imageName = mArticle.getImageUrl();

        Picasso.with(getContext())
                .load(imageName)
                .error(R.drawable.errorimage)
                .into(mImageView);
    }

}

