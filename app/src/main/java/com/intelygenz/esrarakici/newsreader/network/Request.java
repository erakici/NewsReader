package com.intelygenz.esrarakici.newsreader.network;

import android.content.Context;

import com.intelygenz.esrarakici.newsreader.db.DataBaseHandler;
import com.intelygenz.esrarakici.newsreader.utils.PreferencesHelper;
import com.intelygenz.esrarakici.newsreader.model.data.NewsItem;
import com.intelygenz.esrarakici.newsreader.utils.NetworkUtils;

import java.util.List;

/**
 * Created by esrarakici on 27/04/2017.
 */

public class Request {

    // Uses AsyncTask to download the XML feed or local data.
    public Request(RequestListener requestListener, Context context) {
        if (NetworkUtils.isNetworkConnected(context)) {
            new FeedTask(PreferencesHelper.getInstance(context).getDataUrl(), requestListener).execute();
        } else {
            int version = PreferencesHelper.getInstance(context).getDbVersion();
            DataBaseHandler db = new DataBaseHandler(context, version);
            db.open();
            List<NewsItem> list = db.getAllNews();
            if (list.size() > 0) {
                requestListener.onLocalResponse(db.getAllNews());
            } else {
                //TODO: no data found in local
            }
        }
    }
}


