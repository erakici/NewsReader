package com.intelygenz.esrarakici.newsreader.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelygenz.esrarakici.newsreader.R;
import com.intelygenz.esrarakici.newsreader.model.data.NewsItem;
import com.intelygenz.esrarakici.newsreader.utils.ImageUtils;

import java.util.List;

/**
 * Created by esrarakici on 26/04/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsItem> adapterList;
    private OnItemClickListener mItemClickListener;

    public NewsAdapter(List<NewsItem> list) {
        this.adapterList = list;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        TextView date;
        ImageView image;

        public ListViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.titleTextView);
            description = (TextView) view.findViewById(R.id.descriptionTextView);
            date = (TextView) view.findViewById(R.id.dateTextView);
            image = (ImageView) view.findViewById(R.id.newsImg);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_news, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsItem item = adapterList.get(position);
        ListViewHolder listHolder = ((ListViewHolder) holder);
        listHolder.title.setText(item.Title);
        listHolder.description.setText(item.DescriptionWOTags);
        listHolder.date.setText(item.Date.substring(0, 22));
        ImageUtils.loadThumbnail(listHolder.image.getContext(), item.ImageLink, listHolder.image);

    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }
}
