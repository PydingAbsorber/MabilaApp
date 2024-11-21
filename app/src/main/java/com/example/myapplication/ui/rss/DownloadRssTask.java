package com.example.myapplication.ui.rss;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

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
    private ProgressBar progressBar;

    public DownloadRssTask(MainActivity context) {
        this.activityReference = new WeakReference<>(context);
        this.progressBar = context.findViewById(R.id.progressBar);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
    }

    @Override
    protected List<RSSItem> doInBackground(String... urls) {
        try {
            URL url = new URL(urls[0]);

            int totalSteps = 10;
            for (int i = 0; i < totalSteps; i++) {
                try {
                    Thread.sleep(500);
                    progressBar.setProgress((i + 1) * (100 / totalSteps));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

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
        progressBar.setVisibility(View.INVISIBLE);
        if (activity == null || activity.isFinishing()) return;

        if (rssItems == null) {
            Toast.makeText(activity, "Не удалось загрузить RSS", Toast.LENGTH_LONG).show();
        } else {
            activity.updateRssItems(rssItems);
        }
    }


}

