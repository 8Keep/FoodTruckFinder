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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainContentPg extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private ImageView logout, menu;
    private Boolean isVendor;
    SharedPreferences sp;
    private RecyclerView recyclerView;
    private SearchView search;

    private RecyclerView.Adapter adapter;
    //192.168.0.17 for real
    //10.0.2.2 for emu
    private static final String URL_DATA = "https://www.peopleorderourpatties.com/backend/api2/foodtrucks/show.php";
    private static final String URL_DATAET = "https://www.peopleorderourpatties.com/backend/api2/vendors/show.php";
    private static final String URL_SEARCH = "https://www.peopleorderourpatties.com/backend/api2/foodtrucks/search.php";
    private static final String URL_SEARCHET = "https://www.peopleorderourpatties.com/backend/api2/vendors/search.php";
    //private static final String URL_DATA = "http://10.0.2.2/api/foodtrucks/show.php";
    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content_pg);
        logout = findViewById(R.id.logout);
        sp = getSharedPreferences("POOP", MODE_PRIVATE);
        isVendor = sp.getBoolean("isVendor", false);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainContentPg.this, FirstPage.class);
//                startActivity(intent);
//                sp.edit().putBoolean("logged", false).apply();
//                finish();
//
//            }
//        });
        menu = findViewById(R.id.menu);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        search = findViewById(R.id.searchView);
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
                        if(isVendor)
                            Toast.makeText(MainContentPg.this, "No more food trucks", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainContentPg.this, "No more vendors", Toast.LENGTH_LONG).show();
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

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });


    }

    private void searchData(String searchKey){
        listItems.clear();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("keywords", searchKey);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String sendingData;
        if ((isVendor))
        {
            sendingData = URL_SEARCH;
        }
        else
            sendingData = URL_SEARCHET;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, sendingData, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("results");
                            for(int i=0; i<array.length(); i++)
                            {
                                JSONObject o = array.getJSONObject(i);
                                String EntityName;
                                if(isVendor)
                                    EntityName = o.getString("TruckName");
                                else
                                    EntityName = o.getString("EntertainerName");
                                ListItem item = new ListItem(
                                        EntityName,
                                        o.getString("City") +", "+ o.getString("State"),
                                        o.getString("imgURL"),
                                        o.getString("username"));
                                listItems.add(item);
                            }

                            adapter = new MainPgViewAdapter(listItems, getApplicationContext());
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
    private void loadRecyclerViewData(){
        listItems.clear();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.layout_loading);
        final AlertDialog dialog = builder.create();
        dialog.show();

        String sendingURL;
        if(isVendor)
            sendingURL = URL_DATA;
        else
            sendingURL = URL_DATAET;

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                sendingURL,
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
                                String EntityName;
                                if(isVendor)
                                    EntityName = o.getString("TruckName");
                                else
                                    EntityName = o.getString("ETname");
                                ListItem item = new ListItem(
                                        EntityName,
                                        o.getString("city") +", "+ o.getString("state"),
                                        o.getString("imgURL"),
                                        o.getString("username"));
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

    public void showPopUp(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(MainContentPg.this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.logout_popup, popup.getMenu());
        popup.show();

    }
    public void logout()
    {
        Intent intent = new Intent(MainContentPg.this, FirstPage.class);
        startActivity(intent);
        sp.edit().putBoolean("logged", false).apply();
        finish();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout_id:
                logout();
                Toast.makeText(MainContentPg.this, "Logging out...", Toast.LENGTH_LONG).show();
                return true;
            default:
                return false;
        }

    }
}
