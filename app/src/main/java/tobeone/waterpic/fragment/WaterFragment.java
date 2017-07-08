package tobeone.waterpic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import tobeone.waterpic.R;


/**
 * Created by 王特 on 2017/7/8.
 * 查看水印相册
 */
public class WaterFragment extends Fragment {


    private GridView mGridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water,container,false);
        initView(view);
        return view;
    }

    private void initView(View v) {
        mGridView = (GridView) v.findViewById(R.id.mGridView);


    }
}
