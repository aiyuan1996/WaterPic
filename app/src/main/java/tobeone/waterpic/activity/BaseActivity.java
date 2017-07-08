package tobeone.waterpic.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import tobeone.waterpic.utils.PermissionListener;
import tobeone.waterpic.utils.ActivityCollector;

/**
 * Created by 王特 on 2017/7/7.
 */

public class BaseActivity  extends AppCompatActivity {

    private PermissionListener mlistener;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ActivityCollector.addActivity(this);
    }
    public void RequestPermission(String [] permissions,PermissionListener listener){
        mlistener = listener;
        List<String> ListPermission = new ArrayList<>();
        for (String permission: permissions) {

            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                ListPermission.add(permission);
            }
        }
        if (!ListPermission.isEmpty()){

            ActivityCompat.requestPermissions(this,ListPermission.toArray(new String[ListPermission.size()]),1);

        }else{
            listener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    List<String> deniedPermission = new ArrayList<>();
                    for (int i = 0; i <grantResults.length ; i++) {
                        int grantResult = grantResults[i];
                        String permissoin = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED){
                            deniedPermission.add(permissoin);
                        }
                    }
                    if (deniedPermission.isEmpty()){
                        mlistener.onGranted();
                    }else{
                        mlistener.onDenied(deniedPermission);
                    }
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


}
