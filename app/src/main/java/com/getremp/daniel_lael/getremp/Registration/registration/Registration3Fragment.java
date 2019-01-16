package com.getremp.daniel_lael.getremp.Registration.registration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getremp.daniel_lael.getremp.R;
import com.getremp.daniel_lael.getremp.RegistrationActivity;


public class Registration3Fragment extends Fragment  {

    RegistrationActivity activity;
    EditText fName, lName, eMail, address;
    TextView fNameC, lNameC, eMailC, addressC;


    private class CountDownTextWatcher implements TextWatcher{

        private View view;
        private CountDownTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable s) {
            String text = s.toString().length() + "/50";
            switch(view.getId()){
                case R.id.reg3_name:
                    fNameC.setText(text);
                    break;
                case R.id.reg3_surname:
                    lNameC.setText(text);
                    break;
                case R.id.reg3_email:
                    eMailC.setText(text);
                    break;
                case R.id.reg3_address:
                    addressC.setText(text);
                    break;
            }
        }
    }


    public static Registration3Fragment newInstance(String param1, String param2) {
        Registration3Fragment fragment = new Registration3Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =   inflater.inflate(R.layout.registration3_fragment, container, false);


        ImageView img = view.findViewById(R.id.reg3_avatar_image);
        Button btn = view.findViewById(R.id.reg3_next_btn);


        fName = view.findViewById(R.id.reg3_name);
        fName.addTextChangedListener(new CountDownTextWatcher(fName));

        lName = view.findViewById(R.id.reg3_surname);
        lName.addTextChangedListener(new CountDownTextWatcher(lName));

        eMail = view.findViewById(R.id.reg3_email);
        eMail.addTextChangedListener(new CountDownTextWatcher(eMail));

        address = view.findViewById(R.id.reg3_address);
        address.addTextChangedListener(new CountDownTextWatcher(address));

        fNameC = view.findViewById(R.id.reg3_name_count);
        lNameC = view.findViewById(R.id.reg3_surname_count);
        eMailC = view.findViewById(R.id.reg3_email_count);
        addressC = view.findViewById(R.id.reg3_address_count);




        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity = (RegistrationActivity)getActivity();
                activity.UploadAvatarImage();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( fieldValid(fName) && fieldValid(lName) && fieldValid(eMail) )
                {
                    RegistrationActivity activity = (RegistrationActivity) getActivity();
                    activity.moveToGroupSelect(fName.getText().toString(), lName.getText().toString(), eMail.getText().toString(), address.getText().toString());
                }
                else {
                    Toast.makeText(getActivity(), "אנא מלא את כל שדות החובה", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private boolean fieldValid(EditText field) {

        String text = field.getText().toString().trim();
        if(field == eMail)
        {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                return true;
            }
            else{
                eMail.setError("invalid Email address");
                return false;
            }
        }
        else
        {
            if(text.length() >= 2)
            {
                return true;
            }
        }
        return false;
    }


//    @Override
//    public void onClick(View v) {
//        activity = (RegistrationActivity)getActivity();
//        switch(v.getId())
//        {
//            case R.id.reg3_avatar_image :
//            {
//                activity.UploadAvatarImage();
//                break;
//            }
//            case R.id.reg3_next_btn :
//            {
//                break;
//            }
//        }
//    }
}
