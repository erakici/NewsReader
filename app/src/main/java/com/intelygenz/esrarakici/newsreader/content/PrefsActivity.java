package com.intelygenz.esrarakici.newsreader.content;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.intelygenz.esrarakici.newsreader.R;
import com.intelygenz.esrarakici.newsreader.utils.PreferencesHelper;
import com.intelygenz.esrarakici.newsreader.utils.AppContstants;

public class PrefsActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButtonSelected;
    private Button btnSelect;
    private String[] data_urls = {AppContstants.DATA_URL, AppContstants.DATA_URL_2, AppContstants.DATA_URL_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);
        addListenerOnButton();

    }

    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnSelect = (Button) findViewById(R.id.button2);
        final String url = PreferencesHelper.getInstance(this).getDataUrl();
        //set urls from array to radiabuttons tags
        for (int i = 0; i < radioGroup.getChildCount() && i < data_urls.length; i++) {
            radioGroup.getChildAt(i).setTag(data_urls[i]);
            if (url.equals(data_urls[i])) {
                ((RadioButton) radioGroup.getChildAt(i)).toggle();
            }
        }

        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButtonSelected = (RadioButton) findViewById(selectedId);
                if (!radioButtonSelected.getTag().equals(url)) {
                    PreferencesHelper.getInstance(PrefsActivity.this).setDataUrl(radioButtonSelected.getTag().toString());
                    setResult(RESULT_OK);
                }
                finish();

            }

        });
    }
}
