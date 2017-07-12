package tobeone.waterpic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import tobeone.waterpic.R;
import tobeone.waterpic.activity.GalleryActivity;
import tobeone.waterpic.adapter.SpecialGridAdapter;
import tobeone.waterpic.entity.Local.LocalWaterInfo;
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
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), GalleryActivity.class);
                    intent.putExtra("water_info",mList.get(i));
                    startActivity(intent);
                }
            });
        }else{
            ToastUtils.showLong(getActivity(),"本地相册为空！");
        }

    }

}
