package com.sda.lib;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class CheckWheater {
    private final static String urlDomain = "https://www.metaweather.com/api/location/search/?query=";
    private final static String urlApiTemplate = "https://www.metaweather.com/api/location/";
    private static String localization;

    private CheckWheater() {
    }

    private static String getIDLocalization() throws MalformedURLException {
        localization = OpenSaveSettings
                .openConfig()
                .getLocalization();

        URL url;
        StringBuilder sb = new StringBuilder(urlDomain);
        sb.append(localization);

        url = new URL(sb.toString());

        StringBuilder jsonText = new StringBuilder();
        try (InputStream is = url.openStream()) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8")));
            br.lines().forEach(jsonText::append);
        } catch (IOException | JSONException e) {
            //e.printStackTrace();
            return "";
        }

        if (jsonText==null || jsonText.toString().isEmpty()){
            return "";
        }

        JSONArray jsonArray = new JSONArray(jsonText.toString());

        if(jsonArray.length()==0){
            return "";
        }
        return jsonArray.getJSONObject(0).get("woeid").toString();
    }





    public static Weather checkWeatherNow() throws MalformedURLException {

        String id = getIDLocalization();

        if(id==null || id.isEmpty())
        {
            return new Weather(0,0,0,"Location not found","-");

        }

        URL url;
        StringBuilder sb = new StringBuilder(urlApiTemplate);
        sb.append(id).append("/");
        url = new URL(sb.toString());

        StringBuilder jsonText = new StringBuilder();
        try (InputStream is = url.openStream()) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8")));
            br.lines().forEach(jsonText::append);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject(jsonText.toString());
        JSONArray jsonArray = new JSONArray(jsonObject.get("consolidated_weather").toString());

        jsonObject = new JSONObject(jsonArray.get(0).toString());

        return new Weather((Double) jsonObject.get("the_temp"),
                (Double) jsonObject.get("min_temp"),
                (Double) jsonObject.get("max_temp"),
                localization,
                (String) jsonObject.get("weather_state_abbr"));

    }


}
