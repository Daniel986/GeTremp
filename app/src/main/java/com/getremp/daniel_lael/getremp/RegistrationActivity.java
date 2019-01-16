package com.getremp.daniel_lael.getremp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.getremp.daniel_lael.getremp.Registration.registration.Registration1Fragment;
import com.getremp.daniel_lael.getremp.Registration.registration.Registration2Fragment;
import com.getremp.daniel_lael.getremp.Registration.registration.Registration3Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";
    private static final int RESULT_LOAD_IMAGE = 1;

    FragmentManager mFragmentManager;

    Registration1Fragment reg1_frag;
    Registration2Fragment reg2_frag;
    Registration3Fragment reg3_frag;

    TextView toolbar_title;

    String codeSent, phoneNum, avatar;

    FirebaseAuth mAuth;


    final Activity t = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        Toolbar myToolbar = findViewById(R.id.registration1_toolbar);
        setSupportActionBar(myToolbar);

        mAuth = FirebaseAuth.getInstance();

        mFragmentManager = getSupportFragmentManager();
        toolbar_title = findViewById(R.id.toolbar_title);




        if (savedInstanceState == null) {
            reg1_frag = new Registration1Fragment();
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.add(R.id.reg_frag_placeholder, reg1_frag);
            ft.commit();
        }

        //moveToFragmentThree();

    }


    public void moveToFragmentOne(){

        if (reg1_frag == null){
            reg1_frag = new Registration1Fragment();
        }
        toolbar_title.setText(R.string.reg1_title);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.reg_frag_placeholder,reg1_frag);
        ft.commit();
    }

    public void moveToFragmentTwo(){

        if (reg2_frag == null){
            reg2_frag = new Registration2Fragment();
        }
        toolbar_title.setText(R.string.reg2_title);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.reg_frag_placeholder,reg2_frag);
        ft.commit();
    }

    public void moveToFragmentThree(){

        if (reg3_frag == null){
            reg3_frag = new Registration3Fragment();
        }
        toolbar_title.setText(R.string.reg3_title);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(R.id.reg_frag_placeholder,reg3_frag);
        ft.commit();
    }

    public void UploadAvatarImage() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    public void moveToGroupSelect(String fName, String lName, String eMail, String address)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();


        Intent intent = new Intent(this, GroupSelectionActivity.class);

        intent.putExtra("id", mAuth.getCurrentUser().getUid());
//        intent.putExtra("phone", phoneNum);
        intent.putExtra("fName", fName);
        intent.putExtra("lName", lName);
//        intent.putExtra("eMail", eMail);
        intent.putExtra("address", address);

        editor.putString("image", avatar);
        editor.commit();

        sendUserToServer(intent, fName, lName, eMail, address);

    }

    public void sendUserToServer(final Intent intent, String fName, String lName, String eMail, String address) {


        HashMap<String, String> params = new HashMap<>();
        Log.d(TAG, "sendUserToServer: sending");

        //id, fName, lName, eMail, address, avatar;
        params.put("id", mAuth.getCurrentUser().getUid()); // the entered data as the body.
        params.put("phoneNumber", phoneNum); // the entered data as the body.
        params.put("firstName", lName); // the entered data as the body.
        params.put("lastName", fName); // the entered data as the body.
        params.put("email", eMail); // the entered data as the body.
        params.put("address", address); // the entered data as the body.

        // TODO: return when capacity enlarged
        //params.put("image", avatar); // the entered data as the body.

        params.put("image", "no image");
        params.put("groups", "");

        Log.d(TAG, "JSON: " + new JSONObject(params));

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, ServerRequest.url+"/register", new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: success. got response.");
//                    Log.d(TAG, "onResponse: " + response.getString("message"));
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onResponse: ERROR RESPONSE");
            }

        });


        Log.d(TAG, "adding to Q: " + jsObjRequest.getBody().toString());
        ServerRequest.getInstance(this).addToRequestQueue(jsObjRequest);
        Log.d(TAG, "Added");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            //Uri selectedImage = data.getData();
            try {
            final Uri imageUri = data.getData();
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            ImageView im = (ImageView) findViewById(R.id.reg3_avatar_image);
            im.setImageURI(imageUri);

            avatar = encodeImage(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    public boolean SendVerificationCode(String phoneNumber) {

        if(phoneNumber.isEmpty() || phoneNumber.length() != 10)
        {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(t, "Please enter a valid phone number", Toast.LENGTH_LONG).show();
                }
            });
            return false;
        }

        this.phoneNum = phoneNumber;


        phoneNumber = getString(R.string.reg1_il_code) + phoneNumber.substring(1);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,            // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,       // Unit of timeout
                this,            // Activity (for callback binding)
                mCallbacks);            // OnVerificationStateChangedCallbacks
        return true;
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            t.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(t, "Oops, something in the verification went wrong.. please try again later.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
        }
    };

    public void VerifySignInCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        final Activity t = this;
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AUTH", "signInWithCredential:success");
                            //moveToFragmentThree();
                            FirebaseUser user = task.getResult().getUser();

                            // TODO save user credentials to re-use with server

                            reg2_frag.countDownTimer.cancel();

                            moveToFragmentThree();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(t, "Authentication failed.", Toast.LENGTH_LONG).show();
                                }
                            });
                            Log.w("AUTH", "signInWithCredential:failure", task.getException());
                            //moveToFragmentOne();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.gs_back_text))
                .setNegativeButton(getString(R.string.gs_back_stay), null)
                .setPositiveButton(getString(R.string.gs_back_out), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        // GroupSelectionActivity.super.onBackPressed();
                        finishAffinity();
                    }
                }).create().show();
    }

}
