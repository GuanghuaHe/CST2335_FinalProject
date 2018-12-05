package com.example.guanghuahe.cst2335_finalmilestone1.CBCWORLD;
import android.app.Activity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/** source:https://www.androidauthority.com/simple-rss-reader-full-tutorial-733245/
 */

/**
 * This class is used to carry the content of CBC world news
 * and used to to stay up-to-date with the content
 */
public class CBCRSSParser {
    /**
     * The parser extracts data for articles and and ignores the rest of the data
     * @param activity
     * @param is
     */
    public void parseXML(Activity activity, InputStream is){
        CBCNews news = CBCNews.get(activity);
        ArrayList<CBCArticle> articles = news.getArticles();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbFactory.newDocumentBuilder();
            Document document = db.parse(is);

            Element element = document.getDocumentElement();
            element.normalize();

            NodeList nodeList = document.getElementsByTagName("item");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element item = (Element) node;
                    String theHeadline = getValue("title", item);
                    String theNewsUrl = getValue("link", item);
                    String theDescription = getValue("description", item);
                    String[] articleDescription = getArticleDescription(theDescription);
                    String theImageUrl = articleDescription[0];
                    String paragraph = articleDescription[1];

                    CBCArticle article = new CBCArticle(theHeadline, theImageUrl, theNewsUrl, paragraph);
                    articles.add(article);
                }
            }
        }catch (Exception e){
            System.err.print(e.getMessage());
        }

    }
    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node;

        if (tag.equals("description")) {
            node = nodeList.item(1);
            return node.getNodeValue();
        } else {
            node = nodeList.item(0);
            return node.getNodeValue();
        }

    }

    private String[] getArticleDescription(String theDescription) {

        int a = theDescription.indexOf("src='") + 5;
        int b = theDescription.lastIndexOf("' al");
        String theImageUrl = theDescription.substring(a, b);

        a = theDescription.indexOf("<p>") + 3 ;
        b = theDescription.lastIndexOf("</p>");
        String paragraph = theDescription.substring(a, b);

        String[] articleDescription = {theImageUrl, paragraph};
        return articleDescription;
    }

}
