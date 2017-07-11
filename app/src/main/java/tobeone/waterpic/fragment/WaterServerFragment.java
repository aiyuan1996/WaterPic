package tobeone.waterpic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import tobeone.waterpic.R;
import tobeone.waterpic.adapter.ImageAdapter;

/**
 * Created by aiyuan on 2017/7/11.
 */

public class WaterServerFragment extends Fragment{
    private Gallery gallery;
    private ImageView iv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water_server,container,false);
        initView(view);
        return view;
    }
    private void initView(View view){
        iv = (ImageView) view.findViewById(R.id.imageView);
        iv.setImageResource(ImageAdapter.images[0]);
        gallery = (Gallery) view.findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(getActivity()));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                iv.setImageResource(ImageAdapter.images[position]);
                Toast.makeText(getActivity(), "点击是图片"+(position+1), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
