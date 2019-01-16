package com.getremp.daniel_lael.getremp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoadingActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private static final String TAG = "LoadingActivity";
    String fName, lName, address;


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

//                    currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Log.d(TAG, "onComplete: OK! Works fine!");
//                            } else {
//                                Log.w(TAG,"onComplete: Something is wrong!");
//                            }
//                        }
//                    });
                    //startRegistration();



                    Log.d(TAG, "run: startGroupSelect -->" + currentUser.getUid());
                    getUserDetailsFromServer();

                }
                else
                {
                    Log.d(TAG, "run: startRegistration -->");
                    startRegistration();
                    // goto registration activity
                }
            }
        }, 1000);   //3 seconds


    }

    private void getUserDetailsFromServer() {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, ServerRequest.url + "/getuserbyid/" + currentUser.getUid(), null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject user) {

                            Log.d(TAG, "onResponse: " + user.toString());

                            try{

                               // for(int i=0;i<response.length();i++){
                                    // Get current json object
                                    //JSONObject user = response.getJSONObject();

                                    // Get the current group (json object) data
                                    fName = user.getString("firstName");
                                    lName = user.getString("lastName");
                                    address = user.getString("address");

                                //}
                                startGroupSelect();
                            }catch (JSONException e){
                                Log.d(TAG, "onResponse(exception): " + e.toString());
                            }


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.d(TAG, "onErrorResponse: " + error.toString());
                        }
                    });

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            ServerRequest.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }


    private void startRegistration()
    {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
    private void startGroupSelect()
    {
        Intent intent = new Intent(this, GroupSelectionActivity.class);
        intent.putExtra("id", currentUser.getUid());
        intent.putExtra("fName", fName);
        intent.putExtra("lName", lName);
        intent.putExtra("address", address);
        startActivity(intent);
    }

}
