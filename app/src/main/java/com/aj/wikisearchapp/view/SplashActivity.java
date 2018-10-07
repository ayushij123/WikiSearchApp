package com.aj.wikisearchapp.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.aj.wikisearchapp.R;

public class SplashActivity extends AppCompatActivity {

    TextView tvWiki;
    ImageView ivWiki;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContext = SplashActivity.this;

        tvWiki = findViewById(R.id.tv_wiki);
        ivWiki = findViewById(R.id.iv_wiki);

        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.splash_transition);
        tvWiki.startAnimation(animation);
        ivWiki.startAnimation(animation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(mContext, MainActivity.class));
            }
        }, 2000);
    }
}


