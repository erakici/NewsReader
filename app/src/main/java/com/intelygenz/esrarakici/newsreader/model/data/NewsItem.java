package com.intelygenz.esrarakici.newsreader.model.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.intelygenz.esrarakici.newsreader.utils.StringUtils;

/**
 * Created by esrarakici on 26/04/2017.
 */

public class NewsItem implements Parcelable {
    public String Title;
    public String Link;
    public String Description;
    public String DescriptionWOTags;
    public String Date;
    public String ImageLink;

    public NewsItem(String title, String link, String description, String date, String imageLink) {
        Title = title;
        Link = link;
        Description = description;
        Date = date;
        ImageLink = imageLink;
        DescriptionWOTags = StringUtils.removeTags(Description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Title);
        dest.writeString(Link);
        dest.writeString(Description);
        dest.writeString(DescriptionWOTags);
        dest.writeString(Date);
        dest.writeString(ImageLink);
    }

    public NewsItem(Parcel in) {
        Title = in.readString();
        Link = in.readString();
        Description = in.readString();
        DescriptionWOTags = in.readString();
        Date = in.readString();
        ImageLink = in.readString();
    }

    public static final Creator CREATOR = new Creator() {
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };
}
