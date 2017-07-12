package aiyuan1996.cn.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aiyuan on 2017/1/25.
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More{
        @SerializedName("txt")
        public String info;
    }
}
