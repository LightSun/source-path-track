package com.medlinker.track.sourcepath.demo;

import android.os.Bundle;
import android.widget.Button;

import com.medlinker.track.sourcepath.BaseActivity;
import com.medlinker.track.sourcepath.TrackFactory;

import butterknife.InjectView;

/**
 * Created by heaven7 on 2016/4/28.
 */
public class ShowTextActivity extends BaseActivity {

    public static final String KEY_TEXT = "text";
    public static final String KEY_LEVEL = "level";

    @InjectView(R.id.bt)
    Button mBt;

    private String mText;
    private int mLevel = -1;

    @Override
    protected int getlayoutId() {
        return R.layout.ac_show_text;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String text = getIntent().getStringExtra(KEY_TEXT);
        this.mText = text;
        this.mLevel = getIntent().getIntExtra(KEY_LEVEL , -1);
        if(text!=null) {
            mBt.setText(text);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mText != null){
            TrackFactory.getDefaultTagTracker().track(mLevel, mText);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
