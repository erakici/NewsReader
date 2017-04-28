package com.intelygenz.esrarakici.newsreader.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by esrarakici on 27/04/2017.
 */

public class ImageUtils {
    public static void loadFullImage(Context context, String link, ImageView view){
        Glide.with(context).load(link).into(view);

    }
    public static void loadThumbnail(Context context, String link,ImageView view){
        Glide.with(context).load(link).thumbnail(0.2f).into(view);

    }
}
