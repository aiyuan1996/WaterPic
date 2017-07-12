package tobeone.waterpic.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import rx.Subscriber;
import tobeone.waterpic.R;
import tobeone.waterpic.adapter.ImageAdapter;
import tobeone.waterpic.entity.UserEntity;
import tobeone.waterpic.entity.WaterInfoEntity;


/**
 * Created by aiyuan on 2017/7/11.
 */

public class WaterServerFragment extends Fragment{
    private Gallery gallery;
    private ImageView iv;
    private String projectName;
    private String companyName;
    //private String[] picUrls;
    private ArrayList<String> arraylist = new ArrayList<String>();
    private Bitmap[] cloudPics;
    private static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    private static final String TAG = "WaterServerFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water_server,container,false);
        queryObjects();
        Log.d(TAG, "onCreateView: 111111");
        Log.d(TAG, arraylist.size()+"--->>>");

        Log.d(TAG, "onCreateView: 3333333333");
        //Log.d(TAG, "hello" + picUrls.length);
        //cloudPics = new Bitmap[picUrls.length];
 //       new Thread(runnable).start();  //启动子线程
        //cloudPics = getBitmap(picUrls);
        initView(view);
        for(int i = 0;i < arraylist.size();i++){
            Log.d(TAG, "onCreateView: 222222");
            Log.d(TAG, arraylist.get(i));
        }
        return view;
    }
    public Bitmap[] getBitmap(String[] url) {
        Bitmap bm[] = new Bitmap[url.length];
        try {
            for(int i = 0;i <url.length;i++){
                URL iconUrl = new URL(url[i]);
                URLConnection conn = iconUrl.openConnection();
                HttpURLConnection http = (HttpURLConnection) conn;
                int length = http.getContentLength();

                conn.connect();
                // 获得图像的字符流
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is, length);
                bm[i] = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();// 关闭流
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public void queryObjects(){
        final BmobQuery<WaterInfoEntity> bmobQuery	 = new BmobQuery<WaterInfoEntity>();
        UserEntity userEntity = BmobUser.getCurrentUser(UserEntity.class);
        bmobQuery.addWhereEqualTo("username", userEntity.getUsername());
        bmobQuery.setLimit(50);
        bmobQuery.order("createdAt");
        Log.d(TAG, "1111112222");
        //先判断是否有缓存
        boolean isCache = bmobQuery.hasCachedResult(WaterInfoEntity.class);
        if(isCache){
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);	// 先从缓存取数据，如果没有的话，再从网络取。
        }else{
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);	// 如果没有缓存的话，则先从网络中取
        }
//		observable形式
        bmobQuery.findObjectsObservable(WaterInfoEntity.class)
                .subscribe(new Subscriber<List<WaterInfoEntity>>() {
                    ArrayList<String> querylist = new ArrayList<String>();
                    @Override
                    public void onCompleted() {
                        arraylist = querylist;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "出错");
                    }

                    @Override
                    public void onNext(List<WaterInfoEntity> waterInfoEntities) {
                        Log.d(TAG, "onNext: 查询成功");
                        Log.d(TAG, waterInfoEntities.size()+"");
                        for(WaterInfoEntity waterInfoEnerty : waterInfoEntities){
                            projectName = waterInfoEnerty.getProjectName();
                            companyName = waterInfoEnerty.getCompanyName();
                            Log.d(TAG, waterInfoEnerty.getPicture().getFileUrl());
                            querylist.add(waterInfoEnerty.getPicture().getFileUrl());
                            Log.d(TAG, "44444444444");
                        }
                    }
                });
    }
    private void initView(View view){
        iv = (ImageView) view.findViewById(R.id.imageView);
        iv.setImageResource(ImageAdapter.images[0]);
        //iv.setImageBitmap(ImageAdapter.images[0]);
        gallery = (Gallery) view.findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(getActivity()));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //iv.setImageBitmap(ImageAdapter.images[position]);
                iv.setImageResource(ImageAdapter.images[position]);
                Toast.makeText(getActivity(), "点击是图片"+(position+1), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
