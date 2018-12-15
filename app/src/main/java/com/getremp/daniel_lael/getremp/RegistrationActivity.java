package com.getremp.daniel_lael.getremp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.getremp.daniel_lael.getremp.ui.registration.Registration1Fragment;
import com.getremp.daniel_lael.getremp.ui.registration.Registration2Fragment;
import com.getremp.daniel_lael.getremp.ui.registration.Registration3Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegistrationActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;

    FragmentManager mFragmentManager;

    Registration1Fragment reg1_frag;
    Registration2Fragment reg2_frag;
    Registration3Fragment reg3_frag;

    TextView toolbar_title;

    String codeSent;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.registration1_toolbar);
        setSupportActionBar(myToolbar);

        mAuth = FirebaseAuth.getInstance();

        mFragmentManager = getSupportFragmentManager();

        toolbar_title = (TextView) findViewById(R.id.toolbar_title);


        if (savedInstanceState == null) {
            reg1_frag = new Registration1Fragment();
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.add(R.id.reg_frag_placeholder, reg1_frag);
            ft.commit();
        }


    }
//
//
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            ImageView im = (ImageView) findViewById(R.id.reg3_avatar_image);
            im.setImageURI(selectedImage);
        }
    }

    public boolean SendVerificationCode(String phoneNumber) {

        if(phoneNumber.isEmpty())
        {
            return false;
        }
        phoneNumber = "+972"+phoneNumber;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

        return true;
    }


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

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
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("AUTH", "signInWithCredential:success");
                            //moveToFragmentThree();
                            FirebaseUser user = task.getResult().getUser();
                            SharedPreferences.Editor editor = getSharedPreferences("preferenceName", MODE_PRIVATE).edit();
                            user.getIdToken(false);
                            editor.putString("key", "VAL");
                            editor.commit();
                            moveToFragmentThree();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("AUTH", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

}
