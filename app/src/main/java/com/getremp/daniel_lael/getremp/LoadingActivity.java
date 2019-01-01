package com.getremp.daniel_lael.getremp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;

public class LoadingActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private static final String TAG = "LoadingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final ImageView loadingArrow = findViewById(R.id.loading_arrow);

        ObjectAnimator animation = ObjectAnimator.ofFloat(loadingArrow, "translationX", -1000f, 1000f);
        animation.setDuration(2000);
        animation.setRepeatCount(ValueAnimator.INFINITE);
        animation.setRepeatMode(ValueAnimator.RESTART);
        animation.start();

        mAuth = FirebaseAuth.getInstance();

        currentUser = mAuth.getCurrentUser();

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                if(currentUser != null)
                {
                    // TODO
                    // send user's group request to server
                    // if server returns no groups --> goto group select act
                    // else goto main
                    Log.d(TAG, "run: startGroupSelect -->" + currentUser.getUid() );
                    //currentUser.delete();
                    //if(currentUser != null)
                     //   Log.d(TAG, "run: startGroupSelect -->" + currentUser.getUid() );
                    startGroupSelect();
                    //startRegistration();

                }
                else
                {
                    Log.d(TAG, "run: startRegistration -->");
                    startRegistration();
                    // goto registration activity
                }
            }
        }, 3000);   //3 seconds


    }


    private void startRegistration()
    {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
    private void startGroupSelect()
    {
        Intent intent = new Intent(this, GroupSelectionActivity.class);
        startActivity(intent);
    }

}
