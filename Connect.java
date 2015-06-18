package com.bugchain.tools;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bugchain on 18/6/2558.
 */
public class Connect {

    private static final String TAG = "ConnectServer";
    private static final int TIME_OUT = 5*60*1000; // 5 Minutes
    private static final String REQUEST_GET = "GET";
    private static final String REQUEST_POST = "POST";

    public String getStringContent(String requestUrl) {
        URL url;
        String response = "";
        try {
            url = new URL(requestUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setRequestMethod(REQUEST_GET);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                if(bufferedReader != null)
                    bufferedReader.close();
            } else {
                response = "";
            }
            if (conn != null) {
                conn.disconnect();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error No response code");
            e.printStackTrace();
        }
        return response;
    }

    private String httpPost(String requestUrl, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setRequestMethod(REQUEST_POST);
            conn.setUseCaches(false);
            conn.setAllowUserInteraction(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(getPostDataToString(postDataParams));

            writer.flush();
            writer.close();
            outputStream.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "Post response code : " + responseCode);
                String line;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                if(bufferedReader != null)
                    bufferedReader.close();
            } else {
                response = "";
                Log.d(TAG, "Post response code : " + responseCode);
            }
        } catch (Exception e) {

        }
        return response;
    }

    private String getPostDataToString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    public String signIn(String email, String password) {
        final String url = " your url ";
        HashMap<String, String> dataParams = new HashMap<String, String>();
        dataParams.put("email", email);
        dataParams.put("password", password);
        String result = httpPost(url, dataParams);
        Log.d(TAG, result);

        return result;

    }

}
