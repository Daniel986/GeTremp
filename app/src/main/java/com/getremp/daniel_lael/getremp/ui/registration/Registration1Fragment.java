package com.getremp.daniel_lael.getremp.ui.registration;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getremp.daniel_lael.getremp.R;
import com.getremp.daniel_lael.getremp.RegistrationActivity;

public class Registration1Fragment extends Fragment {

    private RegistrationViewModel mViewModel;

    public static Registration1Fragment newInstance() {
        return new Registration1Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration1_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        // TODO: Use the ViewModel

        // change color of "GeTrump" & construct the first line.
        TextView line1 = (TextView) getView().findViewById(R.id.reg1_tw1);

        line1.setText( Html.fromHtml(getString(R.string.reg1_text_line1_1) + "<font color=" + ContextCompat.getColor(this.getActivity(),R.color.colorMedGreen)
                + "> " + getString(R.string.reg1_text_line1_2) + " </font>" + getString(R.string.reg1_text_line1_3)));

    }

}
