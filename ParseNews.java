package com.ashuguy.gulfnewsrss;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by dell on 5/17/2017.
 */

public class ParseNews {

    private String xmlData;
    private ArrayList<News> news;

    public ParseNews(String xmlData) {
        this.xmlData = xmlData;
        news = new ArrayList<>();
    }

    public ArrayList<News> getNews() {
        return news;
    }

    public boolean process ()
    {
        boolean status = true;
        News currentNews = null;
        boolean isEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();

            while (eventType!= XmlPullParser.END_DOCUMENT)
            {
                String tagName = xpp.getName();
                switch (eventType)
                {
                    case XmlPullParser.START_TAG:
//                        Log.d("ParseApplications","Start tag for: " + tagName);
                        if (tagName.equalsIgnoreCase("item"))
                        {
                            isEntry = true;
                            currentNews = new News();

                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
//                        Log.d("ParseApplications","End tag for: " + tagName);
                        if (isEntry)
                        {
                            if (tagName.equalsIgnoreCase("item"))
                            {
                                news.add(currentNews);
                                isEntry = false;
                            }
                            else if (tagName.equalsIgnoreCase("title"))
                            {
                                currentNews.setTitle(textValue);
                            }
                            else if (tagName.equalsIgnoreCase("link"))
                            {
                                currentNews.setLink(textValue);
                            }
                            else if (tagName.equalsIgnoreCase("description"))
                            {
                                currentNews.setDescription(textValue);
                            }

                        }
                        break;

                    default:
                        //nothing

                }

                eventType = xpp.next();

            }

        }catch (Exception e)
        {
            Log.d("ParseApplications","Error"+e.getMessage());
            e.printStackTrace();
        }

        for (News newsob : news)
        {
            Log.d("ParseApplications","**********");
            Log.d("ParseApplications","Title: " + newsob.getTitle());
            Log.d("ParseApplications","Link: " + newsob.getLink());
            Log.d("ParseApplications","Description: " + newsob.getDescription());

        }

        return true;

    }
}
