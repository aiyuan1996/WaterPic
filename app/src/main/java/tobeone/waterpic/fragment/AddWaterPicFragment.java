package tobeone.waterpic.fragment;


import android.Manifest;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import tobeone.waterpic.PermissionListener;
import tobeone.waterpic.R;
import tobeone.waterpic.activity.AddCompanyName;
import tobeone.waterpic.activity.AddProjectName;
import tobeone.waterpic.activity.BigPictureActivity;
import tobeone.waterpic.activity.MainActivity;
import tobeone.waterpic.utils.TakePhotoView;
import tobeone.waterpic.utils.ToastUtils;

/**
 * Created by 王特 on 2017/7/7.
 */
public class AddWaterPicFragment extends Fragment  {

    public static final int ADD_PROJECT_NAME = 1;
    public static final int ADD_COMPANY_NAME = 2;
    private  View totalView;

    private android.support.v7.app.AlertDialog photoDialog;
    private static final String PHOTO_IMAGE_FILE_NAME = "WaterPic.jpg";
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int IMAGE_REQUEST_CODE = 101;
    private static final int RESULT_REQUEST_CODE = 102;
    private static final int REQUEST_BLUETOOTH_PERMISSION = 10;
    private static final String TAG = "AddWaterFragment";
    private File tempFile = null;

    private ImageView imageView;
    private LinearLayout projectNameLayout;
    private LinearLayout CompanyNameLayout;
    private LinearLayout TimeLayout;
    private TextView addProjectNameText;
    private TextView addCompanyNameText;
    private TextView addTimeText;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_water_pic, container, false);
        initView(view);
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


        imageView.setImageResource(R.drawable.default_pic);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击 图片放大
                Intent intent = new Intent(getContext(), BigPictureActivity.class);
//                Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
//                Bitmap bitmap1 = Bitmap.createBitmap(image);
//
//                intent.putExtra("pic_bitmap",bitmap1);
                startActivity(intent);
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
                return;
            } else {
            }
        } else {
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
        intent.putExtra("crop", "true");
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

        //if (resultCode == 1){
            switch (requestCode)
            {
                case ADD_PROJECT_NAME:
                    String backDataProject = data.getStringExtra("data_project");
                    addProjectNameText.setText(backDataProject);
                    break;
                case ADD_COMPANY_NAME:
                    String backDataCompany = data.getStringExtra("data_company");
                    addCompanyNameText.setText(backDataCompany);
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

           // }

        }




    }

    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
           Bitmap  bitmap = bundle.getParcelable("data");
            imageView.setImageBitmap(bitmap);
            ToastUtils.showShort(getActivity(), "添加图片成功");

        }else {
            ToastUtils.showShort(getActivity(), "添加图片失败");
        }
    }

    /**
     * Bitmap转File
     */
    public File bitmapToFile(Bitmap bitmap) {
        tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)) {
                bos.flush();
                bos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempFile;
    }



}
