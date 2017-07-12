package tobeone.waterpic.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;
import tobeone.waterpic.R;
import tobeone.waterpic.activity.GalleryActivity;
import tobeone.waterpic.adapter.ImageAdapter;
import tobeone.waterpic.entity.Local.LocalWaterInfo;
import tobeone.waterpic.entity.UserEntity;
import tobeone.waterpic.entity.WaterInfoEntity;
import tobeone.waterpic.utils.GlideUtils;
import tobeone.waterpic.utils.ToastUtils;


/**
 * Created by 王特 on 2017/7/12.
 */

public class WaterServerFragment extends Fragment{
    private Gallery gallery;
    private ImageView iv;

    private ImageAdapter imageAdapter;

    private  int position = -1;


    private List<WaterInfoEntity> querylist = new ArrayList<WaterInfoEntity>();
    private static final String TAG = "WaterServerFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water_server,container,false);
        queryObjects(view);

        return view;
    }

    private void initView(View view){
        iv = (ImageView) view.findViewById(R.id.imageView);
        gallery = (Gallery) view.findViewById(R.id.gallery);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position != -1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setItems(getResources().getStringArray(R.array.Array), new DialogInterface.OnClickListener() {@Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){

                            //下载图片到本地
                            case 0:
                                OkHttpClient client = new OkHttpClient();
                                final Request request = new Request.Builder()
                                        .get()
                                        .url(querylist.get(position).getPicture().getFileUrl())
                                        .build();
                                Call call = client.newCall(request);
                                call.enqueue(new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        ToastUtils.showShort(getActivity(),"下载失败，请稍后再试！");
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {

                                        //拿到字节流
                                        InputStream is = response.body().byteStream();

                                        int len = 0;

                                        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/WaterMark";
                                        File tempfile = new File(filePath);
                                        if (!tempfile.exists()) {
                                            tempfile.mkdir();
                                        }
                                        File file = new File(filePath, "WaterMark"+querylist.get(position).getProjectName() + System.currentTimeMillis() + "pic.jpg");

                                        FileOutputStream fos = new FileOutputStream(file);
                                        byte[] buf = new byte[128];

                                        while ((len = is.read(buf)) != -1){
                                            fos.write(buf, 0, len);
                                        }

                                        fos.flush();
                                        //关闭流
                                        fos.close();
                                        is.close();
                                        LocalWaterInfo localWaterInfo = new LocalWaterInfo();
                                        localWaterInfo.setProjectName(querylist.get(position).getProjectName());
                                        localWaterInfo.setCompanyName(querylist.get(position).getCompanyName());
                                        localWaterInfo.setCurrentTime(querylist.get(position).getCurrentTime());
                                        localWaterInfo.setLocation(querylist.get(position).getLocation());
                                        localWaterInfo.setPictureUri(file.getPath());
                                        ToastUtils.showShort(getActivity(),"水印图片保存在：" + file.getPath());
                                        localWaterInfo.save();
                                    }
                                });

                                break;
                        }
                    }
                    }).show();


                }





        }
        });

        if(querylist.size()!=0){
            imageAdapter = new ImageAdapter(getActivity(),querylist);
            gallery.setAdapter(imageAdapter);
            gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    GlideUtils.loadImageView(getActivity(),querylist.get(i).getPicture().getFileUrl(),iv);
                    position = i;
                }
            });
        }else{
            ToastUtils.showLong(getActivity(),"云端相册为空！，请先上传照片");
        }
    }
    public void queryObjects(final  View view) {
        final BmobQuery<WaterInfoEntity> bmobQuery = new BmobQuery<WaterInfoEntity>();
        UserEntity userEntity = BmobUser.getCurrentUser(UserEntity.class);
        bmobQuery.addWhereEqualTo("username", userEntity.getUsername());
        bmobQuery.setLimit(50);
        bmobQuery.order("createdAt");
        //先判断是否有缓存
        boolean isCache = bmobQuery.hasCachedResult(WaterInfoEntity.class);
        if (isCache) {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 先从缓存取数据，如果没有的话，再从网络取。
        } else {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则先从网络中取
        }
//		observable形式
        bmobQuery.findObjectsObservable(WaterInfoEntity.class)
                .subscribe(new Subscriber<List<WaterInfoEntity>>() {
                    List<WaterInfoEntity> mylist = new ArrayList<WaterInfoEntity>();

                    @Override
                    public void onCompleted() {
                        querylist = mylist;
                        initView(view);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "出错");
                    }

                    @Override
                    public void onNext(List<WaterInfoEntity> waterInfoEntities) {
                        mylist = waterInfoEntities;
                    }
                });
    }


}
