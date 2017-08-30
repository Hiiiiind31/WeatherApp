
package training.weatherapp.Volley.Model_Autocom_cities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdministrativeArea {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("LocalizedName")
    @Expose
    private String localizedName;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

}
