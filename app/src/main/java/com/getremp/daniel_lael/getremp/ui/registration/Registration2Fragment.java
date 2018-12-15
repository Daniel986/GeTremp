package com.getremp.daniel_lael.getremp.ui.registration;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.getremp.daniel_lael.getremp.R;
import com.getremp.daniel_lael.getremp.RegistrationActivity;

public class Registration2Fragment extends Fragment {

    private RegistrationViewModel mViewModel;


    EditText pass;

    public static Registration2Fragment newInstance() {
        return new Registration2Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =   inflater.inflate(R.layout.registration2_fragment, container, false);

        Button btn = view.findViewById(R.id.reg2_next_btn);

        pass = view.findViewById(R.id.reg2_pass);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegistrationActivity activity = (RegistrationActivity)getActivity();
                activity.VerifySignInCode(pass.getText().toString());
                {
//                    activity.moveToFragmentThree();
                    // TODO tell the user that his code is not bueno. suggest resending..
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        // TODO: Use the ViewModel


    }

}
