package tobeone.waterpic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import tobeone.waterpic.R;
import tobeone.waterpic.entity.UserEntity;
import tobeone.waterpic.utils.CustomDialog;
import tobeone.waterpic.utils.SPUtils;
import tobeone.waterpic.utils.ToastUtils;

/**
 * 用户登录页
 * Created by aiyuan on 2017/2/20
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.et_phone) EditText mPhone;
    @BindView(R.id.et_password) EditText mPassword;
    @BindView(R.id.keep_password) CheckBox keepPassword;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        BmobUser bmobUser = BmobUser.getCurrentUser();

        if (bmobUser!=null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //intent.putExtra("tel",phone);
            startActivity(intent);
            finish();
        }

        initView();
    }

    private void initView() {
        boolean isKeep = SPUtils.getBoolean(this, "keeppass", false);
        keepPassword.setChecked(isKeep);
        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_loading, R.style.Theme_dialog, Gravity.CENTER, R.style.pop_anim_style);
        dialog.setCancelable(false);
        if (isKeep) {
            String phone = SPUtils.getString(this, "phone", "");
            String password = SPUtils.getString(this, "password", "");
            mPhone.setText(phone);
            mPassword.setText(password);
        }
    }

    @OnClick(R.id.tv_forget)
    public void onForget(View view) {
        Log.d(TAG, "onForget: ");
        startActivity(new Intent(this, FindPasswordActivity.class));
    }

    @OnClick(R.id.tv_registered)
    public void onRegistered(View view) {
        Log.d(TAG, "onRegistered: ");
        startActivity(new Intent(this, RegisteredActivity.class));
    }

    @OnClick(R.id.btn_login)
    public void onLogin(View view) {
        final String phone = mPhone.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        Log.d(TAG, "onLogin: ");
        if (!TextUtils.isEmpty(phone) & !TextUtils.isEmpty(password)) {
            dialog.show();
            // 登录
            BmobUser.loginByAccount(phone, password, new LogInListener<UserEntity>() {
                @Override
                public void done(UserEntity user, BmobException e) {
                    dialog.dismiss();
                    if (user != null) {
                        Log.d(TAG, "用户登陆成功");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("tel",phone);
                        startActivity(intent);
                        finish();
                    } else {
                        ToastUtils.showShort(getApplicationContext(), getString(R.string.text_login_failure)+ e.toString());
                    }
                }
            });
        } else {
            ToastUtils.showShort(getApplicationContext(), getString(R.string.text_tost_empty));
        }
    }
    /**
     * 假设我现在输入用户名和密码，但是我不点击登录，而是直接退出了
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 保存状态
        SPUtils.putBoolean(this, "keeppass", keepPassword.isChecked());
        // 是否记住密码
        if (keepPassword.isChecked()) {
            // 记住用户名和密码
            SPUtils.putString(this, "phone", mPhone.getText().toString().trim());
            SPUtils.putString(this, "password", mPassword.getText().toString().trim());
        } else {
            SPUtils.remove(this, "phone");
            SPUtils.remove(this, "password");
        }

    }
}