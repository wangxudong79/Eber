package com.eber.ui.slideinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.eber.R;
import com.eber.base.BaseActivity;
import com.eber.ui.slideinfo.adapter.PageFragmentAdapter;
import com.eber.ui.slideinfo.db.ChannelDb;
import com.eber.ui.slideinfo.entity.Channel;
import com.eber.ui.slideinfo.fragment.NewsFragment;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxd on 2017/5/4.
 */

public class SlideInfoActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @ViewInject(R.id.title_content)
    private TextView title;
    private ViewPager viewPager;
    private RadioGroup rgChannel=null;
    private HorizontalScrollView hvChannel;
    private PageFragmentAdapter adapter=null;
    private List<Fragment> fragmentList=new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_slide_info);
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public void setListener() {

    }

    private void initView(){
        title.setText("体检报告");
        rgChannel=(RadioGroup)super.findViewById(R.id.rgChannel);
        viewPager=(ViewPager)super.findViewById(R.id.vpNewsList);
        hvChannel=(HorizontalScrollView)super.findViewById(R.id.hvChannel);
        rgChannel.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId) {
                        viewPager.setCurrentItem(checkedId);
                    }
                });

        viewPager.setOnPageChangeListener(this);
        initTab();
        initViewPager();
        rgChannel.check(0);

    }
    private void initTab(){
        List<Channel> channelList= ChannelDb.getSelectedChannel();
        for(int i=0;i<channelList.size();i++){
            RadioButton rb=(RadioButton)LayoutInflater.from(this).
                    inflate(R.layout.tab_rb, null);
            rb.setId(i);
            rb.setText(channelList.get(i).getName());
            RadioGroup.LayoutParams params=new
                    RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);

            rgChannel.addView(rb,params);
        }

    }
    private void initViewPager(){
        List<Channel> channelList=ChannelDb.getSelectedChannel();
        for(int i=0;i<channelList.size();i++){
            NewsFragment frag=new NewsFragment();
            Bundle bundle=new Bundle();
            bundle.putString("weburl", channelList.get(i).getWeburl());
            bundle.putString("name", channelList.get(i).getName());
            frag.setArguments(bundle);
            fragmentList.add(frag);
        }
        adapter=new PageFragmentAdapter(super.getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        //viewPager.setOffscreenPageLimit(0);
    }


    /**
     * 滑动ViewPager时调整ScroollView的位置以便显示按钮
     * @param idx
     */
    private void setTab(int idx){
        RadioButton rb=(RadioButton)rgChannel.getChildAt(idx);
        rb.setChecked(true);
        int left=rb.getLeft();
        int width=rb.getMeasuredWidth();
        DisplayMetrics metrics=new DisplayMetrics();
        super.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth=metrics.widthPixels;
        int len=left+width/2-screenWidth/2;
        hvChannel.smoothScrollTo(len, 0);//滑动ScroollView
    }
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        setTab(position);
    }



}