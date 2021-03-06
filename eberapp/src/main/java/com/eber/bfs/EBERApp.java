package com.eber.bfs;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;

import com.eber.bfs.bean.User;
import com.eber.bfs.utils.SDCardUtils;
import com.eber.bfs.utils.SPUtil;
import com.eber.bfs.utils.VersionUtil;
import com.inuker.bluetooth.library.BluetoothContext;
import com.qxinli.umeng.UmengUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;

/**
 * Created by Administrator on 2017/4/18.
 */
public class EBERApp extends MultiDexApplication {

    public static Context sContext;
    public static SPUtil spUtil;
    public static VersionUtil versionUtil;
    public static User user;
    public static User nowUser;

    private static EBERApp instance;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        instance = this;
        spUtil = new SPUtil(getApplicationContext());
        versionUtil = new VersionUtil(getApplicationContext());

        umengInit();
        clearShareImagePath();
        BluetoothContext.set(this);
    }

    /**
     * umeng分享初始化
     */
    private void umengInit() {
        UmengUtil.init(getApplicationContext(),  true,
                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN_CIRCLE);
        UmengUtil.setKeySecretWeixin("wx04a60103924f3c38", "c58232f483fd435e7e3827563eb9d4d3");
        UmengUtil.setKeySecretSina("8792365", "2e34095589ba97ede1b5236911b5e6b0","http://sns.whalecloud.com/sina2/callback");
        UmengUtil.setKeySecretQQ("1106039737", "8EiyYudQV8etFJ9O");
    }


    /**
     * 清楚分享图片缓存
     */
    private void clearShareImagePath() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = SDCardUtils.getSDCardPath() + "/eber/ScreenImage";
                    File file = new File(path);
                    file.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
