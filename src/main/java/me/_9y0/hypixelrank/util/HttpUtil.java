package me._9y0.hypixelrank.util;

import com.google.gson.JsonElement;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.rmi.server.ExportException;

public class HttpUtil {

    public static InputStream get(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla 5.0");

            if (connection.getResponseCode() <= HttpURLConnection.HTTP_BAD_REQUEST) {
                return connection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
