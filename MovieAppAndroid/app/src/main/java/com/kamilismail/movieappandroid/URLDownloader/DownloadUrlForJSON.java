package com.kamilismail.movieappandroid.URLDownloader;

import android.content.Context;
import android.util.Log;

import com.kamilismail.movieappandroid.SessionController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadUrlForJSON {
    private SessionController sessionController;
    private Context context;

    public DownloadUrlForJSON(Context _context) {
        this.context = _context;
        this.sessionController = new SessionController(context);
    }

    public String readUrl (String myUrl) throws Exception {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(myUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            // Set cookie as a request property to authorize
            urlConnection.setRequestProperty(sessionController.KEY_COOKIE, sessionController.getCookie());
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (MalformedURLException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        } finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        Log.d("DownloadURL", "Returning data= " + data);

        return data;
    }
}
