package aiyuan1996.cn.weather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aiyuan on 2017/1/25.
 */

public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }


}
