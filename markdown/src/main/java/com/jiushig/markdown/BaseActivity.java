package com.jiushig.markdown;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by zk on 2017/7/16.
 */

public class BaseActivity extends AppCompatActivity {


    protected Toolbar toolbar;

    protected SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("OTMarkdownEditor", MODE_PRIVATE);
    }

    /**
     * 设置ToolBar
     */
    protected void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     * @param mContext
     */
    public void closeKeyboard(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }


    /**
     * 打开软键盘
     *
     * @param mEditText
     */
    public boolean showKeyboard(EditText mEditText) {
        if (mEditText == null) return false;
        mEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.showSoftInput(mEditText, InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

}
