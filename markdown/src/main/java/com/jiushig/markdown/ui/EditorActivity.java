package com.jiushig.markdown.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.jiushig.markdown.BaseActivity;
import com.jiushig.markdown.R;
import com.jiushig.markdown.ui.adapter.ViewPagerAdapter;
import com.jiushig.markdown.ui.fragment.EditorFragment;
import com.jiushig.markdown.ui.fragment.PreviewFragment;
import com.jiushig.markdown.utils.Log;
import com.jiushig.markdown.utils.MarkdownUtils;
import com.jiushig.markdown.utils.PermissionUtils;
import com.jiushig.markdown.widget.MarkdownView;

import java.util.ArrayList;
import java.util.List;


public class EditorActivity extends BaseActivity {

    private static final String TAG = EditorActivity.class.getSimpleName();

    protected ViewPager viewPager;

    private EditorFragment editorFragment = new EditorFragment();
    private PreviewFragment previewFragment = new PreviewFragment();

    public final static int REQUEST_CODE_IMG = 4000;

    private Menu menu;

    private MarkdownView.LinkClickListener linkClickListener = new MarkdownView.LinkClickListener() {
        @Override
        public void click(String url) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            startActivity(intent);
        }
    };
    private MarkdownView.ImgClickListener imgClickListener = new MarkdownView.ImgClickListener() {
        @Override
        public void click(String[] urls, int index) {
            Log.d(TAG, urls[index]);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 保持竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_editor);
        setToolBar();
        initViews();

        toolbar.setTitle(R.string.action_ot_edit);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(editorFragment);
        fragments.add(previewFragment);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    toolbar.setTitle(R.string.action_ot_edit);
                    if (menu != null)
                        menu.findItem(R.id.action_preview).setVisible(true);

                    showKeyboard();
                }
                if (position == 1) {
                    if (editorFragment.editText != null) {
                        previewFragment.load(editorFragment.editText.getText().toString());
                        toolbar.setTitle(R.string.action_ot_preview);
                        closeKeyboard();

                        if (menu != null)
                            menu.findItem(R.id.action_preview).setVisible(false);

                        if (preferences.getBoolean("tip_preview", true)) {
                            new AlertDialog.Builder(EditorActivity.this)
                                    .setTitle(R.string.ot_tip)
                                    .setMessage(R.string.ot_tip_preview)
                                    .setPositiveButton(R.string.ot_tip_i_know, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            preferences.edit().putBoolean("tip_preview", false).apply();
                                        }
                                    }).show();
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (viewPager.getCurrentItem() == 1) {
                viewPager.setCurrentItem(0);
            } else {
                finish();
            }
        } else if (item.getItemId() == R.id.action_reference) {
            clickReference();
        } else if (item.getItemId() == R.id.action_save) {
            clickSave(getMarkdownTitle(), getMarkdownText());
        } else if (item.getItemId() == R.id.action_preview) {
            viewPager.setCurrentItem(1);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_IMG) {
                if (editorFragment.editorHandler != null)
                    editorFragment.editorHandler.addImg(data.getData());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (editorFragment.editorHandler != null)
                    editorFragment.editorHandler.openImg();
            } else {
                Snackbar.make(findViewById(R.id.root), R.string.sd_fail, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (viewPager.getCurrentItem() == 1) {
                viewPager.setCurrentItem(0);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    public void setLinkClickListener(MarkdownView.LinkClickListener linkClickListener) {
        this.linkClickListener = linkClickListener;
    }

    public MarkdownView.LinkClickListener getLinkClickListener() {
        return linkClickListener;
    }

    public MarkdownView.ImgClickListener getImgClickListener() {
        return imgClickListener;
    }

    public void setImgClickListener(MarkdownView.ImgClickListener imgClickListener) {
        this.imgClickListener = imgClickListener;
    }

    /**
     * 关闭键盘的方法
     */
    public void closeKeyboard() {
        closeKeyboard(editorFragment.editText, this);
    }

    /**
     * 打开软键盘
     */
    public void showKeyboard() {
        showKeyboard(editorFragment.editText);
    }

    /**
     * 获得标题
     *
     * @return
     */
    public String getMarkdownTitle() {
        if (editorFragment.editTitle != null)
            return editorFragment.editTitle.getText().toString();
        return "";
    }

    /**
     * 获得文本
     *
     * @return
     */
    public String getMarkdownText() {
        if (editorFragment.editText != null)
            return editorFragment.editText.getText().toString();
        return "";
    }

    public MarkdownView getMarkdownView() {
        return previewFragment.markdownView;
    }

    /**
     * 设置标题
     *
     * @return
     */
    public void setMarkdownTitle(String title) {
        if (editorFragment.editTitle != null && title != null)
            editorFragment.editTitle.setText(title);
    }

    /**
     * 设置文本
     *
     * @return
     */
    public void setMarkdownText(String text) {
        if (editorFragment.editText != null && text != null)
            editorFragment.editText.setText(text);
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
        Log.d(TAG, MarkdownUtils.getImgUrlFromMarkDownText(text).toString());
    }


}
