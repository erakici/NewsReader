package com.intelygenz.esrarakici.newsreader.content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.intelygenz.esrarakici.newsreader.db.DataBaseHandler;
import com.intelygenz.esrarakici.newsreader.model.NewsAdapter;
import com.intelygenz.esrarakici.newsreader.R;
import com.intelygenz.esrarakici.newsreader.utils.PreferencesHelper;
import com.intelygenz.esrarakici.newsreader.model.data.NewsItem;
import com.intelygenz.esrarakici.newsreader.network.Request;
import com.intelygenz.esrarakici.newsreader.network.RequestListener;
import com.intelygenz.esrarakici.newsreader.utils.AppContstants;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class NewsActivity extends AppCompatActivity {

    private static final int PREF_REQUEST_CODE = 101;
    private RecyclerView newsListView;
    private NewsAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private ArrayList<NewsItem> list = new ArrayList<>();
    private ArrayList<NewsItem> backupList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newsListView = (RecyclerView) findViewById(R.id.recyclerView);
        newsListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(list);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestNews();
            }
        });
        requestNews();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);

        MenuItem menuSearch = menu.findItem(R.id.action_search);
        menuSearch.setActionView(R.layout.row_search_bar);
        View v = menuSearch.getActionView();
        EditText searchView = (EditText) v.findViewById(R.id.txt_search);
        setupChatView(searchView);
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivityForResult(new Intent(NewsActivity.this, PrefsActivity.class), PREF_REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PREF_REQUEST_CODE && resultCode == RESULT_OK) {
            //Data source changed
            requestNews();
        }

    }

    private void setBackupList(ArrayList<NewsItem> data) {
        backupList = new ArrayList<>();
        for (NewsItem item : data) {
            backupList.add(item);

        }

    }

    private void setData(ArrayList<NewsItem> data) {
        list.clear();


        for (NewsItem item : data) {
            list.add(item);
        }

        newsListView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsItem obj = list.get(position);

                Intent intent = new Intent(NewsActivity.this, DetailActivity.class);
                intent.putExtra(AppContstants.NEWS, obj);
                startActivity(intent);

            }
        });

        if (swipeLayout != null && swipeLayout.isRefreshing())
            swipeLayout.setRefreshing(false);


    }

    private void saveDataToLocal() {
        //upgrade database with last data
        int version = PreferencesHelper.getInstance(this).increaseDbVersion();
        DataBaseHandler db = new DataBaseHandler(this, version);
        db.open();

        for (NewsItem item : list) {
            db.insertNewsInfo(item);
        }
        db.close();
    }


    //Search bar
    public void setupChatView(final EditText searchView) {

        searchView.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String str = searchView.getText().toString();
                //After 2 letters, filter list
                if (str.length() > 2) {
                    setData(filterList(str));
                    //if search word is removed, clean filter
                } else if (str.length() == 0) {
                    setData(backupList);
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private ArrayList<NewsItem> filterList(String searchKey) {
        ArrayList<NewsItem> filteredList = new ArrayList<>();
        for (NewsItem item : list) {
            if (item.Title.toLowerCase().contains(searchKey.toLowerCase())) {
                filteredList.add(item);
            }
        }

        return filteredList;

    }


    private void requestNews() {
        new Request(new RequestListener() {
            @Override
            public void onResponse(List value) {
                ArrayList<NewsItem> list1 = (ArrayList<NewsItem>) value;
                sortListByDate(list1);
                setBackupList(list1);
                setData(list1);
                saveDataToLocal();
            }

            @Override
            public void onLocalResponse(List value) {
                setBackupList((ArrayList<NewsItem>) value);
                setData((ArrayList<NewsItem>) value);
            }

            @Override
            public void onError(final String message) {
                Toast.makeText(NewsActivity.this, message, Toast.LENGTH_SHORT);
            }
        }, this);

    }

    public void sortListByDate(List<NewsItem> list) {
        final DateFormat f = (new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH));

        Collections.sort(list, new Comparator<NewsItem>() {


            @Override
            public int compare(NewsItem o1, NewsItem o2) {
                try {
                    return f.parse(o2.Date).compareTo(f.parse(o1.Date));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }

            }
        });

    }
}
