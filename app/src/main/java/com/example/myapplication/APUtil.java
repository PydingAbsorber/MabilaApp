package com.example.myapplication;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class APUtil {

    public static String readJsonFromAssets(Context context) {
        AssetManager assetManager = context.getAssets();
        StringBuilder json = new StringBuilder();

        try (InputStream is = assetManager.open("news.json");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json.toString();
    }

    public static String getJsonString() throws FileNotFoundException, JSONException {
        try {
            FileReader reader = new FileReader("/app/src/main/assets/news.json");
            JSONTokener tokener = new JSONTokener(reader.getEncoding());
            JSONObject jsonObject = new JSONObject(tokener);
            JSONArray newsArray = jsonObject.getJSONArray("news");

            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject newsItem = newsArray.getJSONObject(i);
                return newsItem.getString("cover");
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return "";
    }

}
