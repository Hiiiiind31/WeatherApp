
package training.weatherapp.Volley.Model_Of_Current_Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoPosition {

    @SerializedName("Latitude")
    @Expose
    private Float latitude;
    @SerializedName("Longitude")
    @Expose
    private Float longitude;

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }


}
