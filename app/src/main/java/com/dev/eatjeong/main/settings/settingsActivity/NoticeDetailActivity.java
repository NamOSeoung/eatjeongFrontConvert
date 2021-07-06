package com.dev.eatjeong.main.settings.settingsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dev.eatjeong.R;

public class NoticeDetailActivity extends AppCompatActivity {

    private AppCompatTextView tv;
    private TextView title_text,back_text;
    private ConstraintLayout back_button;
    private ImageButton exit_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_detail);

        View action_bar = findViewById(R.id.action_bar);
        exit_button = (ImageButton)action_bar.findViewById(R.id.exit_image);
        back_button = (ConstraintLayout)action_bar.findViewById(R.id.back_button);
        title_text = action_bar.findViewById(R.id.textview1);
        back_text = action_bar.findViewById(R.id.back_text);
        tv = (AppCompatTextView) findViewById(R.id.appCompatTextView1);
        back_text.setText("뒤로");
        title_text.setVisibility(View.INVISIBLE);
        exit_button.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        String service_terms = intent.getStringExtra("contents");
        tv.setText(Html.fromHtml(service_terms));

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_in_left);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
