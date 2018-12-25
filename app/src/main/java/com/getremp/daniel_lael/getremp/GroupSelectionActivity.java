package com.getremp.daniel_lael.getremp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.getremp.daniel_lael.getremp.GroupSelect.GroupSelectRecyclerViewAdapter;

import java.util.ArrayList;

public class GroupSelectionActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private ArrayList<String> gsImageNames = new ArrayList<>();
    private ArrayList<String> gsImageURLs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_selection);

        initImageBitmaps();
        initRecyclerView();

    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.gs_group_rv);
        //mRecyclerView.setHasFixedSize(true);
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

        gsImageNames.add("רכבת מרכז");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("שקר כלשהו");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("שקר כלשהו - שלוחת הצפון");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("רכבת מרכז");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("שקר כלשהו");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");

        gsImageNames.add("שקר כלשהו - שלוחת הצפון");
        gsImageURLs.add("https://images-na.ssl-images-amazon.com/images/I/612pSQoWbcL._SL1100_.jpg");


    }

}
