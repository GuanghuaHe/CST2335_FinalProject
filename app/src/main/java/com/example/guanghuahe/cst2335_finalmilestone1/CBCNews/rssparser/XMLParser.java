package com.example.guanghuahe.cst2335_finalmilestone1.CBCNews.rssparser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

/**
 * Class  create List<Story> from  xml string
 */

public class XMLParser extends Observable {

    private ArrayList<Story> stories;
    private Story currentStory;

    /**
     * Class constructor.
     */

    public XMLParser(){
        stories = new ArrayList<>();
        currentStory = new Story();
    }

    /**
     * Method parsing   adn  create  list stories
     *
     * @param xml  String  when we  must  parsing
     * @throws XmlPullParserException
     * @throws IOException
     */

    public void parseXML(String xml) throws XmlPullParserException, IOException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

        factory.setNamespaceAware(false);
        XmlPullParser xmlPullParser = factory.newPullParser();

        xmlPullParser.setInput(new StringReader(xml));
        boolean insideItem = false;
        int eventType = xmlPullParser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG) {
                if (xmlPullParser.getName().equalsIgnoreCase("item")) {
                    insideItem = true;

                } else if (xmlPullParser.getName().equalsIgnoreCase("title")) {
                    if (insideItem) {
                        String title = xmlPullParser.nextText();
                        currentStory.setTitle(title);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("link")) {
                    if (insideItem) {
                        String link = xmlPullParser.nextText();
                        currentStory.setLink(link);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("author")) {
                    if (insideItem) {
                        String author = xmlPullParser.nextText();
                        currentStory.setAuthor(author);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("category")) {
                    if (insideItem) {
                        String category = xmlPullParser.nextText();
                        currentStory.addCategory(category);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("description")) {
                    if (insideItem) {
                        String description = xmlPullParser.nextText();
                        currentStory.setDescription(description);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("pubDate")) {
                    Date pubDate = new Date(xmlPullParser.nextText());
                    currentStory.setPubDate(pubDate);
                }

            } else if (eventType == XmlPullParser.END_TAG && xmlPullParser.getName().equalsIgnoreCase("item")) {
                insideItem = false;
                stories.add(currentStory);
                currentStory = new Story();
            }
            eventType = xmlPullParser.next();
        }
        triggerObserver();
    }


    /**
     * Method  notify  Observers
     */
    private void triggerObserver() {
        setChanged();
        notifyObservers(stories);
    }



}
