package com.jiushig.rich.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.jiushig.rich.BaseActivity;
import com.jiushig.rich.R;
import com.jiushig.rich.utils.Permission;
import com.jiushig.rich.utils.RichHandler;

import net.cachapa.expandablelayout.ExpandableLayout;


public class EditorActivity extends BaseActivity {

    protected EditText editTitle;
    protected EditText editText;

    private ExpandableLayout expandableLayout;

    private RichHandler richHandler;

    public final static int REQUEST_CODE_IMG = 4000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        setToolBar();
        initViews();

        getData();

        richHandler = new RichHandler(this, expandableLayout, editText);
    }

    private void initViews() {
        editTitle = (EditText) findViewById(R.id.title);
        editText = (EditText) findViewById(R.id.content);
        expandableLayout = (ExpandableLayout) findViewById(R.id.expandable_layout);
    }

    private void getData() {
        String title = getIntent().getStringExtra("title");
        String text = getIntent().getStringExtra("text");
        this.editTitle.setText(title == null ? "" : title);
        this.editText.setText(text == null ? "" : text);
    }

    /**
     * 获得启动此Activity的Intent
     *
     * @param title
     * @param text
     */
    public static Intent getStartIntent(String title, String text) {
        Intent intent = new Intent();
        intent.putExtra("title", title);
        intent.putExtra("text", text);
        return intent;
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
            clickSave(editTitle.getText().toString(), editText.getText().toString());
        } else if (item.getItemId() == R.id.action_more) {
            expandableLayout.toggle();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_IMG) {
                richHandler.addImg(data.getData());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permission.REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                richHandler.onClick(findViewById(R.id.edit_img));
            } else {
                Snackbar.make(findViewById(R.id.root), "获取SD卡读写权限失败", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 预览
     */
    protected void clickPreview() {
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra("text", editText.getText().toString());
        startActivity(intent);
    }

    /**
     * 语法参考
     */
    protected void clickReference() {

    }

    /**
     * 保存
     *
     * @param title
     * @param text
     */
    protected void clickSave(String title, String text) {

    }

}
