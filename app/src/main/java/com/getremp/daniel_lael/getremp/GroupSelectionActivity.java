package com.getremp.daniel_lael.getremp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.getremp.daniel_lael.getremp.GroupSelect.GroupSelectRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupSelectionActivity extends FragmentActivity {

    private RecyclerView mRecyclerView;

    private ArrayList<String> gsImageNames = new ArrayList<>();
    private ArrayList<String> gsImageURLs = new ArrayList<>();

    private static final String TAG = "GroupSelectionActivity";

    String id, fName, lName, address, avatar;

    HashMap<String, String> params;
    //private ImageView iv_avatar;
    private TextView tv_name, tv_address;
    private FloatingActionButton fab_add_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_selection);


        Bundle extrasBundle = this.getIntent().getExtras();
        if (extrasBundle != null) {
            id = extrasBundle.getString("id");
            if(!extrasBundle.getString("fName").isEmpty()) {
                fName = extrasBundle.getString("fName");
                lName = extrasBundle.getString("lName");
                address = extrasBundle.getString("address");
                initUserPanel();
            }

            // TODO : return to implement when capacity enlarged
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//            avatar = preferences.getString("image", "");

            //avatar = "Enlarge Heroku's capacity to receive...";
            //avatar = extrasBundle.getString("avatar");
            //Log.d(TAG, "onCreate: extras: id: " +id+ ", phone: " + phoneNum + ", fname: " + fName + ", lname: " + lName + ", email: " + eMail + ", addr: " + address + ", avatar: " + avatar);

        }


        fab_add_group = (FloatingActionButton)findViewById(R.id.gs_fab_add_group);

        fab_add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmNewGroup();
            }
        });

        getGroupsFromServer();
        initRecyclerView();

        //initImageBitmaps();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getGroupsFromServer();
        //initRecyclerView();

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

    private void initUserPanel() {

        // TODO : later add base64 decoder to decode image
        //iv_avatar = (ImageView)findViewById(R.id.gs_avatar_frame);

        tv_name = (TextView)findViewById(R.id.gs_user_name);
        tv_name.setText(fName + " " + lName);

        // TODO : later change to geographical current address.
        tv_address = (TextView)findViewById(R.id.gs_user_address);
        tv_address.setText(address);

    }

    private void getGroupsFromServer() {

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, ServerRequest.url + "/getallgroups", null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(TAG, "onResponse: " + response.toString());

                        try{

                            gsImageNames.clear();
                            gsImageURLs.clear();
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject group = response.getJSONObject(i);

                                // Get the current group (json object) data
                                gsImageNames.add(group.getString("name"));
                                gsImageURLs.add(group.getString("image"));

                            }

                            initRecyclerView();
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

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.gs_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        GroupSelectRecyclerViewAdapter adapter = new GroupSelectRecyclerViewAdapter(gsImageNames, gsImageURLs, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Mock Data
    private void initImageBitmaps() {



        gsImageNames.add("ברוכין");
        gsImageURLs.add("https://www.shomron.org.il/wp-content/uploads/2018/01/%D7%91%D7%A8%D7%95%D7%9B%D7%99%D7%9F-%D7%9C%D7%95%D7%92%D7%95.jpg");

        gsImageNames.add("אבירי המרכז");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("החברים של לאל");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("מרצים לא מרוצים");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("עכו");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("רכבת מרכז");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("שקר כלשהו");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("שקר כלשהו - שלוחת הצפון");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("רכבת מרכז2");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("שקר כלשהו2");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("שקר כלשהו - שלוחת הצפון2");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("רכבת מרכז3");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("שקר כלשהו3");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("שקר כלשהו - שלוחת הצפון3");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");


    }

    public void moveToTrempSelect(String group) {

        if(group.isEmpty())
            return;

        Intent intent = new Intent(this, TrempSelectionActivity.class);

        // add bundle
        intent.putExtra("id", id);
        intent.putExtra("group", group);
        intent.putExtra("fName", fName);
        intent.putExtra("lName", lName);
        intent.putExtra("address", address);

        startActivity(intent);

    }

    public void confirmNewGroup() {
        //DialogFragment newFragment = new NewGroupDialogFragment();
        //newFragment.show(getSupportFragmentManager(), "NewGroup");

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_group_select_add_dialog, null);

        final EditText editText = (EditText) dialogView.findViewById(R.id.gs_diag_name);
        Button accept_btn = (Button) dialogView.findViewById(R.id.gs_diag_accept_btn);
        Button cancel_btn = (Button) dialogView.findViewById(R.id.gs_diag_cancel_btn);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                Log.d(TAG, "onClick: NEW GROUP NAME: " + name);
                if(!name.isEmpty())
                    sendNewGroupToServer(name);
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }

    public void sendNewGroupToServer(final String new_group) {
        Log.d(TAG, "sendNewGroupToServer: NEW GROUP NAME: " + new_group);
        params = new HashMap<>();

        //id, fName, lName, eMail, address, avatar;


        params.put("name", new_group); // the entered data as the body.
        params.put("image", "no image");

        Log.d(TAG, "sendNewGroupToServer: new JSONObject(params): " + new JSONObject(params));

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, ServerRequest.url+"/addgroup", new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Log.d(TAG, "onResponse: " + response.toString());
                moveToTrempSelect(new_group);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onResponse: ERROR RESPONSE");
            }

        });


        Log.d(TAG, "adding to Q: " + jsObjRequest.getBody().toString());
        ServerRequest.getInstance(this).addToRequestQueue(jsObjRequest);
    }


//    public static class NewGroupDialogFragment extends DialogFragment {
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            // Get the layout inflater
//            final EditText edittext = new EditText(getActivity().findViewById(R.id.gs_diag_name));
//
//            LayoutInflater inflater = getActivity().getLayoutInflater();
//            // Inflate and set the layout for the dialog
//            // Pass null as the parent view because its going in the dialog layout
//            builder.setView(inflater.inflate(R.layout.layout_group_select_add_dialog, null))
//                    // Add action buttons
//                    .setPositiveButton(R.string.gs_diag_confirm, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int id) {
//                            String name = edittext.getText().toString();
//                            //EditText et_new_group = (EditText)getActivity().findViewById(R.id.gs_diag_name);
//                            //String name = ((EditText) et_new_group).getText().toString();
//                            Log.d(TAG, "onClick: NEW GROUP NAME: " + name);
//                            if(!name.isEmpty())
//                                ((GroupSelectionActivity)getActivity()).sendNewGroupToServer(name);
//                        }
//                    })
//                    .setNegativeButton(R.string.gs_diag_exit, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            NewGroupDialogFragment.this.getDialog().cancel();
//                        }
//                    });
//            return builder.create();
//        }
//    }
}
