package com.intelygenz.esrarakici.newsreader.network;

import android.util.Log;
import android.util.Xml;

import com.intelygenz.esrarakici.newsreader.model.data.NewsItem;
import com.intelygenz.esrarakici.newsreader.utils.StringUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by esrarakici on 27/04/2017.
 */

public class XmlParser {
    boolean isItem = false;
    private final String titleTag = "title";
    private final String linkTag = "link";
    private final String descriptionTag = "description";
    private final String dateTag = "pubDate";
    private final String httpTag = "http:";

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        List items = new ArrayList();
        String title = null;
        String link = null;
        String description = null;
        String date = null;
        String imageLink = null;
        while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
            int eventType = xmlPullParser.getEventType();

            String name = xmlPullParser.getName();
            if (name == null)
                continue;

            if (eventType == XmlPullParser.END_TAG) {
                if (name.equalsIgnoreCase("item")) {
                    isItem = false;
                }
                continue;
            }

            if (eventType == XmlPullParser.START_TAG) {
                if (name.equalsIgnoreCase("item")) {
                    isItem = true;
                    continue;
                }
            }


            String result = "";
            if (xmlPullParser.next() == XmlPullParser.TEXT) {
                result = xmlPullParser.getText();
                xmlPullParser.nextTag();
            }
            if (isItem) {
                if (name.equalsIgnoreCase(titleTag)) {
                    title = result;
                } else if (name.equalsIgnoreCase(linkTag)) {
                    link = result;
                } else if (name.equalsIgnoreCase(descriptionTag)) {
                    description = result;
                } else if (name.equalsIgnoreCase(dateTag)) {
                    date = result.substring(0);
                }
            }
            if (title != null && link != null && description != null && date != null) {
                String regularExpression = "src=\"(.*?)\"";
                // Create a Pattern object
                Pattern pattern = Pattern.compile(regularExpression);
                // Now create matcher object.
                Matcher matcher = pattern.matcher(description);
                //Get the image source
                if (matcher.find( )) {
                   imageLink =  matcher.group(1);
                    if(!imageLink.contains(httpTag)){
                        imageLink = httpTag + imageLink;
                    }
                }
                NewsItem item = new NewsItem(title, link, description, date, imageLink);
                items.add(item);

                title = null;
                link = null;
                description = null;
                imageLink = null;
                isItem = false;
            }
        }
        return items;

    }


}
