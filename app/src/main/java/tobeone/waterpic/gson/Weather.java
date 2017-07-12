package tobeone.waterpic.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aiyuan on 2017/1/25.
 */

public class Weather {
    public String status;

    public Basic basic;

    public AQI aqi;

    public Now now;

    public Suggestion suggestion;

    public Forecast forecast;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
