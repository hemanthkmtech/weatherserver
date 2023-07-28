package com.weather.weatherserver.model;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherData {
    private final RestTemplate restTemplate = new RestTemplate();

    public JSONObject getWeatherData(String lan, String lat) {
        JSONObject weatherApiData = getDataFromAPI(lan, lat);
        JSONObject updatedWebData = new JSONObject();
        System.out.println(weatherApiData);
        try {
            updatedWebData.accumulate("weather", weatherApiData.get("weather"));
            double temp = weatherApiData.getJSONObject("main").getDouble("temp");
            temp = temp - 273.15;
            updatedWebData.accumulate("temp", temp);
            updatedWebData.accumulate("pressure", weatherApiData.getJSONObject("main").getInt("pressure"));
            temp = weatherApiData.getJSONObject("main").getDouble("temp_min");
            temp = temp - 273.15;
            updatedWebData.accumulate("temp_min", temp);
            temp = weatherApiData.getJSONObject("main").getDouble("temp_max");
            temp = temp - 273.15;
            updatedWebData.accumulate("temp_max", temp);
            updatedWebData.accumulate("humidity", weatherApiData.getJSONObject("main").getDouble("humidity"));
            updatedWebData.accumulate("visibility", weatherApiData.getDouble("visibility") / 1000);
            updatedWebData.accumulate("wind_speed", weatherApiData.getJSONObject("wind").getDouble("speed"));
            // updatedWebData.accumulate("clouds",
            // weatherApiData.getJSONObject("clouds").getDouble("all"));
            updatedWebData.accumulate("country", weatherApiData.getJSONObject("sys").getString("country"));
            updatedWebData.accumulate("city", weatherApiData.getString("name"));
            System.out.println(weatherApiData);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return updatedWebData;
    }

    public JSONObject getDataFromAPI(String lan, String lat) {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lan
                + "&appid=50b5029dcf22a51a45c8ec049de6de6a";
        String result = this.restTemplate.getForObject(url, String.class);
        JSONObject weatherApiData = null;
        try {
            weatherApiData = new JSONObject(result);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return weatherApiData;
    }
}
