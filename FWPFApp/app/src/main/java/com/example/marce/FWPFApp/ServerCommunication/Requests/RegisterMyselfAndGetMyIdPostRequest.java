package com.example.marce.FWPFApp.ServerCommunication.Requests;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/*
*
* this class represents the post request in the registrationActivity
* registration data (Username, Phonenumber) is sent the the server and a unique id from the server is received
*
* Datei: RegisterMyselfAndGetMyIdPostRequest  Autor: Patrick
* Datum: 25.12.2015 Version: 1.1
* Historie:
* 29.12.15: class implemented
* 25.12.15: class created
*/

public class RegisterMyselfAndGetMyIdPostRequest extends PostRequest {
    private String urlPath = "/users";
    private JSONObject jsonToSend;
    private JSONObject responseJson;

    public RegisterMyselfAndGetMyIdPostRequest(String username, String phoneNumber) {
        this.requestUrl = this.serverUrl + urlPath;
        setJsonToSend(username, phoneNumber);
    }

    /**
     * execute request and read response
     */
    public JSONObject execute() {
        HttpURLConnection urlConnection = null;
        try {
            URL urlToRequest = new URL(this.requestUrl);
            urlConnection = (HttpURLConnection) urlToRequest.openConnection();
            urlConnection.setRequestMethod(this.requestType);
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            dataOutputStream.writeBytes(jsonToSend.toString());
            dataOutputStream.flush();
            dataOutputStream.close();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = urlConnection.getInputStream();
                responseJson = (JSONObject) this.readJSONObjectFromInputStream(inputStream);
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
        return responseJson;
    }

    private void setJsonToSend(String username, String phoneNumber) {
        jsonToSend = new JSONObject();
        try {
            jsonToSend.put("Username", username);
            jsonToSend.put("PhoneNumber", phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
