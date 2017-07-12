package tobeone.waterpic.entity.Local;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 王特 on 2017/7/11.
 */

public class LocalWaterInfo extends DataSupport implements Serializable {

    private String projectName;
    private String companyName;
    private String currentTime;
    private String location;
    private String pictureUri;


    public String getProjectName() {
        return projectName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public String getLocation() {
        return location;
    }


    public String getPictureUri() {
        return pictureUri;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public void setPictureUri(String pictureUri) {
        this.pictureUri = pictureUri;
    }



    @Override
    public String toString() {
        return getProjectName() + "\n"
                + getCompanyName() + "\n"
                + getCurrentTime();
    }

}
