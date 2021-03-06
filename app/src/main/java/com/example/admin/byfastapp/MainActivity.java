package com.example.admin.byfastapp;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.byfastapp.ui.BaseTitleBarActivity;
import com.example.byfastapp.weight.status.IStatusView;
import com.example.byfastapp.weight.status.StatusChangeListener;
import com.example.byfastapp.weight.status.StatusControllerFrameLayoutImp;
import com.example.byfastapp.weight.status.StatusView;

import butterknife.BindView;


public class MainActivity
        extends BaseTitleBarActivity {

    @BindView(R.id.tv_content)
    TextView       mTvContent;
    @BindView(R.id.activity_main)
    RelativeLayout mActivityMain;
    private IStatusView mStatusView;

    @Override
    protected void initContentView(View contentView) {
        setViewOnClickListener(mTvContent,v -> showLoadingFragment());
        mStatusView = new StatusView.Builder(mActivityMain)
                                .setController(new StatusControllerFrameLayoutImp())
                                .setStatusView(R.layout.status_loading,"loading")
                                .setStatusView(R.layout.status_error,"error")
                                .setOnStatusChangeListener(new StatusChangeListener() {

                                    @Override
                                    public void onAfterChangeStatus(String lastStatus,
                                                                    String nowStatus,
                                                                    View currentView)
                                    {
                                        Log.e(TAG, "onAfterChangeStatus: "+currentView.equals(mActivityMain) );
                                    }

                                    @Override
                                    public void onStatusViewShowing(String status,
                                                                    View statusView)
                                    {
                                        showShortToast(status);
                                        if (TextUtils.equals(status,"loading")){
                                            statusView.setOnClickListener(v -> mStatusView.showStatusView("error"));
                                        }else if (TextUtils.equals("error",status)){
                                            statusView.setOnClickListener(v -> mStatusView.showTargetView());
                                        }
                                    }

                                    @Override
                                    public void onBeginChangeStatus(String nowStatus,
                                                                    String afterStatus,
                                                                    View currentView)
                                    {
                                        Log.e(TAG, "onBeginChangeStatus: "+nowStatus );
                                    }


                                })
                                .build();
    }

    private void showLoadingFragment() {
        mStatusView.showStatusView("loading");
    }

    @Override
    protected CharSequence setTitleContentText() {
        return "统一标题";
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isStatusBarTranslucent() {
        return true;
    }


}
