package com.jiushig.rich.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.webkit.WebView;

import com.jiushig.rich.BaseActivity;
import com.jiushig.rich.R;
import com.jiushig.rich.utils.MarkDownHandler;


/**
 * Created by zk on 2017/7/16.
 */

public class PreviewActivity extends BaseActivity {

    private String text;

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        setToolBar();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text = getIntent().getStringExtra("text");

        initViews();

        webView.loadData(MarkDownHandler.getInstance().toHtml(text), "text/html", "utf-8");
    }

    private void initViews() {
        webView = (WebView) findViewById(R.id.webView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
