package training.weatherapp.RecycleListViewHours;

/**
 * Created by hindahmed on 15/08/17.
 */

public class Temp_Model_hours {


    private String Time;
    private String  Image;
    private String Temp;

    public Temp_Model_hours(String time, String image, String temp) {
        Time = time;
        Image = image;
        Temp = temp;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }
}
