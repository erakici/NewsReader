package com.intelygenz.esrarakici.newsreader.network;

import android.os.AsyncTask;
import com.intelygenz.esrarakici.newsreader.utils.AppContstants;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by esrarakici on 27/04/2017.
 */

public class FeedTask extends AsyncTask<Void, Void, List> {

    private String dataUrl = AppContstants.DATA_URL;

    private RequestListener listener;

    private int timeout = 10000;/* milliseconds */

    public FeedTask(String url, RequestListener value) {
        if (url != null) { this.dataUrl = url; }

        listener = value;
    }
    public void onResponse(List value) {
        if (listener != null) {
            listener.onResponse(value);
        }
    }

    public void onError(String value) {
        if (listener != null) {
            listener.onError(value);
        }
    }
    @Override
    protected List doInBackground(Void... params) {
        try {

            URL url = new URL(dataUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(timeout);
            conn.setConnectTimeout(timeout);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            InputStream inputStream = conn.getInputStream();

            return new XmlParser().parse(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            onError(e.getMessage());
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            onError(e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(List data) {
        if (data != null) {
            onResponse(data);
        }
    }
}
