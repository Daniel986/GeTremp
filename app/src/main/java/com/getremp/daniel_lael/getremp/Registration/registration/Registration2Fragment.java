package com.getremp.daniel_lael.getremp.Registration.registration;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.getremp.daniel_lael.getremp.R;
import com.getremp.daniel_lael.getremp.RegistrationActivity;

import java.util.concurrent.TimeUnit;

public class Registration2Fragment extends Fragment {


    TextView timer;
    EditText pass;

    public CountDownTimer countDownTimer;

    public static Registration2Fragment newInstance() {
        return new Registration2Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =   inflater.inflate(R.layout.registration2_fragment, container, false);

        Button btn_next = view.findViewById(R.id.reg2_next_btn);
        Button btn_back = view.findViewById(R.id.reg2_back_btn);

        timer = view.findViewById(R.id.reg2_timer);

        pass = view.findViewById(R.id.reg2_pass);

        countDownTimer = new android.os.CountDownTimer(120000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                long millis= millisUntilFinished;
                String hms= String.format("%d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                );
                timer.setText(hms);
            }

            @Override
            public void onFinish() {
                RegistrationActivity activity = (RegistrationActivity)getActivity();
                Toast.makeText(activity, "Authentication Timed Out.", Toast.LENGTH_LONG).show();
                activity.moveToFragmentOne();
            }
        };

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final RegistrationActivity activity = (RegistrationActivity)getActivity();
                String pass_str = pass.getText().toString();
                if(!pass_str.isEmpty() && pass_str.length() == 6) {
                    activity.VerifySignInCode(pass_str);
                    //countDownTimer.cancel();
                }
                else
                {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "אנא הזן סיסמה בת 6 ספרות.", Toast.LENGTH_LONG).show();
                        }
                    });
                    //Toast.makeText(getActivity(), "אנא הזן סיסמה בת 6 ספרות.", Toast.LENGTH_SHORT);
                    // TODO tell the user that his code is not bueno. suggest resending..
                }

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                countDownTimer.cancel();
                RegistrationActivity activity = (RegistrationActivity)getActivity();
                activity.moveToFragmentOne();

            }
        });



        countDownTimer.start();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
