package com.jiushig.rich.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.TextView;

import com.jiushig.rich.BaseActivity;
import com.jiushig.rich.R;
import com.zzhoujay.richtext.RichText;


/**
 * Created by zk on 2017/7/16.
 */

public class PreviewActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        setToolBar();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();

    }

    private void initViews() {
        TextView textView = (TextView) findViewById(R.id.text);
        RichText.fromMarkdown(getIntent().getStringExtra("text")).into(textView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
