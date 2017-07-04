package tobeone.waterpic.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import tobeone.waterpic.R;
import tobeone.waterpic.entity.UserEntity;
import tobeone.waterpic.utils.ToastUtils;

/**
 * 修改密码
 * Created by gc on 2017/2/10.
 */
public class ChangePasswordActivity extends AppCompatActivity {

    @BindView(R.id.et_now_pass) EditText mNowPass;
    @BindView(R.id.et_new_pass) EditText mNewPass;
    @BindView(R.id.et_new_password) EditText mNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_change_password)
    public void onChangePassword(View view) {
        // 1.获取输入框的值
        String now = mNowPass.getText().toString().trim();
        String news = mNewPass.getText().toString().trim();
        String newPassword = mNewPassword.getText().toString();
        // 2.判断是否为空
        if(!TextUtils.isEmpty(now) & !TextUtils.isEmpty(news) & !TextUtils.isEmpty(newPassword)){
            // 3.判断两次新密码是否一致
            if(news.equals(newPassword)) {
                // 4.重置密码
                UserEntity.updateCurrentUserPassword(now, news, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null) {
                            ToastUtils.showShort(ChangePasswordActivity.this,
                                    getString(R.string.reset_successfully));
                            finish();
                        } else {
                            ToastUtils.showShort(ChangePasswordActivity.this,
                                    getString(R.string.reset_failed));
                        }
                    }
                });
            }else {
                ToastUtils.showShort(ChangePasswordActivity.this,
                        getString(R.string.buyizhi));
            }
        }else {
            ToastUtils.showShort(ChangePasswordActivity.this, getString(R.string.text_tost_empty));
        }
    }

}
