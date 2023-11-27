package com.example.cocktailapi.helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {
    public String getJsonFromApi(String apiUrl) throws Exception{
        URL url = new URL(apiUrl);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null){
            content.append(inputLine);
        }

        in.close();
        con.disconnect();

        return content.toString();
    }
}
