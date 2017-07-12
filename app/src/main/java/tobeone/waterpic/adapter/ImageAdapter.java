package tobeone.waterpic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import tobeone.waterpic.R;

/**
 * 自定义ImageAdapter适配器
 * 要实现Gallery画廊控件功能，需要一个容器来存放Gallery显示的图片。
 * 我们可以使用一个继承自BaseAdapter类的派生类ImageAdapter来装这些图片。
 * Created by aiyuan on 2017/7/11.
 */

public class ImageAdapter extends BaseAdapter{
    //上下文对象，提供给外界实例化ImageAdapter
    private Context mContext;
    //待加载到Gallery之中的图片id数组
    public static int images[] = {
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8
    };
    //ImageAdapter构造方法
    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }
    //获得图片的个数
    @Override
    public int getCount() {
        return images.length;
    }
    //获取对应索引上的图片
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return images[position];
    }
    //获取对应图片的索引id
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    //获取适配器中指定位置的视图对象
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv = new ImageView(mContext);
        iv.setImageResource(images[position % images.length]);
        iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iv.setLayoutParams(new Gallery.LayoutParams(300, 300));
        return iv;
    }
}
