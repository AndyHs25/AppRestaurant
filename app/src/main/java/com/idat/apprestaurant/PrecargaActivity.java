package com.idat.apprestaurant;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

public class PrecargaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_precarga);

        LottieAnimationView animationView = findViewById(R.id.animation_view);
        animationView.setAnimation("Animation.json");
        animationView.setSpeed(1f);
        animationView.setRepeatCount(LottieDrawable.INFINITE);
        animationView.playAnimation();


        new Handler().postDelayed(() -> {
            Intent intent = new Intent(PrecargaActivity.this, PrincipalActivity.class);
            startActivity(intent);
            finish();
        }, 5000);
    }
}
