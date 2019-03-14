package com.sda.lib;

public class Weather {

    double temp;
    double minTemp;
    double maxTemp;
    String localization;
    String weatherStateIcon;


    public Weather(double temp, double minTemp, double maxTemp, String localization, String weatherStateName) {
        this.temp = Math.round(temp*10)/10;
        this.minTemp = Math.round(minTemp*10)/10;
        this.maxTemp = Math.round(maxTemp*10)/10;
        this.localization = localization;

        switch (weatherStateName) {
            case "sn": case "l": case "h": weatherStateIcon = "weather-snow.png"; break;
            case "" : weatherStateIcon = "weather-clouds.png"; break;
            case "hr": case "lr": case "s": weatherStateIcon = "weather-rain.png"; break;
            case "hc": case "lc": weatherStateIcon = "weather-storm.png"; break;
            case "c" : weatherStateIcon = "weather-sun.png"; break;

            default: weatherStateIcon = "edit.png";
        }


    }

    public String getWeatherStateIcon() {
        return weatherStateIcon;
    }

    public String getLocalization() {
        return localization;
    }

    public double getTemp() {
        return temp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "temp=" + temp +
                ", minTemp=" + minTemp +
                ", maxTemp=" + maxTemp +
                '}';
    }
}
