package com.software.hms.projeto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        TextView textView = (TextView)findViewById(R.id.txtDesc);
        textView.setText(Html.fromHtml(getString(R.string.info)));


    }
}
