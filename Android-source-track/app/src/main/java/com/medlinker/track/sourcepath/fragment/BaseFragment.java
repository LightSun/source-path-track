package com.medlinker.track.sourcepath.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heaven7.core.util.Logger;
import com.heaven7.core.util.Toaster;
import com.heaven7.core.util.ViewHelper;
import com.medlinker.track.sourcepath.BaseActivity;


/**
 * Created by heaven7 on 2015/8/14.
 */
public abstract class BaseFragment extends Fragment {

    private static final boolean sDebug = true;

    private Toaster mToaster;
    private ViewHelper mViewHelper;
    //private VolleyUtil.HttpExecutor mHttpExecutor;
    private ICallback mCallback;
    private Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;
    }
    public BaseActivity getActivity2(){
        return (BaseActivity) mActivity;
    }

    protected void launchActivity(Class<?> clazz){
        mActivity.startActivity(new Intent(mActivity,clazz));
    }

    protected void launchActivity(Class<?> clazz,Bundle data){
        mActivity.startActivity(new Intent(mActivity, clazz).putExtras(data));
    }
    protected void launchActivity(Class<?> clazz,Bundle data,int flags){
        mActivity.startActivity(new Intent(mActivity, clazz)
                        .putExtras(data).addFlags(flags)
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getlayoutId(),container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Context ctx = view.getContext();
        mToaster = new Toaster(ctx);
        mViewHelper = new ViewHelper(view);
      //  mHttpExecutor = new VolleyUtil.HttpExecutor();
        initView(ctx);
        initData(ctx, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected void showToast(String msg){
        mToaster.show(msg);
    }
    protected void showToast(int resID){
        mToaster.show(resID);
    }
    protected void showToastIfDebug(String msg){
        if(sDebug) mToaster.show(msg);
    }
    protected void showToastIfDebug(int resID){
        if(sDebug) mToaster.show(resID);
    }

    public Toaster getToaster() {
        return mToaster;
    }
    public ViewHelper getViewHelper() {
        return mViewHelper;
    }

    public ICallback getCallback() {
        return mCallback;
    }
    public void setCallback(ICallback mCallback) {
        this.mCallback = mCallback;
    }
    public void callbackIfNeed(int key,Object data){
        if(mCallback != null){
            mCallback.callback(key,data);
        }else{
            Logger.w("BaseFragment_ICallback","callbackIfNeed"," but mCallback is null !");
        }
    }

    protected abstract int getlayoutId();

    protected abstract void initView(Context context);

    protected abstract void initData(Context context, Bundle savedInstanceState);

    public  interface ICallback{
       void callback(int key, Object data);
    }
}
