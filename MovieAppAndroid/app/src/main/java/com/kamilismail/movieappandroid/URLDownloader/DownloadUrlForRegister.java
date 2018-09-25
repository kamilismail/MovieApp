package com.kamilismail.movieappandroid.URLDownloader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUrlForRegister {
    private String username, password;

    public DownloadUrlForRegister(String _username, String _password) {
        username = _username;
        password = _password;
    }

    public String readUrl(String myUrl) throws IOException {
        String data = "";
        InputStream inputStream = null;

        try {
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("username", username);
            postDataParams.put("password", password);
            postDataParams.put("role", "client");

            URL url = new URL(myUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            conn.connect();
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(postDataParams.toString());
            wr.flush();
            wr.close();

            try {
                inputStream = conn.getInputStream();
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                    StringBuffer sb = new StringBuffer();

                    String line = "";
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    data = sb.toString();
                    br.close();
                } catch (IOException e) {
                    throw e;
                } finally {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw e;
            } finally {
                conn.disconnect();
            }
        } catch (IOException e) {
            throw e;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
