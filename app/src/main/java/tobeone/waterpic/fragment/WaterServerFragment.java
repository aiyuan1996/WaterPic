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

import com.bumptech.glide.Glide;

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
import tobeone.waterpic.utils.GlideUtils;
import tobeone.waterpic.utils.ToastUtils;


/**
 * Created by 王特 on 2017/7/12.
 */

public class WaterServerFragment extends Fragment{
    private Gallery gallery;
    private ImageView iv;

    private ImageAdapter imageAdapter;


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


        imageAdapter = new ImageAdapter(getActivity(),querylist);
        ToastUtils.showShort(getActivity(),""+querylist.size());
        gallery.setAdapter(imageAdapter);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GlideUtils.loadImageView(getActivity(),querylist.get(position).getPicture().getFileUrl(),iv);
            }
        });


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
