package com.kamilismail.movieappandroid.URLDownloader;

import android.content.Context;
import android.util.Base64;

import com.kamilismail.movieappandroid.SessionController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class DownloadUrlForAuth {
    private String username, password;
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();
    static final String COOKIES_HEADER = "Set-Cookie";
    private SessionController sessionController;
    private Context applicationContext;

    public DownloadUrlForAuth(String _username, String _password, Context _applicationContext) {
        username = _username;
        password = _password;
        applicationContext = _applicationContext;
        sessionController = new SessionController(applicationContext);
    }

    public String readUrl(String myUrl) throws IOException {
        String data = "";
        InputStream inputStream;
        HttpURLConnection urlConnection;

        try {
            URL url = new URL(myUrl);
            final String basicAuth = "Basic " + Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", basicAuth);
            urlConnection.setConnectTimeout(10000);
            urlConnection.connect();

            try {
                inputStream = urlConnection.getInputStream();
                String cookiesHeader = urlConnection.getHeaderField(COOKIES_HEADER);
                List<HttpCookie> cookies = HttpCookie.parse(cookiesHeader);
                for (HttpCookie cookie : cookies) {
                    msCookieManager.getCookieStore().add(null, cookie);
                }
                String sessionToken = cookies.get(0).toString();
                sessionController.createLoginSession(sessionToken);

                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuffer sb = new StringBuffer();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    data = sb.toString();
                    br.close();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            } finally {
                urlConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (java.net.SocketTimeoutException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
