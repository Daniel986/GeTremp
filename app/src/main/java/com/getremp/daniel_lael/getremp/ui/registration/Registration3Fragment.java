package com.getremp.daniel_lael.getremp.ui.registration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.getremp.daniel_lael.getremp.R;
import com.getremp.daniel_lael.getremp.RegistrationActivity;


public class Registration3Fragment extends Fragment implements View.OnClickListener {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    RegistrationActivity activity;

    private final TextWatcher mTextEditorWatcher1 = new TextWatcher() {
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public void afterTextChanged(Editable s) {
        //This sets a textview to the current length
        //mTextView.setText(50 - s.toString().length() + "/50");
    }
};

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registration3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Registration3Fragment newInstance(String param1, String param2) {
        Registration3Fragment fragment = new Registration3Fragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

        //TODO connect listener to textEdit fields
        //mEditText.addTextChangedListener(mTextEditorWatcher1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =   inflater.inflate(R.layout.registration3_fragment, container, false);


        ImageView img = view.findViewById(R.id.reg3_avatar_image);
        Button btn = view.findViewById(R.id.reg3_next_btn);

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

                RegistrationActivity activity = (RegistrationActivity)getActivity();
                activity.moveToFragmentOne();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onClick(View v) {
        activity = (RegistrationActivity)getActivity();
        switch(v.getId())
        {
            case R.id.reg3_avatar_image :
            {
                activity.UploadAvatarImage();
                break;
            }
            case R.id.reg3_next_btn :
            {
                break;
            }
        }
    }
}
