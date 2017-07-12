package tobeone.waterpic.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.GridView;


import org.litepal.crud.DataSupport;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import tobeone.waterpic.R;
import tobeone.waterpic.activity.GalleryActivity;
import tobeone.waterpic.adapter.SpecialGridAdapter;
import tobeone.waterpic.entity.Local.LocalWaterInfo;
import tobeone.waterpic.entity.UserEntity;
import tobeone.waterpic.entity.WaterInfoEntity;
import tobeone.waterpic.utils.ToastUtils;


/**
 * Created by 王特 on 2017/7/8.
 * 查看本地水印相册
 */
public class WaterLocalFragment extends Fragment {


    private GridView mGridView;
    private SpecialGridAdapter mSpecialGridAdapter;
    private List<LocalWaterInfo> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water_local,container,false);

        initView(view);
        return view;
    }

    private void initView(View v) {

        mGridView = (GridView) v.findViewById(R.id.mGridView);
        mList = DataSupport.findAll(LocalWaterInfo.class);
        if (mList.size()!=0){
            Log.d("LocalWater",mList.get(0).getCompanyName());
            mSpecialGridAdapter = new SpecialGridAdapter(getActivity(),mList);
            mGridView.setAdapter(mSpecialGridAdapter);


           mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
               @Override
               public boolean onItemLongClick(AdapterView<?> parent, View view,final int i, long id) {

                   AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                   builder.setItems(getResources().getStringArray(R.array.selectArray), new DialogInterface.OnClickListener() {@Override
                   public void onClick(DialogInterface dialog, int which) {
                       switch (which){
                           case 0:
                              //查看
                               Intent intent = new Intent(getActivity(), GalleryActivity.class);
                               intent.putExtra("water_info",mList.get(i));
                               startActivity(intent);
                               break;
                           case 1:
                               File tempfile = new File(mList.get(i).getPictureUri());
                               if (tempfile.exists()) {
                                   tempfile.delete();
                                   ToastUtils.showShort(getActivity(),"删除图片成功！");
                               }else{
                                   ToastUtils.showShort(getActivity(),"删除图片失败！");
                               }
                               //删除
                               break;
                           case 2:
                               //上传到云端
                               saveToServer(mList.get(i));
                               break;
                       }
                   }
                }).show();

                   return true;
               }
           });
        }else{
            ToastUtils.showLong(getActivity(),"本地相册为空！");
        }

    }

    private void saveToServer(LocalWaterInfo  temp){
        final WaterInfoEntity waterInfoEntity = new WaterInfoEntity();
        waterInfoEntity.setProjectName(temp.getProjectName());
        waterInfoEntity.setCompanyName(temp.getCompanyName());
        waterInfoEntity.setCurrentTime(temp.getCurrentTime());
        waterInfoEntity.setLocation(temp.getLocation());
        if(BmobUser.getCurrentUser() != null){
            UserEntity userEntity = BmobUser.getCurrentUser(UserEntity.class);
            waterInfoEntity.setUsername(userEntity.getUsername());
        }
        final BmobFile bmobFile = new BmobFile(new File(temp.getPictureUri()));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    waterInfoEntity.setPicture(bmobFile);
                    waterInfoEntity.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                ToastUtils.showShort(getActivity(),"上传水印图片成功");
                            }else{
                                ToastUtils.showShort(getActivity(),"创建数据失败：" + e.getMessage());
                            }
                        }
                    });
                }
            }
        });

    }


}
