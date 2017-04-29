package com.eber.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 首次启动滑屏适配器 是是是
 */
public class GuidePageAdapter extends PagerAdapter {

    private List<View> viewList;

    public GuidePageAdapter(List<View> viewList) {
        this.viewList = viewList;
    }

    /**
     * @return 返回页面的个数
     */
    @Override
    public int getCount() {
        if (viewList != null){
            return viewList.size();
        }
        return 0;
    }

    /**
     * 判断对象是否生成界面
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化position位置的界面
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));
        return viewList.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }
}
