package com.jiushig.rich.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.jiushig.rich.BaseActivity;
import com.jiushig.rich.R;
import com.jiushig.rich.widget.RichView;


/**
 * Created by zk on 2017/7/16.
 */

public class PreviewActivity extends BaseActivity {

    private String text;

    private RichView richView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        setToolBar();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        text = getIntent().getStringExtra("text");

        initViews();

    }

    private void initViews() {
        richView = (RichView) findViewById(R.id.richView);

        richView.setText(text);

        richView.setListener(new RichView.LinkClickListener() {
            @Override
            public void click(String url) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
