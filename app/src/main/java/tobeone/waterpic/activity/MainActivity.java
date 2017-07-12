package tobeone.waterpic.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;
import tobeone.waterpic.R;
import tobeone.waterpic.entity.UserEntity;
import tobeone.waterpic.fragment.AddWaterPicFragment;
import tobeone.waterpic.fragment.LocationMapFragment;
import tobeone.waterpic.fragment.UserManageFragment;
import tobeone.waterpic.fragment.WaterLocalFragment;
import tobeone.waterpic.fragment.WaterServerFragment;
import tobeone.waterpic.utils.ImageUtil;
import tobeone.waterpic.utils.PermissionListener;
import tobeone.waterpic.utils.ToastUtils;



public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private AddWaterPicFragment addWaterPicFragment;
    private WaterLocalFragment waterLocalFragment;
    private WaterServerFragment waterServerFragment;
    private UserManageFragment userManageFragment;

    private LocationMapFragment locationMapFragment;

    private  DrawerLayout drawer;



    private CircleImageView userImage;
    private TextView tel;
    private LinearLayout nav_layout;
    private android.support.v7.app.AlertDialog photoDialog;
    private static final String PHOTO_IMAGE_FILE_NAME = "fileImg.jpg";
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int IMAGE_REQUEST_CODE = 101;
    private static final int RESULT_REQUEST_CODE = 102;
    private static final int REQUEST_BLUETOOTH_PERMISSION = 10;
    private static final String TAG = "WeatherMainActivity";
    private File tempFile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        nav_layout = (LinearLayout) navHeaderView.findViewById(R.id.nav_layout);
        userImage = (CircleImageView)navHeaderView.findViewById(R.id.profile_image);
        String telString = BmobUser.getCurrentUser().getUsername();
        tel = (TextView)navHeaderView.findViewById(R.id.telnumber);
        tel.setText(telString);
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        initview();

        initAddWaterPic();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //取消提示框
            new AlertDialog.Builder(this)
                    .setMessage("是否退出应用？")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_add_watermark:
                initAddWaterPic();
                break;
            case R.id.nav_cloud_album:
                initWaterServerFragment();
                break;
            case R.id.nav_view_album:
                initWaterLocalfragment();
                break;
            case R.id.nav_setting:
                nav_layout.setBackgroundResource(R.drawable.pugongying);
                break;
            case R.id.nav_user_manage:
                initUserManageFragment();
                break;
            case R.id.nav_weather:
                Intent intent = new Intent(MainActivity.this,WeatherMainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_map:
                initLocationMap();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initUserManageFragment(){
        getSupportActionBar().setTitle("用户管理");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (userManageFragment == null) {
            userManageFragment = new UserManageFragment();
            transaction.add(R.id.content_main, userManageFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(userManageFragment);
        transaction.commit();
    }

    private void initLocationMap() {
        getSupportActionBar().setTitle("附近地图");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (locationMapFragment == null) {
        locationMapFragment = new LocationMapFragment();
        transaction.add(R.id.content_main, locationMapFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(locationMapFragment);
        transaction.commit();


    }

    private void hideFragment(FragmentTransaction transaction){

        if (addWaterPicFragment != null){
            transaction.hide(addWaterPicFragment);
        }
        if (waterLocalFragment != null) {
            transaction.hide(waterLocalFragment);
        }
        if(waterServerFragment != null){
            transaction.hide(waterServerFragment);
        }
        if (locationMapFragment != null){
            transaction.hide(locationMapFragment);
        }
        if(userManageFragment != null){
            transaction.hide(userManageFragment);
        }


    }

    private void initAddWaterPic() {

        getSupportActionBar().setTitle("添加水印");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addWaterPicFragment == null) {
              addWaterPicFragment = new AddWaterPicFragment();
            transaction.add(R.id.content_main, addWaterPicFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(addWaterPicFragment);
        transaction.commit();
    }

    private void initWaterServerFragment() {

        getSupportActionBar().setTitle("云端水印相册");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (waterServerFragment == null) {
            waterServerFragment = new WaterServerFragment();
            transaction.add(R.id.content_main, waterServerFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(waterServerFragment);
        transaction.commit();
    }
    private void initWaterLocalfragment() {

        getSupportActionBar().setTitle("本地水印相册");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (waterLocalFragment == null) {
            waterLocalFragment = new WaterLocalFragment();
            transaction.add(R.id.content_main, waterLocalFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(waterLocalFragment);
        transaction.commit();
    }

    private void initview() {
        if (BmobUser.getCurrentUser()!=null) {
            Log.d(TAG, "BmobUser.getCurrentUser()!=null");
            UserEntity userEntity = BmobUser.getCurrentUser(UserEntity.class);
            if (userEntity.getAvatar()!=null) {
                Log.d(TAG, "userEntity.getAvatar()!=null");

                if(userEntity.getAvatar().getFileUrl() != null){
                    Log.d(TAG, "图片不为空");
                    Glide.with(MainActivity.this).load(userEntity.getAvatar().getFileUrl()).into(userImage);
                }else{
                    Log.d(TAG, "图片为空");
                }
            }
        }
    }

    /**
     * 点击头像的提示对话框
     */
    private void showDialog() {
        photoDialog = new android.support.v7.app.AlertDialog.Builder(MainActivity.this).create();
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
                toCameraAvatar();
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
     * 跳转相机去拍头像
     */
    private void toCameraAvatar() {
        RequestPermission(new String[]{ Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionListener() {
            @Override
            public void onGranted() {
                photoDialog.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // 调用系统的拍照功能
                // 判断内存卡是否可用，可用的话就进行储存
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }

            @Override
            public void onDenied(List<String> DeniedPermissionList) {
                for (String permission:DeniedPermissionList) {
                    Toast.makeText(MainActivity.this,"拒绝了权限："+permission,Toast.LENGTH_SHORT).show();
                }
            }
        });

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



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
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
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪
     * @param uri
     */
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

    /**
     * 设置icon并上传服务器
     * @param data
     */
    public void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            final Bitmap bitmap = bundle.getParcelable("data");
            ImageUtil imageUtil = new ImageUtil(PHOTO_IMAGE_FILE_NAME);
            final BmobFile bmobFile = new BmobFile(imageUtil.bitmapToFile(bitmap));

            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null) {
                        // 此时上传成功
                        UserEntity userEntity = new UserEntity();
                        userEntity.setAvatar(bmobFile);// 获取文件并赋值给实体类
                        BmobUser bmobUser = BmobUser.getCurrentUser();
                        userEntity.update(bmobUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    userImage.setImageBitmap(bitmap);
                                    ToastUtils.showShort(MainActivity.this, getString(R.string.avatar_editor_success));
                                } else {
                                    ToastUtils.showShort(MainActivity.this, getString(R.string.avatar_editor_failure));
                                }
                            }
                        });
                    } else {
                        ToastUtils.showShort(MainActivity.this, getString(R.string.avatar_editor_failure));
                    }
                    // 既然已经设置了图片，我们原先的就应该删除
                    if (tempFile != null) {
                        tempFile.delete();
                        //LogUtils.i("JAVA", "tempFile已删除");
                    }
                }
                @Override
                public void onProgress(Integer value) {
                    // 返回的上传进度（百分比）
                }
            });

        }
    }

}
