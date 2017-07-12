package tobeone.waterpic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cn.bmob.v3.BmobUser;
import tobeone.waterpic.R;
import tobeone.waterpic.activity.ChangePasswordActivity;
import tobeone.waterpic.activity.LoginActivity;
import tobeone.waterpic.utils.ActivityCollector;

/**
 * Created by aiyuan on 2017/7/12.
 */

public class UserManageFragment extends Fragment{
    private ListView listView;
    private String[] user_manage_item = {"更改密码","注销登陆"};
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_manage,container,false);
        initView(view);
        return view;
    }
    private void initView(View view){
        listView = (ListView)view.findViewById(R.id.user_manage);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,user_manage_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    intent = new Intent(getActivity(),ChangePasswordActivity.class);
                    startActivity(intent);
                }else if (position == 1){
                    BmobUser.logOut();
                    intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }
}
