package com.example.wodozbior.utils;

import com.example.wodozbior.datastructures.Stacja;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

@Component
public class ImgwApiFacade {

    @Value("${imgw.api.url}")
    private static String imgwApiUrl;

    public static Vector<Stacja> getStacje() {
        Vector<Stacja> stacje = new Vector<>();
        try {
            URL url = new URL(imgwApiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String jsonResponse = response.toString();
            Gson gson = new Gson();
            Stacja[] stacjeArray = gson.fromJson(jsonResponse, Stacja[].class);
            for (Stacja stacja : stacjeArray) {
                stacje.add(stacja);
            }
        } catch (java.net.SocketTimeoutException e) {
            System.out.println("Timeout occurred while connecting to the API: " + e.getMessage());
        } catch (java.net.UnknownHostException e) {
            System.out.println("Unknown host: " + e.getMessage());
        } catch (java.io.IOException e) {
            System.out.println("I/O error occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
        }

        if (stacje.isEmpty()) {
            System.out.println("No data received from the API.");
        } else {
            System.out.println("Data received successfully.");
        }

        return stacje;
    }
}