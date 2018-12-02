package com.example.guanghuahe.cst2335_finalmilestone1.CBCNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.rssparser.Parser;
import com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.rssparser.Story;
import com.example.guanghuahe.cst2335_finalmilestone1.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoryListFragment extends Fragment implements Parser.OnTaskCompleted,StoryAdapter.OnStoryClick {


    private ProgressBar progressBar;
    private ListView storiesListView;
    private  StoryAdapter storyAdapter;

    private String url = "https://www.cbc.ca/cmlink/rss-world";

    public StoryListFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     * @return A new instance of fragment StoryListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoryListFragment newInstance() {
        StoryListFragment fragment = new StoryListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cbc_story_list, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        storiesListView = view.findViewById(R.id.storiesViewList);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData(){
        loadFeed();
    }

    private void loadFeed() {
        inProgress();
        Parser parser = new Parser();
        parser.execute(url);
        parser.onFinish(this);
    }

    void inProgress(){
        progressBar.setVisibility(View.VISIBLE);
        storiesListView.setVisibility(View.INVISIBLE);
    }

    void loaded(){
        progressBar.setVisibility(View.INVISIBLE);
        storiesListView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onTaskCompleted(ArrayList<Story> stories) {
        loaded();
        storyAdapter = new StoryAdapter(stories);
        storyAdapter.addOnStoryClick(this);
        storiesListView.setAdapter(storyAdapter);
    }

    @Override
    public void onError() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loaded();
                Toast.makeText(getContext(), "Unable to load data.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void openStory(Story story) {
        startDetailActivity(story);
    }

    @Override
    public void onLinkClick(String url) {

    }

    @Override
    public void onCategoriesClick(List<String> categories) {
        String categoriesStr = categories.toString().substring(1,categories.toString().length()-1);
        Snackbar.make(getView(),categoriesStr,Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Call method should go to a {@link DetailActivity}.
     *
     * @param story
     */

    void startDetailActivity(Story story){
        Intent intent = new Intent(getContext(),DetailActivity.class);
        intent.putExtra(DetailActivity.DETAIL_STORY_EXTRA,story);
        startActivity(intent);
    }
}
