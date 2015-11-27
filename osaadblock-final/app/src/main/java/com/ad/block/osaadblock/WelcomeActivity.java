package com.ad.block.osaadblock;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.ad.block.osaadblock.utils.RecordFileUtils;
import java.util.ArrayList;

import info.hoang8f.widget.FButton;
import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends BaseActivity {
    private FButton button;
    private ViewPager viewPager;
    private ArrayList<View> pagerViews;

    private View rl_waiting;
    private View rl_guide;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rl_guide = findViewById(R.id.rl_guide);
        rl_waiting = findViewById(R.id.rl_waiting);

        //
        if (!RecordFileUtils.getInstance(
                this.getApplicationContext()).getBooleanData(Constants.haveguided, false)) {

            //设置开机启动
            RecordFileUtils.getInstance(mContext.getApplicationContext())
                                        .setBooleanData(Constants.AUTOSTARTUP,true);


            rl_guide.setVisibility(View.VISIBLE);
            rl_waiting.setVisibility(View.GONE);

            viewPager = (ViewPager) findViewById(R.id.viewpager);
            button = (FButton) findViewById(R.id.enter_button);
            button.setOnClickListener(this);

            pagerViews = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                View view = View.inflate(mContext,R.layout.item_yindao,null);
                if (i == 0) {
                    view.setBackgroundResource(R.drawable.yd1);
                    ((TextView)view.findViewById(R.id.tip)).setText(R.string.ydtip1);
                } else if (i == 1) {
                    view.setBackgroundResource(R.drawable.yd2);
                    ((TextView)view.findViewById(R.id.tip)).setText(R.string.ydtip2);
                } else if (i == 2) {
                    view.setBackgroundResource(R.drawable.yd3);
                    ((TextView)view.findViewById(R.id.tip)).setText(R.string.ydtip3);
                }else if (i == 3) {
                    view.setBackgroundResource(R.drawable.yd4);
                    ((TextView)view.findViewById(R.id.tip)).setText(R.string.ydtip4);
                }
                pagerViews.add(view);
            }

            final WelcomeAdapter adapter = new WelcomeAdapter();
            viewPager.setAdapter(adapter);
            CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
            indicator.setViewPager(viewPager);

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (position == (adapter.getCount() - 1)) {
                        //
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                buttonShow(button, 500, 0);
                            }
                        },300);

                    } else {
                        if (!beDoingAnim && button.getVisibility() == View.VISIBLE) {
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    buttonHide(button, 0, 500);
                                }
                            },300);
                        }
                    }
                }
                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        } else {
            rl_guide.setVisibility(View.GONE);
            rl_waiting.setVisibility(View.VISIBLE);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MainActivity.openActivity(WelcomeActivity.this);
                    WelcomeActivity.this.finish();
                }
            }, 1000);
        }
    }


    private Handler mHandler = new Handler();

    @Override
    protected String getUmPageName() {
        return this.getClass().getSimpleName();
    }

    private boolean beDoingAnim = false;

    private void buttonShow(final View view, float from, float to) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", from, to);
        objectAnimator.addListener(new AnimLL() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                beDoingAnim = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                beDoingAnim = false;
            }
        });
        objectAnimator.setDuration(300);
        objectAnimator.setRepeatCount(0);
        objectAnimator.setInterpolator(new OvershootInterpolator());
        objectAnimator.start();
    }


    private void buttonHide(final View view, float from, float to) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "translationY", from, to);
        objectAnimator.addListener(new AnimLL() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                beDoingAnim = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                beDoingAnim = false;
            }
        });
        objectAnimator.setDuration(300);
        objectAnimator.setRepeatCount(0);
        objectAnimator.setInterpolator(new OvershootInterpolator());
        objectAnimator.start();
    }


    protected boolean dealWithStatusBar() {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enter_button:
                RecordFileUtils.getInstance(
                        mContext.getApplicationContext()).setBooleanData(Constants.haveguided, true);
                MainActivity.openActivity(mContext);

                //
                finish();
                break;
        }

    }


    class WelcomeAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return pagerViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pagerViews.get(position));
            return pagerViews.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(pagerViews.get(position));
        }
    }


}
