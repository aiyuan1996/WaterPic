package tobeone.waterpic.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import tobeone.waterpic.R;
import tobeone.waterpic.activity.AddCompanyName;
import tobeone.waterpic.activity.AddProjectName;
import tobeone.waterpic.activity.BigPictureActivity;
import tobeone.waterpic.activity.WaterMarkSettingActivity;
import tobeone.waterpic.app.App;
import tobeone.waterpic.entity.Local.LocalWaterInfo;
import tobeone.waterpic.entity.UserEntity;
import tobeone.waterpic.entity.WaterInfoEntity;
import tobeone.waterpic.entity.WatermarkInformationEntity;
import tobeone.waterpic.utils.ToastUtils;

public class AddWaterPicFragment extends Fragment {

    public static final int ADD_PROJECT_NAME = 1;
    public static final int ADD_COMPANY_NAME = 2;
    private  View totalView;
    private String waterPicPath;

    public void setWaterPicPath(String waterPicPath) {
        this.waterPicPath = waterPicPath;
    }

    public String getWaterPicPath() {

        return waterPicPath;
    }

    private android.support.v7.app.AlertDialog photoDialog;
    private static final String PHOTO_IMAGE_FILE_NAME = "pic.jpg";
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int IMAGE_REQUEST_CODE = 101;
    private static final int RESULT_REQUEST_CODE = 102;

    private static final int QEQUEST_BY_WATER_SETING = 300;
    private static final int REQUEST_BLUETOOTH_PERMISSION = 10;
    private static final String TAG = "AddWaterFragment";
    private File tempFile = null;

    private ImageView imageView;
    private LinearLayout projectNameLayout;
    private LinearLayout CompanyNameLayout;
    private LinearLayout TimeLayout;
    private LinearLayout LocationLayout;

    private TextView addProjectNameText;
    private TextView addCompanyNameText;
    private TextView addTimeText;
    private TextView addLocationText;


    private Button settingBtn;
    private Button saveBtn;
    private Button saveToServerBtn;
    private Button shareBtn;

    private Bitmap srcBitmap;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    public WatermarkInformationEntity watermarkInformationEntity = new WatermarkInformationEntity();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_water_pic, container, false);
        initView(view);
        getLocation();
        totalView = view;
        return view;
    }

    private void initView(final View view) {
        imageView = ((ImageView) view.findViewById(R.id.imageView));
        projectNameLayout = (LinearLayout) view.findViewById(R.id.project_name_layout);
        addProjectNameText = (TextView) view.findViewById(R.id.text_project_name);
        CompanyNameLayout = (LinearLayout) view.findViewById(R.id.compay_name_layout);
        addCompanyNameText = (TextView) view.findViewById(R.id.text_bulid_compay);
        TimeLayout = (LinearLayout) view.findViewById(R.id.time_now_layout);
        addTimeText = (TextView) view.findViewById(R.id.text_now_time);
        LocationLayout = (LinearLayout) view.findViewById(R.id.location_now_layout);
        addLocationText = (TextView) view.findViewById(R.id.text_now_location);

        settingBtn = (Button) view.findViewById(R.id.btn_setting);
        saveBtn = (Button) view.findViewById(R.id.btn_save);

        saveToServerBtn = (Button) view.findViewById(R.id.btn_save_cloud);
        shareBtn = (Button) view.findViewById(R.id.btn_share);

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getDrawable()==null){
                    Toast.makeText(getActivity(),"您还未选择图片，请先进行图片的选择",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(getActivity(), WaterMarkSettingActivity.class);
                    intent.putExtra("src_bitmap",srcBitmap);
                    if (!addProjectNameText.getText().toString().trim().equals("")){
                        watermarkInformationEntity.setProjectName(addProjectNameText.getText().toString().trim());
                    }
                    if (!addCompanyNameText.getText().toString().trim().equals("")){
                        watermarkInformationEntity.setConpanyName(addCompanyNameText.getText().toString().trim());
                    }
                         Date date = new Date();
                         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                         String s = sdf.format(date);
                        watermarkInformationEntity.setNowTime(s);
                    if(!addLocationText.getText().toString().trim().equals("")){
                        watermarkInformationEntity.setLocation(addLocationText.getText().toString().trim());
                    }
                    intent.putExtra("water_info",watermarkInformationEntity.toString());
                     startActivityForResult(intent,QEQUEST_BY_WATER_SETING);

                    //startActivity(intent);
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToLocalhost();
            }
        });

        saveToServerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToServer();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePhoto(getWaterPicPath());
            }
        });


        LocationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
            }
        });




        //imageView.setImageResource(R.drawable.default_pic);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getDrawable()!=null){
                    //点击 图片放大
                    Intent intent = new Intent(getContext(), BigPictureActivity.class);
                    Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    Bitmap bitmap1 = Bitmap.createBitmap(image);
                    intent.putExtra("pic_bitmap",bitmap1);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(),"请添加图片",Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //长按 图片进入选择菜单
                showDialog();

                return true;
            }
        });
        projectNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddProjectName.class);
                startActivityForResult(intent ,ADD_PROJECT_NAME);
            }
        });
        CompanyNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddCompanyName.class);
                startActivityForResult(intent ,ADD_COMPANY_NAME);
            }
        });
        TimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String s = sdf.format(date);
                addTimeText.setText(s);
            }
        });

    }

    // 分享照片
    private void SharePhoto(String photoUri) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        File file = new File(photoUri);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        shareIntent.setType("image/jpeg");
        startActivity(shareIntent);
    }

    private void saveToLocalhost() {
         LocalWaterInfo localWaterInfo = new LocalWaterInfo();
        localWaterInfo.setProjectName(watermarkInformationEntity.getProjectName());
        localWaterInfo.setCompanyName(watermarkInformationEntity.getConpanyName());
        localWaterInfo.setCurrentTime(watermarkInformationEntity.getNowTime());
        localWaterInfo.setLocation(watermarkInformationEntity.getLocation());
        imageView.setDrawingCacheEnabled(true);
        Bitmap waterbitmap=imageView.getDrawingCache();
        File file = bitmapToFile(waterbitmap);
        localWaterInfo.setPictureUri(file.getPath());
        setWaterPicPath(file.getPath());
        ToastUtils.showShort(getActivity(),"水印图片保存在：" + file.getPath());
        localWaterInfo.save();
    }
    private void saveToServer(){
        final WaterInfoEntity waterInfoEntity = new WaterInfoEntity();
        waterInfoEntity.setProjectName(watermarkInformationEntity.getProjectName());
        waterInfoEntity.setCompanyName(watermarkInformationEntity.getConpanyName());
        waterInfoEntity.setCurrentTime(watermarkInformationEntity.getNowTime());
        waterInfoEntity.setLocation(watermarkInformationEntity.getLocation());
        if(BmobUser.getCurrentUser() != null){
            UserEntity userEntity = BmobUser.getCurrentUser(UserEntity.class);
            waterInfoEntity.setUsername(userEntity.getUsername());
        }
        imageView.setDrawingCacheEnabled(true);
        final Bitmap waterbitmap=imageView.getDrawingCache();
        final BmobFile bmobFile = new BmobFile(bitmapToFile(waterbitmap));
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
                                Log.d(TAG, "---->>>" + e.getMessage());
                            }
                        }
                    });
                }
            }
        });

    }



    /**
     * Bitmap转File
     */
    public File bitmapToFile(Bitmap bitmap) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/WaterMark";
        File tempfile = new File(filePath);
        if (!tempfile.exists()) {
                tempfile.mkdir();
                }
        File file = new File(filePath, "WaterMark"+addProjectNameText.getText().toString().trim() + System.currentTimeMillis() + PHOTO_IMAGE_FILE_NAME);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)) {
                bos.flush();
                bos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void getLocation() {

        //初始化定位
        mLocationClient = new AMapLocationClient(App.getAppIntance());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    private class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    addLocationText.setText(aMapLocation.getAddress());
                    Log.e("位置：", aMapLocation.getAddress());
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    }

    private void showDialog() {
        photoDialog = new android.support.v7.app.AlertDialog.Builder(getActivity()).create();
        photoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        photoDialog.show();
        Window window = photoDialog.getWindow();
        window.setContentView(R.layout.dialog_photo); // 修改整个dialog窗口的显示
        window.setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams lp = photoDialog.getWindow().getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        lp.width = dm.widthPixels;
        photoDialog.getWindow().setAttributes(lp); // 设置宽度

        photoDialog.findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toCamera();
            }
        });
        photoDialog.findViewById(R.id.btn_picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPicture();
            }
        });
        photoDialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoDialog.dismiss();
            }
        });
    }


    /**
     * 跳转相机
     */
    private void toCamera() {
        requestWESPermission(); // 安卓6.0以上需要申请权限
        photoDialog.dismiss();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 调用系统的拍照功能
        // 判断内存卡是否可用，可用的话就进行储存
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);

    }
    /**
     * 动态申请权限
     */
    private void requestWESPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                // 判断是否需要 向用户解释，为什么要申请该权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    ToastUtils.showShort(getActivity(),"Need write external storage permission.");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_BLUETOOTH_PERMISSION);

            }
        }
    }


    /**
     * 跳转相册
     */
    private void toPicture() {
        photoDialog.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }
    private void startPhotoZoom(Uri uri) {
        if (uri == null) {
            //LogUtils.e("JAVA", "裁剪uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "false");
        // 裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        // 发送数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if (resultCode == skin_5){
            switch (requestCode)
            {
                case ADD_PROJECT_NAME:
                    if (data != null){

                        String backDataProject = data.getStringExtra("data_project");
                        addProjectNameText.setText(backDataProject);
                    }
                    break;
                case ADD_COMPANY_NAME:
                    if (data!=null){
                        String backDataCompany = data.getStringExtra("data_company");
                        addCompanyNameText.setText(backDataCompany);
                    }
                    break;
                case IMAGE_REQUEST_CODE: // 相册数据
                    if (data != null) {
                        startPhotoZoom(data.getData());
                    }
                    break;
                case CAMERA_REQUEST_CODE: // 相机数据
                    tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE: // 有可能点击舍弃
                    if (data != null) {
                        // 拿到图片设置, 然后需要删除tempFile
                        setImageToView(data);
                    }
                    break;
                case QEQUEST_BY_WATER_SETING:
                        if (data != null) {

                            Bitmap bitmap =data.getParcelableExtra("r_pic_bitmap");
                            imageView.setImageBitmap(bitmap);
                            imageView.invalidate();
                        }
                    break;

           // }

        }




    }

    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
           Bitmap  bitmap = bundle.getParcelable("data");
            imageView.setImageBitmap(bitmap);
            srcBitmap = bitmap;
            ToastUtils.showShort(getActivity(), "添加图片成功");

        }else {
            ToastUtils.showShort(getActivity(), "添加图片失败");
        }
    }
}
