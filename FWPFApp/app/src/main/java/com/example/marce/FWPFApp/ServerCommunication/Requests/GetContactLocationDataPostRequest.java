package com.example.marce.FWPFApp.ServerCommunication.Requests;

import android.location.Location;

import com.example.marce.FWPFApp.DataObjects.Contact;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
*
* this class represents the request to get the current location data for one single contact
* used in the navigationActivity to update data for the contact to navigate
*
* Datei: GetContactLocationDataPostRequest  Autor: Patrick
* Datum: 25.12.2015 Version: 1.2
* Historie:
* 15.01.16: add "LocationUpdateTime"-Handling
* 29.12.15: class implemented
* 25.12.15: class created
*/

public class GetContactLocationDataPostRequest extends PostRequest {
    private String urlPath = "/contact-locations/";
    private JSONObject responseJson;

    public GetContactLocationDataPostRequest(String contactId) {
        this.requestUrl = serverUrl + urlPath + contactId;
    }

    /**
     * execute request and read response
     */
    public void execute(Contact contact) {
        HttpURLConnection urlConnection = null;
        try {
            URL urlToRequest = new URL(this.requestUrl);
            urlConnection = (HttpURLConnection) urlToRequest.openConnection();
            urlConnection.setRequestMethod(requestType);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                responseJson = (JSONObject) readJSONObjectFromInputStream(inputStream); //parse response to json

                Location location = new Location("");
                location.setLatitude(Double.parseDouble(responseJson.getString("Latitude")));
                location.setLongitude(Double.parseDouble(responseJson.getString("Longitude")));
                contact.setLocation(location);

                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                String string1 = responseJson.getString("LocationUpdateTime");
                Date locationUpdateTime = df1.parse(string1);
                contact.setLocationUpdateTime(locationUpdateTime);

            } else {
                throw new Exception("not implemented");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // could not read response body
            // (could not create input stream)
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
