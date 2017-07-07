package tobeone.waterpic.app;

import android.app.Application;

import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import tobeone.waterpic.utils.ConfigUtils;
import tobeone.waterpic.utils.ConstantUtils;

/**
 * Created by gc on 2017/1/16.
 */
public class App extends Application {


    public static App app;


    public static App getAppIntance() {
        return app;
    }



    @Override
    public void onCreate() {
        app = this;
        super.onCreate();

        // 读取配置文件
        ConfigUtils.getInstance().readConfig();
        // 依赖注入框架ButterKnife
        ButterKnife.setDebug(butterknife.BuildConfig.DEBUG);

        // 初始化Bmob
        if (!ConstantUtils.BMOB_APP_ID.isEmpty()) {
            BmobConfig config =new BmobConfig.Builder(this)
                    .setApplicationId(ConstantUtils.BMOB_APP_ID)// 设置appkey
                    .setConnectTimeout(30)// 请求超时时间（单位为秒）：默认15s
                    .setUploadBlockSize(1024*1024)// 文件分片上传时每片的大小（单位字节），默认512*1024
                    .setFileExpiration(2500)// 文件的过期时间(单位为秒)：默认1800s
                    .build();
            Bmob.initialize(config);
        }
    }

}