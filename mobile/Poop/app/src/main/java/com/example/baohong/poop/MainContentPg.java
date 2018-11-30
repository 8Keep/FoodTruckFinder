package com.example.baohong.poop;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainContentPg extends AppCompatActivity {
    private ImageView logout, menu;
    SharedPreferences sp;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    //192.168.0.17 for real
    //10.0.2.2 for emu
    private static final String URL_DATA = "http://10.32.254.140/api/foodtrucks/show.php";
    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content_pg);
        logout = findViewById(R.id.logout);
        sp = getSharedPreferences("POOP", MODE_PRIVATE);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainContentPg.this, FirstPage.class);
                startActivity(intent);
                sp.edit().putBoolean("logged", false).apply();
                finish();

            }
        });
        menu = findViewById(R.id.menu);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        loadRecyclerViewData();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0)
                {
                    if(!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN))
                    {
                        Toast.makeText(MainContentPg.this, "No more food trucks", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);

                        loadRecyclerViewData();

                    }
                }, 1000);

            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainContentPg.this, Profile_page.class);
                startActivity(intent);
            }
        });


    }
    private void loadRecyclerViewData(){
        listItems.clear();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading);
        final AlertDialog dialog = builder.create();
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("records");

                            for(int i=0; i<array.length(); i++)
                            {
                                JSONObject o = array.getJSONObject(i);
                                ListItem item = new ListItem(
                                        o.getString("TruckName"),
                                        o.getString("city") +", "+ o.getString("state"),
                                        o.getString("imgURL"));
                                listItems.add(item);
                            }

                            adapter = new MainPgViewAdapter(listItems, getApplicationContext());
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
