package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private Button count, button1,button2,button3,button4;
    private EditText hint1, hint2;
    private TextView question, answer;
    public int answerID = 1;
    public int correctanswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        hint1 = findViewById(R.id.hint1);
        hint2 = findViewById(R.id.hint2);
        count = findViewById(R.id.count);
        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hint1.getText() != null && hint2.getText() != null) {
                    float num1 = Float.parseFloat(hint1.getText().toString());
                    float num2 = Float.parseFloat(hint2.getText().toString());
                    float num3 = num1 + num2;
                    Snackbar.make(v, String.valueOf(num3), Snackbar.LENGTH_LONG).show();
                }
            }
        });
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
        if(answerID == 10){
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

}