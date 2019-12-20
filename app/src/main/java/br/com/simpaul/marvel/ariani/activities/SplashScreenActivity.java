package br.com.simpaul.marvel.ariani.activities;

import androidx.appcompat.app.AppCompatActivity;
import br.com.simpaul.marvel.ariani.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView logo, gradient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.splash_logo);
        gradient = findViewById(R.id.gradient);
        YoYo.with(Techniques.FadeIn).duration(1500).playOn(logo);
        YoYo.with(Techniques.FadeIn).duration(1500).playOn(gradient);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goHome();
            }
        },2000);
    }

    private void goHome(){
        Intent homeIntent = new Intent();
        homeIntent.setClass(SplashScreenActivity.this, MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
        finish();
    }
}
