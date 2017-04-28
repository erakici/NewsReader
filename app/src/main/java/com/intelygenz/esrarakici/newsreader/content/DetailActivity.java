package com.intelygenz.esrarakici.newsreader.content;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelygenz.esrarakici.newsreader.R;
import com.intelygenz.esrarakici.newsreader.model.data.NewsItem;
import com.intelygenz.esrarakici.newsreader.utils.AppContstants;
import com.intelygenz.esrarakici.newsreader.utils.ImageUtils;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final NewsItem newsItem = getIntent().getParcelableExtra(AppContstants.NEWS);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(newsItem.Link);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });


        ImageView img = (ImageView)findViewById(R.id.image);
        ImageUtils.loadFullImage(this,newsItem.ImageLink, img);

        TextView title = (TextView) findViewById(R.id.titleTextView);
        title.setText(newsItem.Title.trim());

        TextView text = (TextView) findViewById(R.id.descriptionTextView);
        text.setText(newsItem.DescriptionWOTags);
//TODO:
        //if html version wants to be used, uncomment this block
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//            text.setText(Html.fromHtml(newsItem.Description,Html.FROM_HTML_MODE_COMPACT));
//        }else{
//            text.setText(Html.fromHtml(newsItem.Description));
//        }



    }

}
