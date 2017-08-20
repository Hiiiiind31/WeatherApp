package training.weatherapp.RecycleLists.Models;

/**
 * Created by hindahmed on 16/08/17.
 */

public class Temp_model_days {


    private String d_Date;
    private String d_Image;
    private String d_Max_Temp;
    private String d_Min_Temp;

    public Temp_model_days(String d_Date, String d_Image, String d_Max_Temp, String d_Min_Temp) {
        this.d_Date = d_Date;
        this.d_Image = d_Image;
        this.d_Max_Temp = d_Max_Temp;
        this.d_Min_Temp = d_Min_Temp;
    }

    public String getD_Date() {
        return d_Date;
    }

    public void setD_Date(String d_Date) {
        this.d_Date = d_Date;
    }

    public String getD_Image() {
        return d_Image;
    }

    public void setD_Image(String d_Image) {
        this.d_Image = d_Image;
    }

    public String getD_Max_Temp() {
        return d_Max_Temp;
    }

    public void setD_Max_Temp(String d_Max_Temp) {
        this.d_Max_Temp = d_Max_Temp;
    }

    public String getD_Min_Temp() {
        return d_Min_Temp;
    }

    public void setD_Min_Temp(String d_Min_Temp) {
        this.d_Min_Temp = d_Min_Temp;
    }
}