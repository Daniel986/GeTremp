package com.getremp.daniel_lael.getremp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.getremp.daniel_lael.getremp.GroupSelect.GroupSelectRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupSelectionActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private ArrayList<String> gsImageNames = new ArrayList<>();
    private ArrayList<String> gsImageURLs = new ArrayList<>();

    private static final String TAG = "GroupSelectionActivity";

    RequestQueue queue;
    HashMap<String, String> params;

    String id, phoneNum, fName, lName, eMail, address, avatar;

    final String url = "http://getremp.herokuapp.com"; // your URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_selection);


        Bundle extrasBundle = this.getIntent().getExtras();
        if (extrasBundle != null) {
            id = extrasBundle.getString("id");
            phoneNum = extrasBundle.getString("phone");
            fName = extrasBundle.getString("fName");
            lName = extrasBundle.getString("lName");
            eMail = extrasBundle.getString("eMail");
            address = extrasBundle.getString("address");

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            avatar = preferences.getString("image", "");


            avatar = "Enlarge Heroku's capacity to receive...";
            //avatar = extrasBundle.getString("avatar");
            Log.d(TAG, "onCreate: extras: id: " +id+ ", phone: " + phoneNum + ", fname: " + fName + ", lname: " + lName + ", email: " + eMail + ", addr: " + address + ", avatar: " + avatar);

        }

        queue = Volley.newRequestQueue(this);

        params = new HashMap<String,String>();

        getGroupsFromServer();

        //initImageBitmaps();
        //initRecyclerView();
    }

    private void getGroupsFromServer() {

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url+ "/getallgroups", null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(TAG, "onResponse: " + response.toString());

                        try{

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
        queue.add(jsonObjectRequest);

    }

    public void sendUserToServer(String groupName) {


        if(id == null || phoneNum == null) {
            Log.d(TAG, "sendUserToServer: Failed. No id/phone");
            return;
        }

        Log.d(TAG, "sendUserToServer: sending");
        
        //id, fName, lName, eMail, address, avatar;
        params.put("id", id); // the entered data as the body.
        params.put("phoneNumber", phoneNum); // the entered data as the body.
        params.put("firstName", lName); // the entered data as the body.
        params.put("lastName", fName); // the entered data as the body.
        params.put("email", eMail); // the entered data as the body.
        params.put("address", address); // the entered data as the body.
        params.put("image", avatar); // the entered data as the body.
        
        // TODO wait for lael's "fix" before uncommenting
        //params.put("group", groupName); // the entered data as the body.

        queue.start();

        Log.d(TAG, "JSON: " + new JSONObject(params));

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url+"/register", new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, "onResponse: success. got response.");
                    Log.d(TAG, "onResponse: " + response.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onResponse: ERROR RESPONSE");
            }

        });


        Log.d(TAG, "adding to Q: " + jsObjRequest.getBody().toString());
        queue.add(jsObjRequest);
        Log.d(TAG, "Added");
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

}
