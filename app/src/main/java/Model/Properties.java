package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Properties {

    @SerializedName("mag")
    @Expose
    private Double mag;

    @SerializedName("place")
    @Expose
    private String place;

    @SerializedName("time")
    @Expose
    private Long time;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("tsunami")
    @Expose
    private int tsunami;


    public Double getMag() {
        return mag;
    }

    public void setMag(Double mag) {
        this.mag = mag;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTsunami() {
        return tsunami;
    }

    public void setTsunami(int tsunami) {
        this.tsunami = tsunami;
    }

    @Override
    public String toString() {
        return "Properties{" +
                "mag=" + mag +
                ", place='" + place + '\'' +
                ", time=" + time +
                ", url='" + url + '\'' +
                ", tsunami=" + tsunami +
                '}';
    }
}
