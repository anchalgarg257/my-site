package com.adobe.aem.guides.nirvana.core.practice;

import com.adobe.aem.guides.nirvana.core.pojo.CountryBean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

public class CallingCountryApi {

//    public static CountryBean main(String[] arg) {
//
//        try {
//            URL url = new URL("https://restcountries.eu/rest/v2/all");
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//            httpURLConnection.setRequestMethod("GET");
//            httpURLConnection.setRequestProperty("Accept", "application/json");
//            if (httpURLConnection.getResponseCode() != 200) {
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + httpURLConnection.getResponseCode());
//            }
//
//            InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//
//            boolean buffer;
//
//            StringBuilder stringBuilder = new StringBuilder();
//            while (buffer = bufferedReader.readLine() != null) {
//                stringBuilder.append(buffer);
//
//                JSONArray jsonArray = new JSONArray(stringBuilder.toString());
//
//                JSONObject countryObjcet = (JSONObject) c.get(0);
//
//                CountryBean countryBean = new CountryBean();
//                countryBean.setName(countryObjcet.has("name") ? countryObjcet.getString("name") : "");
//
//               String countryBean = stringBuilder.toString();
//                System.out.println(countryBean.getName());
//
//            }
//
//        } catch (IOException |
//                JSONException e) {
//
//            e.printStackTrace();
//        }
//
//    }
}