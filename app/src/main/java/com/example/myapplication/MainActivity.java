package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.ui.rss.DownloadRssTask;
import com.example.myapplication.ui.rss.RSSAdapter;
import com.example.myapplication.ui.rss.RSSFeed;
import com.example.myapplication.ui.rss.RSSItem;
import com.example.myapplication.ui.rss.RSSRoot;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ActivityMainBinding;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Button count, button1,button2,button3,button4;
    private EditText hint1, hint2;
    private TextView question, answer;
    public int answerID = 1;
    public int correctanswers = 0;

    private RecyclerView recyclerView;
    private RSSAdapter adapter;
    private List<RSSItem> rssItemList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /////////////

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RSSAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        Button addButton = findViewById(R.id.btnAddRSS);
        addButton.setOnClickListener(new View.OnClickListener() {      //https://themeisle.com/blog/feed/
            @Override
            public void onClick(View v) {
                new DownloadRssTask(MainActivity.this).execute("https://sheffrecept.ru/feed/");  //https://news.yam.md/ro/rss
            }
        });
    }

    public List<RSSItem> parseRSS(InputStream inputStream) {
        try {
            Serializer serializer = new Persister();
            RSSFeed rssFeed = serializer.read(RSSFeed.class, inputStream);
            if (rssFeed.getChannel() != null) {
                return rssFeed.getChannel().getItems();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public void updateRssItems(List<RSSItem> rssItems) {
        adapter.updateItems(rssItems);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void buttonPressed(View v){
        String name = ((Button)v).getText().toString();
        if(name.equals(getString(getResources().getIdentifier("question" + answerID + "_5", "string", getPackageName())))){
            correctanswers++;
        } else{

        }
        if(answerID >= 10){
            Snackbar.make(v, "Поздравляем, вы завершили тест на " + correctanswers + "/10 баллов", Snackbar.LENGTH_LONG).show();
            return;
        }
        answerID++;
        update();
    }

    public void update(){
        if(question == null)
            question = findViewById(R.id.question);
        if(button1 == null)
            button1 = findViewById(R.id.button1);
        if(button2 == null)
            button2 = findViewById(R.id.button2);
        if(button3 == null)
            button3 = findViewById(R.id.button3);
        if(button4 == null)
            button4 = findViewById(R.id.button4);
        if(answer == null)
            answer = findViewById(R.id.answer);

        question.setText(getString(getResources().getIdentifier("question" + answerID + "_0", "string", getPackageName())));
        button1.setText(getString(getResources().getIdentifier("question" + answerID + "_1", "string", getPackageName())));
        button2.setText(getString(getResources().getIdentifier("question" + answerID + "_2", "string", getPackageName())));
        button3.setText(getString(getResources().getIdentifier("question" + answerID + "_3", "string", getPackageName())));
        button4.setText(getString(getResources().getIdentifier("question" + answerID + "_4", "string", getPackageName())));
        answer.setText("Правильных Ответов: " + correctanswers);
    }

    public void showNews(View v){

    }

}