package com.getremp.daniel_lael.getremp.Registration.registration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.getremp.daniel_lael.getremp.R;
import com.getremp.daniel_lael.getremp.RegistrationActivity;

public class Registration1Fragment extends Fragment {

    private EditText phoneNum1, phoneNum2;



    public static Registration1Fragment newInstance() {
        return new Registration1Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.registration1_fragment, container, false);

        Button btn = view.findViewById(R.id.reg1_next_btn);

        phoneNum1 = view.findViewById(R.id.reg1_phone_num1);
        phoneNum2 = view.findViewById(R.id.reg1_phone_num2);

        phoneNum1.addTextChangedListener(mTextEditorWatcher1);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrationActivity activity = (RegistrationActivity)getActivity();
                if(activity.SendVerificationCode(phoneNum1.getText().toString() + phoneNum2.getText().toString())) //sending auth
                {
                    activity.moveToFragmentTwo();
                }
//                else
//                {
//                }
                // TODO else - let user know you didn't send auth
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // change color of "GeTrump" & construct the first line.
        TextView line1 = (TextView) getView().findViewById(R.id.reg1_tw1);

        line1.setText( Html.fromHtml(getString(R.string.reg1_text_line1_1) + "<font color=" + ContextCompat.getColor(this.getActivity(),R.color.colorMedGreen)
                + "> " + getString(R.string.reg1_text_line1_2) + " </font>" + getString(R.string.reg1_text_line1_3)));

    }

    private final TextWatcher mTextEditorWatcher1 = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if(s.toString().length() == 3) {
                phoneNum1.clearFocus();
                phoneNum2.requestFocus();
                phoneNum2.setCursorVisible(true);
            }
        }
    };

}
