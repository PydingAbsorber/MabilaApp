package com.example.myapplication.ui.rss;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.MainActivity;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DownloadRssTask extends AsyncTask<String, Void, List<RSSItem>> {

    private WeakReference<MainActivity> activityReference;

    public DownloadRssTask(MainActivity context) {
        this.activityReference = new WeakReference<>(context);
    }

    @Override
    protected List<RSSItem> doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder xmlContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                xmlContent.append(line);
            }
            Log.d("RSS XML", xmlContent.toString());

            // Парсим XML
            return activityReference.get().parseRSS(new ByteArrayInputStream(xmlContent.toString().getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(List<RSSItem> rssItems) {
        MainActivity activity = activityReference.get();
        if (activity == null || activity.isFinishing()) return;

        if (rssItems == null) {
            Toast.makeText(activity, "Не удалось загрузить RSS", Toast.LENGTH_LONG).show();
        } else {
            activity.updateRssItems(rssItems);
        }
    }


}

