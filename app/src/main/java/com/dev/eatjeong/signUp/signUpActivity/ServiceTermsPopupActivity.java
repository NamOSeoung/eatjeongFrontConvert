package com.dev.eatjeong.signUp.signUpActivity;

import android.app.Activity;
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

public class ServiceTermsPopupActivity extends AppCompatActivity {

    private AppCompatTextView tv;
    private TextView title_text;
    private ConstraintLayout back_button;
    private ImageButton exit_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_popup);

        View action_bar = findViewById(R.id.action_bar);
        exit_button = (ImageButton) action_bar.findViewById(R.id.exit_image);
        back_button = (ConstraintLayout)action_bar.findViewById(R.id.back_button);
        title_text = action_bar.findViewById(R.id.textview1);
        tv = (AppCompatTextView) findViewById(R.id.appCompatTextView1);

        back_button.setVisibility(View.INVISIBLE);
        title_text.setText("서비스 이용 약관");

        String service_terms = getString(R.string.service_terms);
        tv.setText(Html.fromHtml(service_terms));

        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.stay, R.anim.sliding_down);
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
