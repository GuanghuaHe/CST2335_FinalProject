package com.example.guanghuahe.cst2335_finalmilestone1.CBCNews;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.rssparser.Story;
import com.example.guanghuahe.cst2335_finalmilestone1.R;


public class StoryAdapter extends BaseAdapter {

    private ArrayList<Story> stories;
    private ArrayList<TextWatcher> textWatcherList;

    private OnStoryClick onStoryClick;

    /**
     * Class constructor specifying List  of stories.
     */
    StoryAdapter(ArrayList<Story> stories) {
        this.stories = stories;
        textWatcherList = new ArrayList(stories.size());
        for (int i= 0 ; i< stories.size();i++)
            textWatcherList.add(null);
    }

    @Override
    public int getCount() {
        return stories.size();
    }

    @Override
    public Object getItem(int position) {
        return stories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        final Story story = (Story) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(container.getContext()).inflate(R.layout.item_cbc_story,container, false);
        }
        Locale.setDefault(Locale.getDefault());
        Date date = story.getPubDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        final String pubDateString = sdf.format(date);
        final String categories = story.getCategories().toString().substring(1,story.getCategories().toString().length()-1);
        ((TextView) convertView.findViewById(R.id.title)).setText(story.getTitle());
        ((TextView) convertView.findViewById(R.id.pubDate)).setText(pubDateString);
        ((TextView) convertView.findViewById(R.id.categories)).setText(categories);
        convertView.findViewById(R.id.link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onStoryClick != null){
                    onStoryClick.onLinkClick(story.getLink());
                }
            }
        });
        convertView.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stories.remove(story);
                notifyDataSetChanged();
            }
        });
        convertView.findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onStoryClick != null){
                    onStoryClick.openStory(story);
                }
            }
        });
        convertView.findViewById(R.id.categories).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onStoryClick != null){
                    onStoryClick.onCategoriesClick(story.getCategories());
                }
            }
        });
        if (textWatcherList.get(position)== null){
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    story.setComment(s.toString());
                }
            };
            textWatcherList.add(position,textWatcher);
        }
//        EditText note = convertView.findViewById(R.id.note);
//        for (int i= 0 ; i< stories.size();i++)
//            if (textWatcherList.get(i)!=null)
//                note.removeTextChangedListener(textWatcherList.get(i));
//        note.setText(story.getComment());
//        note.addTextChangedListener(textWatcherList.get(position));
        return convertView;
    }

    /**
     * Register a callback to be invoked when something happened
     *
     * @param onStoryClick the callback that will run
     */
    public void addOnStoryClick(OnStoryClick onStoryClick) {
        this.onStoryClick = onStoryClick;
    }



    /**
     * Interface definition for a callback to be invoked when a StoryView is clicked.
     */
    public interface  OnStoryClick{
        /**
         * Called when a part StoryView has been clicked.
         *
         * @param story The story  that was clicked.
         */
        void openStory(Story story);

        /**
         * Called when a part StoryView  url  has been clicked.
         *
         * @param url  the   {@link Story#link}
         */
        void onLinkClick(String url);

        /**
         *  Called when a part StoryView  categories  has been clicked.
         *
         * @param categories the {@link Story#categories}
         */
        void onCategoriesClick(List<String> categories);
    }
}
