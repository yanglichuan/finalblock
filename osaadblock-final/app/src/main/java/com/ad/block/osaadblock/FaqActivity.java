package com.ad.block.osaadblock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.ad.block.osaadblock.utils.CommonUtils;

public class FaqActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getUmPageName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected boolean dealWithStatusBar() {
        return true;
    }

    protected void adjustHeight(boolean bbar){
        View view = this.findViewById(R.id.statusbar_sp);
        if(bbar){
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = CommonUtils.getStatusHeight(FaqActivity.this);
            view.setLayoutParams(params);
        }else{
            view.setVisibility(View.GONE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_faq;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gocomment:
                CommonUtils.jumpMarket(mContext);
                break;
            case R.id.faq:

                break;
        }
    }

    public static void openActivity(Context context){
        context.startActivity(new Intent(context, FaqActivity.class));
    }
}
