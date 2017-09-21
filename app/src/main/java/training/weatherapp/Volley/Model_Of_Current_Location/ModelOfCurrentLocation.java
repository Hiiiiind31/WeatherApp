
package training.weatherapp.Volley.Model_Of_Current_Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelOfCurrentLocation {

    @SerializedName("Key")
    @Expose
    private String key;
    @SerializedName("Type")
    @Expose
    private String primaryPostalCode;
    @SerializedName("Region")
    @Expose
    private Region region;
    @SerializedName("Country")
    @Expose
    private Country country;

    public ModelOfCurrentLocation(String key, String primaryPostalCode, Region region, Country country) {
        this.key = key;
        this.primaryPostalCode = primaryPostalCode;
        this.region = region;
        this.country = country;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPrimaryPostalCode() {
        return primaryPostalCode;
    }

    public void setPrimaryPostalCode(String primaryPostalCode) {
        this.primaryPostalCode = primaryPostalCode;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
