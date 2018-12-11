package com.getremp.daniel_lael.getremp;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

import com.getremp.daniel_lael.getremp.ui.registration.Registration1Fragment;
import com.getremp.daniel_lael.getremp.ui.registration.Registration2Fragment;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.registration1_toolbar);
        setSupportActionBar(myToolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Registration1Fragment.newInstance())
                    .commitNow();
        }


    }

}
