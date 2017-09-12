package training.weatherapp.RecycleLists.offline_Models;

/**
 * Created by hindahmed on 12/09/17.
 */

public class Offline_Model_12Hours {

    private String Time;
    private String Image;
    private String Temp;
    private String weather_phrase ;

    public Offline_Model_12Hours(String time, String image, String temp, String weather_phrase) {
        Time = time;
        Image = image;
        Temp = temp;
        this.weather_phrase = weather_phrase;
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

    public String getWeather_phrase() {
        return weather_phrase;
    }

    public void setWeather_phrase(String weather_phrase) {
        this.weather_phrase = weather_phrase;
    }
}
