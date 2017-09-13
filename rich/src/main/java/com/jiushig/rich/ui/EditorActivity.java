package com.jiushig.rich.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.jiushig.rich.BaseActivity;
import com.jiushig.rich.R;
import com.jiushig.rich.utils.RichHandler;

import net.cachapa.expandablelayout.ExpandableLayout;


public class EditorActivity extends BaseActivity {

    protected EditText title;
    protected EditText content;

    private ExpandableLayout expandableLayout;

    private RichHandler richHandler;

    public final static int REQUEST_CODE_IMG = 4000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        setToolBar();
        initViews();

        richHandler = new RichHandler(this, expandableLayout, content);
    }

    private void initViews() {
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        expandableLayout = (ExpandableLayout) findViewById(R.id.expandable_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_preview) {
            clickPreview();
        } else if (item.getItemId() == R.id.action_reference) {
            clickReference();
        } else if (item.getItemId() == R.id.action_save) {
            save(title.getText().toString(), content.getText().toString());
        } else if (item.getItemId() == R.id.action_more) {
            expandableLayout.toggle();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void clickPreview() {
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra("text", content.getText().toString());
        startActivity(intent);
    }

    protected void clickReference() {

    }

    /**
     * 保存
     *
     * @param title
     * @param content
     */
    protected void save(String title, String content) {

    }
}
