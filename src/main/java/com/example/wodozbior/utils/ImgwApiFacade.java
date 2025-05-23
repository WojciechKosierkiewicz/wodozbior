package com.example.wodozbior.utils;

import com.example.wodozbior.datastructures.Stacja;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

@Component
public class ImgwApiFacade {

   // @Value("${imgw.api.url}")
   // private static String imgwApiUrl;

//    static Vector<Stacja> get_current_synop(){
//        try {
//            URL url = new URL(imgwApiUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Accept", "application/json");
//
//            if(conn.getResponseCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
//            }
//
//            BufferedReader in = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//            StringBuilder jsonBuilder = new StringBuilder();
//            String line;
//            while ((line = in.readLine()) != null) {
//                jsonBuilder.append(line);
//            }
//            in.close();
//
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
