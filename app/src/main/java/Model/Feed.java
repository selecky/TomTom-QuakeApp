package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Feed {

    @SerializedName("features")
    @Expose
    private ArrayList<Features> features;


    public ArrayList<Features> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Features> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "features=" + features +
                '}';
    }

}
