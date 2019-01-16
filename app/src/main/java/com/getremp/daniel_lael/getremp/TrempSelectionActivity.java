package com.getremp.daniel_lael.getremp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.getremp.daniel_lael.getremp.TrempSelect.RegularTremp;
import com.getremp.daniel_lael.getremp.TrempSelect.TrempSelectRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TrempSelectionActivity extends AppCompatActivity {

    private static final String TAG = "TrempSelectionActivity";
    private RecyclerView mRecyclerView;

    private ArrayList<RegularTremp> tremps = new ArrayList<>();

    private TextView tv_name, tv_address, tv_group;
    private String id, fName, lName, address, group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tremp_selection);


        Bundle extrasBundle = this.getIntent().getExtras();

        if (extrasBundle != null) {
            id = extrasBundle.getString("id");
            fName = extrasBundle.getString("fName");
            lName = extrasBundle.getString("lName");
            address = extrasBundle.getString("address");
            group = extrasBundle.getString("group");

            Log.d(TAG, "onCreate: id: " + id + ", group: " + group);

            initUserPanel();
        }

        //insertMockData();
        getTrempsFromServer();

        initRecyclerView();
    }

    private void initUserPanel() {


        tv_name = (TextView)findViewById(R.id.ts_user_name);
        tv_name.setText(fName + " " + lName);

        // TODO : later change to geographical current address.
        tv_address = (TextView)findViewById(R.id.ts_user_address);
        tv_address.setText(address);

        tv_group = (TextView)findViewById(R.id.ts_group_name);
        tv_group.setText(group);

    }

    private void getTrempsFromServer() {

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, ServerRequest.url+ "/getallroutinedrivesbyadmin/" + group, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(TAG, "onResponse: " + response.toString());

                        try{

                            RegularTremp t;
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject tmp = response.getJSONObject(i);

                                t = new RegularTremp();
                                t.setDriver(tmp.getString("name"));
                                t.setTotalSeats(Integer.parseInt(tmp.getString("totalslots")));

                                int occupied = Integer.parseInt(tmp.getString("totalslots")) - Integer.parseInt(tmp.getString("openslots"));

                                t.setOccupiedSeats(occupied);

                                Log.d(TAG, "onResponse: totalslots - " +tmp.getString("totalslots") + ",open - " + tmp.getString("openslots") + ", occupied: " +
                                t.getOccupiedSeats() + ", totalSeats - " + t.getTotalSeats());

                                t.setDay(tmp.getString("day"));
                                t.setHour(tmp.getString("time"));
                                t.setDestination(tmp.getString("endcity"));
                                t.setOrigin(tmp.getString("begincity"));


                                tremps.add(t);


                                /**
                                 *    "userid": "9283fh2389h32dhh83",
                                 *     "name": "יעקב פרץ",
                                 *     "begincity": "יבנה",
                                 *     "endcity": "תל-אביב",
                                 *     "day": "ד",
                                 *     "time": "10:00",
                                 *     "openslots": "2",
                                 *     "totalslots": "5"
                                 */
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
        mRecyclerView = findViewById(R.id.ts_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        TrempSelectRecyclerViewAdapter adapter = new TrempSelectRecyclerViewAdapter(tremps, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void insertMockData(){


        String[] participants = {"oded", "josh"};
        RegularTremp t = new RegularTremp();
        t.setDriver("דניאל");
        t.setDestination("תל אביב");
        t.setOrigin("רמת גן");
        t.setDay("ראשון");
        t.setHour("08:00");
        t.setParticipants(participants);
        t.setTotalSeats(5);
        t.setDriverImage("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        t.refreshSeats();

        tremps.add(t);

        t = new RegularTremp();

        t.setDriver("לאל");
        t.setDestination("תל אביב");
        t.setOrigin("יבנה");
        t.setDay("שלישי");
        t.setHour("12:00");
        t.setParticipants(participants);
        t.setTotalSeats(4);
        t.setDriverImage("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        t.refreshSeats();

        tremps.add(t);
    }


}
